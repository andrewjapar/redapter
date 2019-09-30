package com.andrewjapar.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class BindViewHolder(vararg val viewHolders: KClass<out Any>)