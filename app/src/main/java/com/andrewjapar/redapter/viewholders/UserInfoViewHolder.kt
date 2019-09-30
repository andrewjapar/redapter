package com.andrewjapar.redapter.viewholders

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewjapar.annotations.BindLayout
import com.andrewjapar.redapter.R
import com.andrewjapar.redapter.viewentity.UserInfoViewEntity
import kotlinx.android.synthetic.main.item_user_info.view.*


@BindLayout(R.layout.item_user_info)
class UserInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val useName by lazy { itemView.userName }
    private val companyTagline by lazy { itemView.companyTagline }
    private val userWebsite by lazy { itemView.userWebsite }
    private val userAvatar by lazy { itemView.userAvatar }

    fun onBind(item: UserInfoViewEntity, actionListener: (item: UserInfoViewEntity) -> Unit) {

        AsyncTask.execute {
            val bmp = BitmapFactory.decodeStream(item.avatarUrl.openConnection().getInputStream())
            userAvatar.post {
                userAvatar.setImageBitmap(bmp)
            }
        }


        useName.text = item.name
        companyTagline.text = item.roleDescription
        userWebsite.text = item.domainName

        itemView.setOnClickListener {
            actionListener(item)
        }
    }

}