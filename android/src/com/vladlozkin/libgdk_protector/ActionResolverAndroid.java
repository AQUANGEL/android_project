package com.vladlozkin.libgdk_protector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class ActionResolverAndroid extends Activity implements IActionResolver {
    Handler m_handler;
    Context m_context;

    private int MAIN_MENU_CODE = 1;
    private int LEADER_BOARD_CODE = 2;

    public ActionResolverAndroid(Context context)
    {
        m_handler = new Handler();
        m_context = context;
    }

    @Override
    public void ShowMainMenu() {
        m_handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(m_context, WelcomeActivity.class);
                if (m_context instanceof Activity) {
                    ((Activity) m_context).startActivityForResult(intent, MAIN_MENU_CODE);
                } else {
                    Log.e("ActionResolverError","mContext should be an instanceof Activity.");
                }
            }
        });
    }

    @Override
    public void ShowLeaderBoard() {
        m_handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(m_context, LeaderBoardActivity.class);
                if (m_context instanceof Activity) {
                    ((Activity) m_context).startActivityForResult(intent, LEADER_BOARD_CODE);
                } else {
                    Log.e("ActionResolverError","mContext should be an instanceof Activity.");
                }
            }
        });
    }
}
