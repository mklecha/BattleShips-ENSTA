package com.excilys.formation.battleships.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import battleships.formation.excilys.com.battleships.R;

public class PlayerNameActivity extends AppCompatActivity {
    EditText mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);
        mNameEditText = (EditText) findViewById(R.id.enter_player_name);
    }

    public void onClickButton(View view) {
        String name = mNameEditText.getText().toString();
        if (!name.isEmpty()) {
            BattleShipsApplication.init(name);
        }
    }
}
