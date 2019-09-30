package com.andrewjapar.redapter

import androidx.recyclerview.widget.RecyclerView
import com.andrewjapar.annotations.BindViewHolder
import com.andrewjapar.redapter.viewentity.UserInfoViewEntity
import com.andrewjapar.redapter.viewholders.UserInfoViewHolder

@BindViewHolder(UserInfoViewHolder::class)
class MainAdapter : MainAdapter_Helper() {

    var data = emptyList<UserInfoViewEntity>()
    var itemActionListener: (UserInfoViewEntity) -> Unit = {}

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserInfoViewHolder -> holder.onBind(data[position], itemActionListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            else -> R.layout.item_user_info
        }
    }
}