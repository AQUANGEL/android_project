package com.vladlozkin.libgdk_protector;

import android.app.Activity;
import android.os.Bundle;

public class LeaderBoardActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board);
    }

    @Override
    public void onBackPressed() {

    }
}
