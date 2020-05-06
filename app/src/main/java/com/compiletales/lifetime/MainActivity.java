package com.compiletales.lifetime;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCE_NAME = "com.compiletales.lifetime";
    public static final String DESTINATION_TIME_KEY = "DESTINATION_TIME";

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx = findViewById(R.id.main_activity_toolbar_text);
        Typeface adelline_font = Typeface.createFromAsset(getAssets(),  "fonts/adelline.ttf");
        tx.setTypeface(adelline_font);
        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if(savedInstanceState != null) {
            return;
        }
        pref = getSharedPreferences(SHARED_PREFERENCE_NAME, 0);

        if(pref.getLong(DESTINATION_TIME_KEY, 0) == -1){

            SetTimerFragment setTimerFragment = new SetTimerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_frame_layout, setTimerFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_frame_layout, new ShowTimerFragment()).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pref.getBoolean("firstrun", true)) {
            pref.edit().putLong(DESTINATION_TIME_KEY, Long.parseLong("-1")).apply();
            SetTimerFragment setTimerFragment = new SetTimerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_frame_layout, setTimerFragment).commit();
            pref.edit().putBoolean("firstrun", false).apply();
        }
    }
}
