package com.excilys.formation.battleships.android.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.excilys.formation.battleships.logic.ships.AbstractShip;

import java.util.List;
import java.util.Locale;

import battleships.formation.excilys.com.battleships.R;

import static com.excilys.formation.battleships.android.ui.BattleShipsApplication.NAME_KEY;
import static com.excilys.formation.battleships.android.ui.BattleShipsApplication.PREF_NAME;

public class PutShipsActivity extends AppCompatActivity implements BoardGridFragment.BoardGridFragmentListener {
    private static final String TAG = PutShipsActivity.class.getSimpleName();

    /* ***
     * Widgets
     */
    private RadioGroup mOrientationRadioGroup;
    private RadioButton mNorthRadio;
    private RadioButton mSouthRadio;
    private RadioButton mEastRadio;
    private RadioButton mWestRadio;
    private TextView mShipName;
    private TextView mPlayerName;
    private View mLayout;
    private Toolbar mToolbar;

    /* ***
     * Attributes
     */
    private BoardController mBoard;
    private int mCurrentShip;
    private AbstractShip[] mShips;
    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the layout
        setContentView(R.layout.activity_put_ships);

        // Init the put_ships_toolbar
        mToolbar = (Toolbar) findViewById(R.id.putship_toolbar_actionbar);
        setSupportActionBar(mToolbar);
        try {
            getActionBar().setTitle("");
            getActionBar().setDisplayShowTitleEnabled(false);
            getActionBar().setDisplayShowHomeEnabled(false);
        } catch (Exception ex) {
        }

        mPlayerName = (TextView) mToolbar.findViewById(R.id.tvPlayerName);
        mPlayerName.setText(getPlayerName());

        mLayout = findViewById(R.id.main_content);
        mOrientationRadioGroup = (RadioGroup) findViewById(R.id.putship_radios_orientation);
        mOrientationRadioGroup.setOnCheckedChangeListener(new ShipOrientationChangeListener());

        mNorthRadio = (RadioButton) findViewById(R.id.radio_north);
        mSouthRadio = (RadioButton) findViewById(R.id.radio_south);
        mEastRadio = (RadioButton) findViewById(R.id.radio_east);
        mWestRadio = (RadioButton) findViewById(R.id.radio_west);
        mShipName = (TextView) findViewById(R.id.ship_name);

        // init board controller to create BoardGridFragments
        int playerId = 0;
        mCurrentShip = 0;
        mBoard = BattleShipsApplication.getBoard();
        List<AbstractShip> ships = BattleShipsApplication.getPlayers()[playerId].getShips();
        mShips = ships.toArray(new AbstractShip[ships.size()]);

        mFragment = mBoard.getFragments()[BoardController.SHIPS_FRAGMENT];
        if (savedInstanceState == null) {
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.putships_fragment_container,
                            mFragment)
                    .commit();
        }

        updateRadioButton();
        updateNextShipNameToDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.put_ships, menu);
        return true;
    }

    @Override
    public void onTileClick(int boardId, int x, int y) {
        String msg;
        msg = String.format(Locale.US, "put ship : (%d, %d)", x, y);
        Log.d(TAG, msg);
        try {
            mBoard.putShip(mShips[mCurrentShip], x, y);
            mCurrentShip++;
            updateNextShipNameToDisplay();
        } catch (Exception e) {
            Snackbar.make(mLayout, R.string.put_ship_error, Snackbar.LENGTH_LONG).show();
        }

        if (mCurrentShip >= mShips.length) {
            gotoBoardActivity();
        } else {
            updateRadioButton();
        }
    }

    private void gotoBoardActivity() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(mFragment)
                .commit();

        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateRadioButton() {
        if (mShips[mCurrentShip].getOrientation() == null)
            mShips[mCurrentShip].setOrientation(AbstractShip.Orientation.NORTH);

        switch (mShips[mCurrentShip].getOrientation()) {
            case NORTH:
                mNorthRadio.setChecked(true);
                break;
            case SOUTH:
                mSouthRadio.setChecked(true);
                break;
            case EAST:
                mEastRadio.setChecked(true);
                break;
            case WEST:
                mWestRadio.setChecked(true);
                break;
        }
    }

    private class ShipOrientationChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radio_east:
                    mShips[mCurrentShip].setOrientation(AbstractShip.Orientation.EAST);
                    break;
                case R.id.radio_north:
                    mShips[mCurrentShip].setOrientation(AbstractShip.Orientation.NORTH);
                    break;
                case R.id.radio_south:
                    mShips[mCurrentShip].setOrientation(AbstractShip.Orientation.SOUTH);
                    break;
                case R.id.radio_west:
                    mShips[mCurrentShip].setOrientation(AbstractShip.Orientation.WEST);
                    break;
            }
        }
    }

    private void updateNextShipNameToDisplay() {
        if (mCurrentShip < mShips.length) {
            String text = "" + mShips[mCurrentShip].getName() + "(" + mShips[mCurrentShip].getLength() + ")";
            mShipName.setText(text);
        }
    }

    private String getPlayerName() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return preferences.getString(NAME_KEY, "unknown");
    }
}
