package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import kotlinx.android.synthetic.main.game_over.*

class GameOverActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

        val mediaPlayer = MediaPlayer.create(this, R.raw.game_over_mario);
        mediaPlayer.start();

        game_over_layout.setOnClickListener {
            mediaPlayer.stop() // just in case
            val intent = Intent(this, LeaderBoardActivity::class.java)
            val score : Int = intent.getIntExtra("score", -9999)
            intent.putExtra("score",score)
            startActivity(intent)
        }
    }
}