package com.revature.dash.presentation.core.di

import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.domain.routine.RunRoutine
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindRunRepo(runRepo:RunRoutine):IRunRoutine
}