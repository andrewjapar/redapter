package com.andrewjapar.sample

import com.andrewjapar.annotations.BindViewHolder
import com.andrewjapar.sample.viewentity.UserInfoViewEntity
import com.andrewjapar.sample.viewholders.UserInfoViewHolder

@BindViewHolder(UserInfoViewHolder::class)
class MainAdapter : MainAdapter_Helper()