package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import battleships.formation.excilys.com.battleships.R;

public class PlayerNameActivity extends AppCompatActivity {


    EditText mPlayerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);
        mPlayerEditText = (EditText) findViewById(R.id.edit_player_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickPlay(View v) {
        String name = mPlayerEditText.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, R.string.enter_name_hint, Toast.LENGTH_LONG).show();
        } else {
            BattleShipsApplication.getGame().init(name);
            Intent intent = new Intent(this, PutShipsActivity.class);
            startActivity(intent);
        }

    }

}
