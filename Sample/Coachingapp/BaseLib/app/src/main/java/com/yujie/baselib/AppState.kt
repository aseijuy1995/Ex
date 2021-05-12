package com.yujie.baselib

sealed class AppState {

		fun foreground() = Foreground

		fun background() = Background

		object Foreground : AppState()

		object Background : AppState()
}