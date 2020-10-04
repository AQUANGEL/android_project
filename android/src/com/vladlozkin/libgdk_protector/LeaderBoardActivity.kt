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

        val scores = db.scoreDao().getAll().sortedByDescending { it.score }.toTypedArray()

        val myListAdapter = ScoreListAdapter(this, scores)
        scoreList.adapter = myListAdapter
    }

    override fun onBackPressed() {}
}