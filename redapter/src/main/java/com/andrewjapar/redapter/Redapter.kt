package com.andrewjapar.redapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewjapar.annotations.RedapterModel

class Redapter {

    abstract class Adapter : RecyclerView.Adapter<ViewHolder>() {
        var data: MutableList<RedapterModel> = mutableListOf()
        var itemActionListener: (RedapterModel) -> Unit = {}

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.onBind(data[position], itemActionListener)
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: RedapterModel, itemActionListener: (RedapterModel) -> Unit)
    }
}