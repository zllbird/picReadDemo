package com.bird.mm.repository

import com.bird.mm.AppExecutors
import com.bird.mm.db.MusicDao
import com.bird.mm.db.MusicInPlayListDao
import com.bird.mm.db.MusicPlayListDao
import com.bird.mm.db.UserDao
import com.bird.mm.net.ApiMTWService
import com.bird.mm.net.ApiService
import com.bird.mm.net.ApiTDService
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val musicDao: MusicDao,
    private val musicInPlayListDao: MusicInPlayListDao,
    private val musicPlayListDao: MusicPlayListDao,
    private val apiService: ApiService
) {

}