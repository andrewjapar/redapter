package com.andrewjapar.annotations

import androidx.annotation.LayoutRes
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class BindLayout(@LayoutRes val layout: Int, val model: KClass<out RedapterModel>)