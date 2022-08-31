package com.revature.dash.presentation.core.di.module

import android.app.Application
import androidx.room.Room
import com.revature.dash.model.data.retrofit.RetroFitConstants
import com.revature.dash.model.data.retrofit.RoutineAPI
import com.revature.dash.model.data.room.RoutineDao
import com.revature.dash.model.data.room.RoutineDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(RetroFitConstants.Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }).build())
            .build()
    }

    @Singleton
    @Provides
    fun provideRoutineAPI(retrofit:Retrofit):RoutineAPI{
        return retrofit.create(RoutineAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRoutineDB(app: Application):RoutineDatabase {
        return Room.databaseBuilder(
            app,
            RoutineDatabase::class.java,
            "routineDB"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRoutineDao(routineDatabase: RoutineDatabase):RoutineDao{
        return routineDatabase.getRoutineDao()
    }


}