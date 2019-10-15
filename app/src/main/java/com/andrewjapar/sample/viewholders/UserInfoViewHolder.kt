package com.andrewjapar.sample.viewholders

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import com.andrewjapar.annotations.BindLayout
import com.andrewjapar.annotations.RedapterModel
import com.andrewjapar.redapter.Redapter
import com.andrewjapar.sample.R
import com.andrewjapar.sample.viewentity.UserInfoViewEntity
import kotlinx.android.synthetic.main.item_user_info.view.*

@BindLayout(layout = R.layout.item_user_info, model = UserInfoViewEntity::class)
class UserInfoViewHolder(view: View) : Redapter.ViewHolder(view) {

    private val useName by lazy { itemView.userName }
    private val companyTagline by lazy { itemView.companyTagline }
    private val userWebsite by lazy { itemView.userWebsite }
    private val userAvatar by lazy { itemView.userAvatar }

    override fun onBind(item: RedapterModel, itemActionListener: (RedapterModel) -> Unit) {
        if (item is UserInfoViewEntity) {
            AsyncTask.execute {
                val bmp =
                    BitmapFactory.decodeStream(item.avatarUrl.openConnection().getInputStream())
                userAvatar.post {
                    userAvatar.setImageBitmap(bmp)
                }
            }


            useName.text = item.name
            companyTagline.text = item.roleDescription
            userWebsite.text = item.domainName

            itemView.setOnClickListener {
                itemActionListener(item)
            }
        }
    }
}