package com.revature.dash.presentation.core.di.keys

import kotlin.reflect.KClass


annotation class AndroidInjectorKeyRegistry(
    val keys: Array<KClass<out Annotation>> = [],
)