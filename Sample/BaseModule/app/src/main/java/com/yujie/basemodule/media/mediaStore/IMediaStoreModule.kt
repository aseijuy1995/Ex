package tw.north27.coachingapp.media.mediaStore

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

//all
const val MEDIA_ALBUM_ALL = "MEDIA_ALBUM_ALL"

//audio
const val MEDIA_ALBUM_AUDIO = "MEDIA_ALBUM_AUDIO"

//image
const val MEDIA_ALBUM_IMAGE = "MEDIA_ALBUM_IMAGE"

//video
const val MEDIA_ALBUM_VIDEO = "MEDIA_ALBUM_VIDEO"

interface IMediaStoreModule {
		/**
		 * 獲取媒體相關資訊
		 * */
		fun fetchMedia(setting: MediaAlbumSetting): Completable

		/**
		 * 獲取媒體專輯
		 * */
		fun getMediaAlbum(setting: MediaAlbumSetting): PublishRelay<List<MediaAlbum>>

		/**
		 * 獲取指定媒體專輯
		 * MEDIA_ALBUM_ALL：全部（音訊，圖片，影音）
		 * MEDIA_ALBUM_AUDIO：音訊
		 * MEDIA_ALBUM_IMAGE：圖片
		 * MEDIA_ALBUM_VIDEO：影音
		 * */
		fun getAlbumFromName(albumName: String, setting: MediaAlbumSetting): Observable<MediaAlbum?>

		/**
		 * 獲取指定媒體專輯
		 * MEDIA_ALBUM_ALL：全部（音訊，圖片，影音）
		 * MEDIA_ALBUM_AUDIO：音訊
		 * MEDIA_ALBUM_IMAGE：圖片
		 * MEDIA_ALBUM_VIDEO：影音
		 * */
		fun getAlbumFromNameSync(albumName: String, setting: MediaAlbumSetting): MediaAlbum?
}