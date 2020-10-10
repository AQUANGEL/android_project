package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button

import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)

        val mediaPlayer = MediaPlayer.create(this, R.raw.epicsaxguy);
        mediaPlayer.start();

        startGame.setOnClickListener {
            mediaPlayer.stop() // just in case
            val returnIntent = Intent()
            // I don't check the data of returned Intent, made it for future use if we will need it.
            // could of just write finish() and that is it.

            returnIntent.putExtra("startGame", 1)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

        leaderBoardBtn.setOnClickListener {
            mediaPlayer.stop() // just in case
            val intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent)
        }

        val animation = AnimationUtils.loadAnimation(this, R.anim.balloon_animation)
        balloonSet1.startAnimation(animation)
        balloonSet2.startAnimation(animation)
        balloonSet3.startAnimation(animation)
        balloonSet4.startAnimation(animation)
    }

    override fun onBackPressed() {}
}