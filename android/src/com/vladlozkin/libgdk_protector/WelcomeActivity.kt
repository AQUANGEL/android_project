package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeActivity : Activity() {
    var introPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)

        introPlayer = MediaPlayer.create(this, R.raw.epicsaxguy);

        if (!introPlayer!!.isPlaying) {
            introPlayer!!.start()
            introPlayer!!.isLooping = true
        }

        startGame.setOnClickListener {
            introPlayer?.pause() // just in case
            val returnIntent = Intent()
            // I don't check the data of returned Intent, made it for future use if we will need it.
            // could of just write finish() and that is it.

            returnIntent.putExtra("startGame", 1)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

        leaderBoardBtn.setOnClickListener {
            introPlayer?.pause() // just in case
            val intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent)
            finish()
        }

        val animation = AnimationUtils.loadAnimation(this, R.anim.balloon_animation)
        balloonSet1.startAnimation(animation)
        balloonSet2.startAnimation(animation)
        balloonSet3.startAnimation(animation)
        balloonSet4.startAnimation(animation)
    }

    override fun onBackPressed() {

    }

    override fun onResume() {

//         starting the player if it is not playing
        if (!introPlayer!!.isPlaying) {
            introPlayer!!.start()
            introPlayer!!.isLooping = true
        }

        super.onResume()
    }

    override fun onStart() {
//         starting the player if it is not playing
        if (!introPlayer!!.isPlaying) {
            introPlayer!!.start()
            introPlayer!!.isLooping = true
        }

        super.onStart()
    }

    override fun onPause() {
        introPlayer?.pause()

        super.onPause()
    }

    override fun onStop() {
        introPlayer?.pause()

        super.onStop()
    }
}