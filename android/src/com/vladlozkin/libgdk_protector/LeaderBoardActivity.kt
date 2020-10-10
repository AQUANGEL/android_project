package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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

        loadScores()

        val usrScore: Int = intent.getIntExtra("score", -9999) // 2
        if (usrScore >= 0) {
            val cdd = AddScoreDialog(this, usrScore)
            cdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            cdd.show()

            cdd.setOnDismissListener { loadScores() }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadScores() {
        var scores: Array<Score> = arrayOf<Score>();
        val fetchDb = thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,
                    "database-name").build()

            if (db.scoreDao().getAll().isEmpty()) {
                db.scoreDao().insertAll(Score(1, "Tomer Test", 1000))
            }

            scores = db.scoreDao().getAll().sortedByDescending { it.score }.toTypedArray()
            db.close()
        }
        fetchDb.join()

        val myListAdapter = ScoreListAdapter(this, scores)
        scoreList.adapter = myListAdapter
    }
}