package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.excilys.formation.battleships.dbentities.User;

import java.util.Arrays;
import java.util.List;

import battleships.formation.excilys.com.battleships.R;

import static com.excilys.formation.battleships.android.ui.BattleShipsApplication.NAME_KEY;
import static com.excilys.formation.battleships.android.ui.BattleShipsApplication.PREF_NAME;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBackButton;
    private Button mStartOverButton;

    public static class Extra {
        public static String WIN = "EXTRA_WIN";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(this);
        mStartOverButton = (Button) findViewById(R.id.start_over_button);
        mStartOverButton.setOnClickListener(this);

        boolean win = getIntent().getExtras().getBoolean(Extra.WIN);
        if(win){
            updateUserInDB(true);
        }
        List<View> won = Arrays.asList(findViewById(R.id.score_win_label), findViewById(R.id.won_image));
        List<View> lost = Arrays.asList(findViewById(R.id.score_lose_label), findViewById(R.id.lost_image));


        for (View v : won) {
            v.setVisibility(win ? View.VISIBLE : View.GONE);
        }
        for (View v : lost) {
            v.setVisibility(win ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button: {
                this.onBackPressed();
                break;
            }

            case R.id.start_over_button: {
                updateUserInDB(false);
                Intent intent = new Intent(this, PutShipsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                BattleShipsApplication.init();
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    public void updateUserInDB(boolean winOrDone){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String name =  preferences.getString(NAME_KEY, "unknown");
        for(User u:User.listAll(User.class)){
            if(u.getName().equals(name)){
                if(winOrDone)u.setGamesWin(u.getGamesWin()+1);
                if(!winOrDone)u.setGamesDone(u.getGamesDone()+1);
                u.save();
                break;
            }
        }
    }
}
