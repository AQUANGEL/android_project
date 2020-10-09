package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Intent
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
        val fetchDb = thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
                    "database-name").build()

            if (db.scoreDao().getAll().size == 0) {
                db.scoreDao().insertAll(Score(1, "Tomer Test", 1000))
            }

            scores = db.scoreDao().getAll().sortedByDescending { it.score }.toTypedArray()
            db.close()
        }
        fetchDb.join()

        val myListAdapter = ScoreListAdapter(this, scores)
        scoreList.adapter = myListAdapter

        // here you get the score
        val usrScore: Int = intent.getIntExtra("score", -9999) // 2
        Toast.makeText(this, usrScore.toString() , Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }
}