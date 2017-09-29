package com.excilys.formation.battleships.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Arrays;
import java.util.List;

import battleships.formation.excilys.com.battleships.R;

public class ScoreActivity extends AppCompatActivity {


    public static class Extra {
        public static String WIN = "EXTRA_WIN";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        boolean win = getIntent().getExtras().getBoolean(Extra.WIN);
        List<View> won = Arrays.asList(findViewById(R.id.score_win_label), findViewById(R.id.won_image));
        List<View> lost = Arrays.asList(findViewById(R.id.score_lose_label));


        for (View v : won) {
            v.setVisibility(win ? View.VISIBLE : View.GONE);
        }
        for (View v : lost) {
            v.setVisibility(win ? View.GONE : View.VISIBLE);
        }
    }

}
