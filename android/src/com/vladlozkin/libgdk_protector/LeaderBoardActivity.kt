package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.leader_board.*
import kotlin.concurrent.thread


class LeaderBoardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leader_board)


    }

    override fun onStart()
    {
        super.onStart()
        var scores: Array<Score> = arrayOf<Score>();
        thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
                    "database-name").build()

            scores = db.scoreDao().getAll().sortedByDescending { it.score }.toTypedArray()


        }
        val myListAdapter = ScoreListAdapter(this, scores)
        scoreList.adapter = myListAdapter

        // here you get the score
        var usrScore: Int = intent.getIntExtra("score", -9999) // 2
        Toast.makeText(this, usrScore.toString() , Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {}
}