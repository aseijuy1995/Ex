package com.yujie.core_lib.model

import kotlinx.coroutines.flow.Flow

interface IMediaStoreModule {

		/**
		 * 獲取媒體文件夾列表
		 * */
		fun fetchMediaFolderList(setting: MediaSetting): Flow<List<MediaFolder>>

		/**
		 * 獲取媒體列表
		 * */
		fun fetchMediaList(setting: MediaSetting): Flow<List<Media>>

		/**
		 * 獲取指定媒體文件夾列表
		 * */
		fun fetchMediaFolderFromName(folderName: String, setting: MediaSetting): Flow<MediaFolder?>

		/**
		 * 獲取指定媒體列表
		 * */
		fun fetchMediaFromName(name: String, setting: MediaSetting): Flow<Media?>
}