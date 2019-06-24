package com.gounisdesign.bob.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Initial extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.button_explore) {
            Intent i = new Intent(Initial.this, Games.class);
            startActivity(i);
        }

    }
}
