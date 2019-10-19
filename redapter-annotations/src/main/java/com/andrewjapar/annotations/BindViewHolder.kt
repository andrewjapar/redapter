package com.andrewjapar.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@MustBeDocumented
annotation class BindViewHolder(vararg val viewHolders: KClass<out Any>)