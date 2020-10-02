package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.os.Bundle

import kotlinx.android.synthetic.main.leader_board.*


class LeaderBoardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leader_board)
    }

    override fun onBackPressed() {}
}