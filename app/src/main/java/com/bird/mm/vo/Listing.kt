package com.bird.mm.vo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bird.mm.net.NetworkState

data class Listing<T>(

    val pagedList: LiveData<PagedList<T>>,

    val networkState: LiveData<NetworkState>

    )


