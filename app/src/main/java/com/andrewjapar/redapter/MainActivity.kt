package com.andrewjapar.redapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrewjapar.redapter.utils.AvatarRandomizerImpl
import com.andrewjapar.redapter.viewentity.UserInfoViewEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter()
        adapter.data = 10.generateDummyUser()

        userList.layoutManager = LinearLayoutManager(this)
        userList.adapter = adapter
        userList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun Int.generateDummyUser(): List<UserInfoViewEntity> {
        return (0..this).map {
            UserInfoViewEntity("User $it", AvatarRandomizerImpl().get(), "https://user$it.com", "Engineer $it")
        }
    }
}
