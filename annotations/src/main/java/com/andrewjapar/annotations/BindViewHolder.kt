package com.andrewjapar.annotations

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
@Repeatable
@MustBeDocumented
annotation class BindViewHolder(vararg val viewHolder: KClass<out Any>)