package com.revature.dash.presentation.core.di.module;

import com.bluelinelabs.conductor.Controller;

import java.util.Map;

import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.Multibinds;
import kotlin.jvm.JvmSuppressWildcards;

@Module
public abstract class ControllerInjectionModule {

    @Multibinds
    abstract Map<Class<? extends Controller>, AndroidInjector.Factory<? extends Controller>>
    controllerInjectorFactories();
}