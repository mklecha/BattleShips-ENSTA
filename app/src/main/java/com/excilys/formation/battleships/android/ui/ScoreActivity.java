package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import battleships.formation.excilys.com.battleships.R;

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
        List<View> won = Arrays.asList(findViewById(R.id.score_win_label), findViewById(R.id.won_image));
        List<View> lost = Arrays.asList(findViewById(R.id.score_lose_label));


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
                Intent intent = new Intent(this, PutShipsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                BattleShipsApplication.init();
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}
