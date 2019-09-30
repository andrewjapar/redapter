package com.andrewjapar.redapter.viewentity

import java.net.URL

data class UserInfoViewEntity(
    val name: String,
    val avatarUrl: URL,
    val domainName: String,
    val roleDescription: String
)