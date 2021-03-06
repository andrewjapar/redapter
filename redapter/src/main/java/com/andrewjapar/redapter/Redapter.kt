package com.andrewjapar.redapter

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andrewjapar.annotations.BindViewHolder
import com.andrewjapar.annotations.RedapterModel
import java.lang.reflect.Field

class Redapter {

    companion object {
        fun bind(activity: Activity) {
            initiateBinders(activity, activity::class.java.declaredFields)
        }

        private fun initiateBinders(obj: Any, fields: Array<Field>) {
            fields
                .filter { it.type == Adapter::class.java }
                .forEach { field ->
                    if (field.isAnnotated()) {
                        try {
                            field.isAccessible = true
                            field.set(obj, field.getClass().newInstance())
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        }
                    }
                }
        }

        private fun Field.getClass(): Class<*> {
            val parentClassName = this.declaringClass.name
            return Class.forName("${parentClassName}_${this.name}_Helper")
        }

        private fun Field.isAnnotated(): Boolean {
            return this.isAnnotationPresent(BindViewHolder::class.java)
        }
    }

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