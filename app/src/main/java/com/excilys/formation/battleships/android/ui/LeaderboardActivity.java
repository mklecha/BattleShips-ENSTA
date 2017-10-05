package com.excilys.formation.battleships.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.excilys.formation.battleships.adapter.LeaderboardAdapter;

import battleships.formation.excilys.com.battleships.R;

public class LeaderboardActivity extends AppCompatActivity {

    ListView mLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setFinishOnTouchOutside(false);
        mLeaderboard = (ListView)findViewById(R.id.lvLeaderboard);
        mLeaderboard.setAdapter(new LeaderboardAdapter(this));
    }
}
