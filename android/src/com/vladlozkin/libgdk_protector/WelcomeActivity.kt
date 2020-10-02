package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)

        startGame.setOnClickListener {
            val returnIntent = Intent()
            // I don't check the data of returned Intent, made it for future use if we will need it.
            // could of just write finish() and that is it.
            returnIntent.putExtra("startGame", 1)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onBackPressed() {}
}