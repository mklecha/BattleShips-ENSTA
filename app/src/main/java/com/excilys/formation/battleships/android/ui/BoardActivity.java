package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.excilys.formation.battleships.Board;
import com.excilys.formation.battleships.ColorUtil;
import com.excilys.formation.battleships.Hit;
import com.excilys.formation.battleships.Player;
import com.excilys.formation.battleships.ship.AbstractShip;

import java.util.Locale;

import battleships.formation.excilys.com.battleships.R;

public class BoardActivity extends AppCompatActivity implements BoardGridFragment.BoardGridFragmentListener {
    private static final String TAG = AppCompatActivity.class.getSimpleName();

    private static class Default {
        private static final int AI_DELAY = 800; // ms
    }

    /* ***
     * Widgets
     */
    /** contains BoardFragments to display ships & hits grids */
    private CustomViewPager mViewPager;

    /* ***
     * Attributes
     */
    private BoardController mBoardController;
    private Board mOpponentBoard;
    private Player mOpponent;
    private boolean mDone = false;
    private TextView mInstructionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setup layout
        setContentView(R.layout.activity_game_session);

        // init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.board_viewpager);
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        mInstructionTextView = (TextView) findViewById(R.id.instruction_textview);

        // Init the Board Controller (to create BoardGridFragments)
        mBoardController = BattleShipsApplication.getBoard();
        mOpponentBoard = BattleShipsApplication.getOpponentBoard();
        mOpponent = BattleShipsApplication.getPlayers()[1];
    }

    @Override
    public void onTileClick(int boardId, int x, int y) {
        switch (boardId) {
            case BoardController.SHIPS_FRAGMENT:
                break;
            case BoardController.HITS_FRAGMENT:
                Hit hit = mOpponentBoard.sendHit(x, y);
                boolean strike = hit != Hit.MISS;

                mBoardController.setHit(strike, x, y);
                mDone = updateScore();

                if (!mDone && !strike) {
                    mViewPager.setCurrentItem(BoardController.SHIPS_FRAGMENT);
                    mViewPager.setEnableSwipe(false);
                    doOpponentTurn();

                } else if (mDone) {
                    gotoScoreActivity();
                }
                String msgToLog = String.format(Locale.US, "Hit (%d, %d) : %s", x, y, strike);
                Log.d(TAG, msgToLog);

                showMessage(makeHitMessage(false, new int[] {x,y}, hit));
                break;
            default:
                throw new AssertionError("Unknown fragment id");
        }
    }

    private void doOpponentTurn() {
        new AsyncTask<Void, String, Boolean>() {
            private String DISPLAY_TEXT = "0", DISPLAY_HIT = "1";

            @Override
            protected Boolean doInBackground(Void... params) {
                Hit hit;
                boolean strike;
                do {
                    wait(Default.AI_DELAY);
                    publishProgress("...");
                    wait(Default.AI_DELAY);

                    int[] coordinate = new int[2];
                    hit = mOpponent.sendHit(coordinate);
                    strike = hit != Hit.MISS;
                    publishProgress(DISPLAY_TEXT, makeHitMessage(true, coordinate, hit));
                    publishProgress(DISPLAY_HIT, String.valueOf(strike), String.valueOf(coordinate[0]), String.valueOf(coordinate[1]));

                    wait(Default.AI_DELAY);
                } while(strike && !mDone);
                return mDone;
            }

            @Override
            protected void onPostExecute(Boolean done) {
                if (!done) {
                    mViewPager.setEnableSwipe(true);
                    mViewPager.setCurrentItem(BoardController.HITS_FRAGMENT);
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

            private void wait(int delay) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.execute();

    }

    private void gotoScoreActivity() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.Extra.WIN, !mOpponent.lose);
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

    private boolean updateScore() {
        for (Player player : BattleShipsApplication.getPlayers()) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips())
                if (ship.isSunk()) {
                    destroyed++;
                }

            player.destroyedCount = destroyed;
            player.lose = destroyed == player.getShips().length;
            if (player.lose) {
                return true;
            }
        }
        return false;
    }

    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        ColorUtil.Color color = ColorUtil.Color.RESET;
        switch (hit) {
            case MISS:
                msg = hit.toString();
                break;
            case STIKE:
                msg = hit.toString();
                break;
            default:
                msg = hit.toString() + " coulé";
        }
        msg = String.format("%s : frappe en %c%d\n%s", incoming ? "IA" : "JOUEUR",
                ((char) ('A' + coords[0])),
                (coords[1] + 1), msg);
        // return ColorUtil.colorize(msg, color);
        return msg;
    }

    private void showMessage(String msg) {
        mInstructionTextView.setText(msg);
    }
}
