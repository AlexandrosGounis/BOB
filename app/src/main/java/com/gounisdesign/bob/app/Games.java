package com.gounisdesign.bob.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Games extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games);
    }

    public void onGame1Click(View v) {
        if (v.getId() == R.id.game1) {
            Intent i = new Intent(Games.this, MainActivity.class);
            startActivity(i);
        }

    }
}