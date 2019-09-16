package com.andrewjapar.annotations

import androidx.annotation.LayoutRes

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class BindLayout(@LayoutRes val layout: Int)