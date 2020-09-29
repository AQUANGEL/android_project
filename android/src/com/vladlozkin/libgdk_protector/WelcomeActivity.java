package com.vladlozkin.libgdk_protector;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        Button startGameBtn =(Button) findViewById(R.id.startGame);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                // I don't check the data of returned Intent, made it for future use if we will need it.
                // could of just write finish() and that is it.
                returnIntent.putExtra("startGame",1);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
