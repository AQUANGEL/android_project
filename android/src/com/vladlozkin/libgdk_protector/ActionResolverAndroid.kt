package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log

class ActionResolverAndroid(context: Context) : Activity(), IActionResolver {
    var m_handler: Handler
    var m_context: Context
    private val MAIN_MENU_CODE = 1
    private val LEADER_BOARD_CODE = 2
    override fun ShowMainMenu() {
        m_handler.post {
            val intent = Intent(m_context, WelcomeActivity::class.java)
            if (m_context is Activity) {
                (m_context as Activity).startActivityForResult(intent, MAIN_MENU_CODE)
            } else {
                Log.e("ActionResolverError", "mContext should be an instanceof Activity.")
            }
        }
    }

    override fun ShowLeaderBoard(score: Int) {
        m_handler.post {
            val intent = Intent(m_context, LeaderBoardActivity::class.java)
            intent.putExtra("score",score)
            if (m_context is Activity) {
                (m_context as Activity).startActivityForResult(intent, LEADER_BOARD_CODE)
            } else {
                Log.e("ActionResolverError", "mContext should be an instanceof Activity.")
            }
        }
    }

    init {
        m_handler = Handler()
        m_context = context
    }
}