package com.revature.dash.presentation.core.di.module

import com.revature.dash.presentation.MainActivity
import com.revature.dash.presentation.core.activity.BaseActivity
import com.revature.dash.presentation.core.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule{

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeMainActivity():MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeBaseActivity():BaseActivity
}