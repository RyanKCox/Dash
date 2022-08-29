package com.revature.dash.presentation.core.di.module

import com.revature.dash.presentation.controllers.mainmenu.MainMenuController
import com.revature.dash.presentation.controllers.run.RunController
import com.revature.dash.presentation.controllers.title.TitleController
import com.revature.dash.presentation.core.di.keys.ControllerKey
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ControllersModule {

    @ContributesAndroidInjector
    abstract fun bindTitleController():TitleController

    @ContributesAndroidInjector
    abstract fun bindMainMenuController():MainMenuController

    @ContributesAndroidInjector
    abstract fun bindRunController():RunController
}