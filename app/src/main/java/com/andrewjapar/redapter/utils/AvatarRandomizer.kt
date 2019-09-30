package com.andrewjapar.redapter.utils

import java.net.URL
import java.util.*

/*
 * This class used for creating dummy avatar only
 * because it is not returned on API
 */

interface AvatarRandomizer {
    fun get(): URL
}

class AvatarRandomizerImpl: AvatarRandomizer {

    override fun get(): URL {
        val avatarList = listOf(
            "https://pbs.twimg.com/profile_images/974603248119222272/N5PLzyan.jpg",
            "https://d19m59y37dris4.cloudfront.net/landy/1-0/img/avatar-5.jpg",
            "https://pbs.twimg.com/profile_images/1079706442763067392/wuaeGZnN.jpg",
            "https://pbs.twimg.com/profile_images/974736784906248192/gPZwCbdS.jpg",
            "https://wrappixel.com/demos/admin-templates/pixeladmin/plugins/images/users/1.jpg",
            "https://pbs.twimg.com/profile_images/969073897189523456/rSuiu_Hr.jpg",
            "https://s3.amazonaws.com/37assets/svn/1024-original.1e9af38097008ef9573f03b03ef6f363219532f9.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTo_T3JKQOf4vV6cCkq54xiz1pJfQtyZk_29tgjHLlSNKJ_xrMF"
        )
        val randomIndex = Random().nextInt(avatarList.size)
        return URL(avatarList[randomIndex])
    }
}