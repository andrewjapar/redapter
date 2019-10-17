package com.andrewjapar.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrewjapar.annotations.BindViewHolder
import com.andrewjapar.annotations.RedapterModel
import com.andrewjapar.redapter.Redapter
import com.andrewjapar.sample.utils.AvatarRandomizerImpl
import com.andrewjapar.sample.viewentity.UserInfoViewEntity
import com.andrewjapar.sample.viewholders.UserInfoViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @BindViewHolder(UserInfoViewHolder::class)
    lateinit var adapter: Redapter.Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Redapter.bind(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val adapter = MainAdapter()
        adapter.data = 10.generateDummyUser()

        userList.layoutManager = LinearLayoutManager(this)
        userList.adapter = adapter
        userList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun Int.generateDummyUser(): MutableList<RedapterModel> {
        return (0..this).map {
            UserInfoViewEntity("User $it", AvatarRandomizerImpl().get(), "https://user$it.com", "Engineer $it")
        }.toMutableList()
    }
}
