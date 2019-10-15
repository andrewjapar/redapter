package com.andrewjapar.sample.viewentity

import java.net.URL

data class UserInfoViewEntity(
    val name: String,
    val avatarUrl: URL,
    val domainName: String,
    val roleDescription: String
)