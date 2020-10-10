package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.os.HandlerCompat.postDelayed
import kotlinx.android.synthetic.main.between_levels_layout.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread


class BetweenLevelsScreen : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.between_levels_layout)

        val level = intent.getIntExtra("level", 0)
        finishedLevlel.text = finishedLevlel.text.toString() + " " + level.toString()

    }

    override fun onStart() {
        super.onStart()
        thread {
            sleep(2500)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }


}
