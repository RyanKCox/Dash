package com.revature.dash.presentation.core.di.module

import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.domain.routine.RunRoutine
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRunRepo():IRunRoutine{
        return RunRoutine()
    }
}