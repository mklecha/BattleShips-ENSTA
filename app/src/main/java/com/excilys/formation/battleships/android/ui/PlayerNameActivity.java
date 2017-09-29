package com.excilys.formation.battleships.android.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import battleships.formation.excilys.com.battleships.R;

public class PlayerNameActivity extends AppCompatActivity {
    private static final String NAME_KEY = "player_name";

    EditText mNameEditText;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences("Pref", MODE_PRIVATE);

        String name = preferences.getString(NAME_KEY, null);
        if (name != null) {
            finish(name);
        } else {
            setContentView(R.layout.activity_player_name);
            mNameEditText = (EditText) findViewById(R.id.enter_player_name);
        }
    }

    public void onClickButton(View view) {
        String name = mNameEditText.getText().toString();
        if (!name.isEmpty()) {
            preferences.edit().putString(NAME_KEY, name).apply();
            finish(name);
        }
    }

    public void finish(String name) {
        BattleShipsApplication.init(name);
        super.finish();
    }
}
