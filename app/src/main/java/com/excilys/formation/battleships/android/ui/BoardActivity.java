package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.excilys.formation.battleships.logic.Hit;
import com.excilys.formation.battleships.logic.board.Board;
import com.excilys.formation.battleships.logic.player.Player;

import java.util.Locale;

import battleships.formation.excilys.com.battleships.R;


public class BoardActivity extends AppCompatActivity implements BoardGridFragment.BoardGridFragmentListener {
    private static final String TAG = BoardActivity.class.getSimpleName();

    private static class Default {
        private static final int TURN_DELAY = 1000; // ms
    }

    /* ***
     * Widgets
     */
    /**
     * contains BoardFragments to display ships & hits grids
     */
    private CustomViewPager mViewPager;
    private TextView mInstructionTextView;
    private Button mFinishButton;
    private CoordinatorLayout mLayout;

    /* ***
     * Attributes
     */
    private BoardController mBoardController;
    private Board mOpponentBoard;
    private Player mOpponent;
    private boolean mDone = false;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup layout
        setContentView(R.layout.activity_game_session);

        // init put_ships_toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.board_viewpager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(BoardController.HITS_FRAGMENT);

        mInstructionTextView = (TextView) findViewById(R.id.instruction_textview);

        mFinishButton = (Button) findViewById(R.id.finish_button);

        mLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        // Init the Board Controller (to create BoardGridFragments)
        mBoardController = BattleShipsApplication.getBoard();
        mOpponentBoard = BattleShipsApplication.getOpponentBoard();
        mOpponent = BattleShipsApplication.getPlayers()[1];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart:
                Intent intent = new Intent(this, PlayerNameActivity.class);// New activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onTileClick(int id, int x, int y) {
        if (!clicked && !mDone && id == BoardController.HITS_FRAGMENT) {
            clicked = true;
            try {
                doPlayerTurn(x, y);
            } catch (IllegalArgumentException e) {
                clicked = false;
                Snackbar.make(mLayout, R.string.field_uner_fire, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void doPlayerTurn(int x, int y) {
        if (mBoardController.getHit(x, y) != null) {
            throw new IllegalArgumentException();
        }
        Hit hit = mOpponentBoard.sendHit(x, y);
        mOpponentBoard.printBoard();
        boolean strike = hit != Hit.MISS;

        mBoardController.setHit(strike, x, y);

        if (strike) {
            mDone = isDone();
            if (mDone) {
                gotoScoreActivity();
                showFinishButton();
            }
            clicked = false;
        } else {
            new AsyncTask<Void, String, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {

                    sleep(Default.TURN_DELAY);
                    return isDone();
                }

                @Override
                protected void onPostExecute(Boolean done) {
                    mViewPager.setCurrentItem(BoardController.SHIPS_FRAGMENT);
                    mViewPager.setEnableSwipe(false);
                    clicked = false;
                }
            }.execute();

            doOpponentTurn();
        }
        String msgToLog = String.format(Locale.US, "Hit (%d, %d) : %s", x, y, strike);
        Log.d(TAG, msgToLog);

        showMessage(makeHitMessage(false, new int[]{x, y}, hit));
    }

    private void doOpponentTurn() {
        new AsyncTask<Void, String, Boolean>() {
            private String DISPLAY_TEXT = "0", DISPLAY_HIT = "1";

            @Override
            protected Boolean doInBackground(Void... params) {
                Hit hit;
                boolean strike;
                do {
                    sleep(Default.TURN_DELAY);
                    publishProgress("...");
                    sleep(Default.TURN_DELAY);

                    int[] coordinate = new int[2];
                    hit = mOpponent.sendHit(coordinate);
                    strike = hit != Hit.MISS;
                    publishProgress(DISPLAY_TEXT, makeHitMessage(true, coordinate, hit));
                    publishProgress(DISPLAY_HIT, String.valueOf(strike), String.valueOf(coordinate[0]), String.valueOf(coordinate[1]));

                    mDone = isDone();
                    sleep(Default.TURN_DELAY);
                } while (strike && !mDone);
                return mDone;
            }

            @Override
            protected void onPostExecute(Boolean done) {
                if (!done) {
                    mViewPager.setEnableSwipe(true);
                    mViewPager.setCurrentItem(BoardController.HITS_FRAGMENT);
                } else {
                    gotoScoreActivity();
                }
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if (values[0].equals(DISPLAY_TEXT)) {
                    showMessage(values[1]);
                } else if (values[0].equals(DISPLAY_HIT)) {
                    mBoardController.displayHitInShipBoard(Boolean.parseBoolean(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                }
            }


        }.execute();

    }

    private void gotoScoreActivity() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.Extra.WIN, mOpponent.getLose());
        startActivity(intent);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a BoardGridFragment
            switch (position) {
                case BoardController.SHIPS_FRAGMENT:
                case BoardController.HITS_FRAGMENT:
                    return mBoardController.getFragments()[position];

                default:
                    throw new IllegalStateException("BoardController doesn't support fragment position : " + position);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case BoardController.SHIPS_FRAGMENT:
                case BoardController.HITS_FRAGMENT:
                    return mBoardController.getFragments()[position].getName();
            }

            return null;
        }
    }

    private boolean isDone() {
        for (Player player : BattleShipsApplication.getPlayers()) {
            if (player.getLose()) {
                return true;
            }
        }
        return false;
    }

    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        switch (hit) {
            case MISS:
                msg = hit.toString();
                break;
            case STIKE:
                msg = hit.toString();
                break;
            default:
                msg = String.format(getString(R.string.board_ship_sunk_format), hit.toString());
        }
        msg = String.format(getString(R.string.board_ship_hit_format), incoming ? "IA" : BattleShipsApplication.getPlayers()[0].getName(),
                ((char) ('A' + coords[0])),
                (coords[1] + 1), msg);
        return msg;
    }

    private void showMessage(String msg) {
        mInstructionTextView.setText(msg);
    }

    private void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showFinishButton() {
        mFinishButton.setVisibility(View.VISIBLE);
        mInstructionTextView.setVisibility(View.GONE);
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoScoreActivity();
            }
        });
    }
}