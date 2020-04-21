package com.bird.mm

import android.app.Application
import androidx.room.Room
import com.bird.mm.db.*
import com.bird.mm.di.ViewModelModule
import com.bird.mm.net.*
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(app: Application):ApiService{
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ChuckerInterceptor(app))
                    .build()
            )
//            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://www.2717.com/")
            .addConverterFactory(MMCoverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiMTWService(app: Application):ApiMTWService{
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ChuckerInterceptor(app))
                    .build()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("http://www.meituba.com/")
//            .addConverterFactory(MMCoverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiMTWService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiTDService(app: Application):ApiTDService{
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ChuckerInterceptor(app))
                    .build()
            )
//            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://www.7160.com")
            .addConverterFactory(MMCoverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiTDService::class.java)
    }

    @Singleton
    @Provides
    fun providerNetMusicApi(app: Application):NetMusicApi{
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ChuckerInterceptor(app))
                    .build()
            )
//            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://www.7160.com")
            .addConverterFactory(MMCoverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(NetMusicApi::class.java)
    }


    @Singleton
    @Provides
    fun providerDb(app: Application): MMDb{
        return Room
            .databaseBuilder(app, MMDb::class.java, "mm.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun providerUserDao(db:MMDb):UserDao{
        return db.userDao()
    }

    @Singleton
    @Provides
    fun providerSchemeDao(db:MMDb):SchemeDao{
        return db.schemeDao()
    }

    @Singleton
    @Provides
    fun providerMusicDao(db:MMDb) : MusicDao{
        return db.musicDao()
    }

    @Singleton
    @Provides
    fun providerMusicListDao(db:MMDb) : MusicPlayListDao{
        return db.musicListDao()
    }

    @Singleton
    @Provides
    fun providerMusicInListDao(db:MMDb) : MusicInPlayListDao{
        return db.musicInListDao()
    }

}