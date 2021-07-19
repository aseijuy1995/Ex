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

class ImageMediaStoreModule(private val cxt: Context) : IMediaStoreModule {

    private val folderMap = mutableMapOf<String, MediaFolder>()

    val isMediaFolderEmpty: Boolean
        get() = folderMap.isEmpty()

    //image
    val MEDIA_ALBUM_IMAGE: String
        get() = "MEDIA_ALBUM_IMAGE"

    override suspend fun fetchMediaFolderList(setting: MediaSetting): Flow<List<MediaFolder>> = flow {
        folderMap.clear()
        if (setting.mimeType == MimeType.IMAGE) {
            val internalContentUri: Uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
            val externalContentUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            runBlocking {
                launch(Dispatchers.IO) { fetchMediaFolderImage(internalContentUri, setting) }
                launch(Dispatchers.IO) { fetchMediaFolderImage(externalContentUri, setting) }
            }
            emit(folderMap.values.toList())
        } else {
            throw NotFindMediaException("MimeType is not the MimeType.IMAGE")
        }
    }

    private fun fetchMediaFolderImage(uri: Uri, setting: MediaSetting) {
        val projection: Array<String> = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DEFAULT_SORT_ORDER,//MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.RELATIVE_PATH else MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.WIDTH,
//						if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_ID else "bucket_id",
        )
        val selection = "${MediaStore.Images.Media.SIZE} > 0"
        val selectionArgs: Array<String> = arrayOf<String>()
        val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
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
            val idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val mimeTypeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val defaultSortOrderColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DEFAULT_SORT_ORDER)
            val pathColumnIndex = cursor.getColumnIndexOrThrow(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.RELATIVE_PATH else MediaStore.Images.Media.DATA)
            val displayNameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val heightColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val widthColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            do {
                val id = cursor.getInt(idColumnIndex)
                val mimeType = cursor.getString(mimeTypeColumnIndex)
                val defaultSortOrder = cursor.getString(defaultSortOrderColumnIndex)
                val path = cursor.getString(pathColumnIndex)
                val displayName = cursor.getString(displayNameColumnIndex)
                val size = cursor.getLong(sizeColumnIndex)
                val height = cursor.getInt(heightColumnIndex)
                val width = cursor.getInt(widthColumnIndex)
                val media = Media(
                    id = id,
                    mimeType = mimeType,
                    defaultSortOrder = defaultSortOrder,
                    path = path,
                    displayName = displayName,
                    size = size,
                    height = height,
                    width = width,
                )

                if (setting.minSize != null && setting.minSize < size) continue
                if (setting.maxSize != null && setting.maxSize > size) continue
                //
                if (isMediaFolderEmpty) addMediaFolder(MEDIA_ALBUM_IMAGE, path)
                addMedia(MEDIA_ALBUM_IMAGE, media)
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
        val mediaList = folderMap[MEDIA_ALBUM_IMAGE]?.mediaList ?: emptyList()
        return flow { emit(mediaList) }
    }

    override suspend fun fetchMediaFolderFromName(folderName: String, setting: MediaSetting): Flow<MediaFolder?> {
        if (isMediaFolderEmpty) fetchMediaFolderList(setting).collect()
        val mediaFolder = folderMap[folderName]
        return flow { emit(mediaFolder) }
    }

    override suspend fun fetchMediaFromName(name: String, setting: MediaSetting): Flow<Media?> {
        if (isMediaFolderEmpty) fetchMediaFolderList(setting).collect()
        val media = folderMap[MEDIA_ALBUM_IMAGE]?.mediaList?.find { it.displayName == name }
        return flow { emit(media) }
    }
}