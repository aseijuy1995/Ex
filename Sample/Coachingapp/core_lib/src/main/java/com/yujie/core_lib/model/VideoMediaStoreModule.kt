package com.yujie.core_lib.model

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class VideoMediaStoreModule(private val cxt: Context) : IMediaStoreModule {

    private val folderMap = mutableMapOf<String, MediaFolder>()

    val isMediaFolderEmpty: Boolean
        get() = folderMap.isEmpty()

    //video
    val MEDIA_ALBUM_VIDEO: String
        get() = "MEDIA_ALBUM_VIDEO"

    override suspend fun fetchMediaFolderList(setting: MediaSetting): Flow<List<MediaFolder>> = flow {
        folderMap.clear()
        if (setting.mimeType == MimeType.VIDEO) {
            val internalContentUri: Uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
            val externalContentUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            runBlocking {
                launch(Dispatchers.IO) { fetchMediaFolderVideo(internalContentUri, setting) }
                launch(Dispatchers.IO) { fetchMediaFolderVideo(externalContentUri, setting) }
            }
            emit(folderMap.values.toList())
        } else {
            throw NotFindMediaException("MimeType is not the MimeType.Video")
        }
    }

    private fun fetchMediaFolderVideo(uri: Uri, setting: MediaSetting) {
        val projection: Array<String> = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DEFAULT_SORT_ORDER,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Video.Media.RELATIVE_PATH else MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.HEIGHT,
            MediaStore.Video.Media.WIDTH,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Video.Media.DURATION else "duration",
        )
        val selection = "${MediaStore.Video.Media.SIZE} > 0"
        val selectionArgs: Array<String> = arrayOf<String>()
        val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
        val cancellationSignal = CancellationSignal()
        val cursor = cxt.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder,
            cancellationSignal
        )
        if (cursor?.moveToFirst() == true) {
            val idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val mimeTypeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)
            val defaultSortOrderColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DEFAULT_SORT_ORDER)
            val pathColumnIndex = cursor.getColumnIndexOrThrow(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Video.Media.RELATIVE_PATH else MediaStore.Video.Media.DATA)
            val displayNameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val sizeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val heightColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT)
            val widthColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH)
            val durationColumnIndex = cursor.getColumnIndexOrThrow(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Video.Media.DURATION else "duration")
            do {
                val id = cursor.getInt(idColumnIndex)
                val mimeType = cursor.getString(mimeTypeColumnIndex)
                val defaultSortOrder = cursor.getString(defaultSortOrderColumnIndex)
                val path = cursor.getString(pathColumnIndex)
                val displayName = cursor.getString(displayNameColumnIndex)
                val size = cursor.getLong(sizeColumnIndex)
                val height = cursor.getInt(heightColumnIndex)
                val width = cursor.getInt(widthColumnIndex)
                val duration = cursor.getLong(durationColumnIndex)
                val media = Media(
                    id = id,
                    mimeType = mimeType,
                    defaultSortOrder = defaultSortOrder,
                    path = path,
                    displayName = displayName,
                    size = size,
                    height = height,
                    width = width,
                    duration = duration
                )
                if (setting.minSec != null && setting.minSec > (duration / 1000)) continue
                if (setting.maxSec != null && setting.maxSec < (duration / 1000)) continue
                //
                if (isMediaFolderEmpty) addMediaFolder(MEDIA_ALBUM_VIDEO, path)
                addMedia(MEDIA_ALBUM_VIDEO, media)
                //
                addMediaFolder(defaultSortOrder, path)
                addMedia(defaultSortOrder, media)
            } while (cursor.moveToNext())
        }
        cursor?.close()
    }

    private fun addMediaFolder(name: String, filePath: String) {
        folderMap[name]
            ?: MediaFolder(
                name = name,
                filePath = filePath
            ).apply {
                folderMap[name] = this
            }
    }

    private fun addMedia(name: String, media: Media) {
        folderMap[name]?.mediaList?.add(media)
    }

    override suspend fun fetchMediaList(setting: MediaSetting): Flow<List<Media>> {
        if (isMediaFolderEmpty) fetchMediaFolderList(setting).collect()
        val mediaList = folderMap[MEDIA_ALBUM_VIDEO]?.mediaList ?: emptyList()
        return flow { emit(mediaList) }
    }

    override suspend fun fetchMediaFolderFromName(folderName: String, setting: MediaSetting): Flow<MediaFolder?> {
        if (isMediaFolderEmpty) fetchMediaFolderList(setting).collect()
        val mediaFolder = folderMap[folderName]
        return flow { emit(mediaFolder) }
    }

    override suspend fun fetchMediaFromName(name: String, setting: MediaSetting): Flow<Media?> {
        if (isMediaFolderEmpty) fetchMediaFolderList(setting).collect()
        val media = folderMap[MEDIA_ALBUM_VIDEO]?.mediaList?.find { it.displayName == name }
        return flow { emit(media) }
    }
}