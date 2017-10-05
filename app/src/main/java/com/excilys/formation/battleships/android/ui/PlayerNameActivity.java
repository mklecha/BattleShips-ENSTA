package com.excilys.formation.battleships.android.ui;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import battleships.formation.excilys.com.battleships.R;

import static com.excilys.formation.battleships.android.ui.BattleShipsApplication.NAME_KEY;
import static com.excilys.formation.battleships.android.ui.BattleShipsApplication.PREF_NAME;

public class PlayerNameActivity extends AppCompatActivity {


    EditText mNameEditText;
    SharedPreferences preferences;
    View mPlayerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        String name = preferences.getString(NAME_KEY, null);
        if (name != null) {
            finish(name);
        } else {
            setContentView(R.layout.activity_player_name);
            mNameEditText = (EditText) findViewById(R.id.enter_player_name);
            mPlayerLayout = findViewById(R.id.player_name_layout);
        }
    }

    public void onClickButton(View view) {
        String name = mNameEditText.getText().toString();
        if (!name.isEmpty()) {
            preferences.edit().putString(NAME_KEY, name).apply();
            finish(name);
        } else {
            Snackbar.make(mPlayerLayout, R.string.insert_name_error, Snackbar.LENGTH_LONG).show();
        }
    }

    public void finish(String name) {
        BattleShipsApplication.init(name);
        super.finish();
    }
}
