package com.revature.dash.presentation.core.di.module

import com.revature.dash.domain.routine.IRunRoutine
import com.revature.dash.domain.routine.RunRoutine
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRunRepo(repo: RunRoutine): IRunRoutine
}