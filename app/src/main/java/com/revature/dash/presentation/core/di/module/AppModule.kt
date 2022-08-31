package com.revature.dash.presentation.core.di.module

import com.revature.dash.model.data.retrofit.RetroFitConstants
import com.revature.dash.model.data.retrofit.RoutineAPI
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
}