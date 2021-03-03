package tw.north27.coachingapp.ext

import androidx.paging.PagingConfig


val pagingConfig: PagingConfig
    get() = PagingConfig(
        pageSize = PAGING_PAGE_SIZE,
        enablePlaceholders = true,
    )

private const val PAGING_PAGE_SIZE = 10