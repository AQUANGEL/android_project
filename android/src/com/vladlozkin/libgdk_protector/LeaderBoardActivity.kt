package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.room.Room

import kotlinx.android.synthetic.main.leader_board.*


class LeaderBoardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leader_board)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
                                "database-name").build()

        

    }

    override fun onBackPressed() {}
}