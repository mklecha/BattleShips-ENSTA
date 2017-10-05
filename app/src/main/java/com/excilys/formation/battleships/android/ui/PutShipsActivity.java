package com.excilys.formation.battleships.android.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.excilys.formation.battleships.dbentities.User;
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
        getMenuInflater().inflate(R.menu.restart_ship_placement, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_restart:
                Intent intent = new Intent(this, PlayerNameActivity.class);// New activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_change_name:
                showDialog();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_leaderboard:
                Intent intent3 = new Intent(this,LeaderboardActivity.class);
                startActivity(intent3);
                break;
            case R.id.menu_logout:
                getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(NAME_KEY,"").commit();
                Intent intent2 = new Intent(this,PlayerNameActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_name, null);
        EditText mUserName = (EditText)view.findViewById(R.id.username);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
            // Add action buttons
            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                    String name = mUserName.getText().toString();
                    if(!name.isEmpty()){
                        mPlayerName.setText(name);
                        changePlayerNameInDB(name);
                        Snackbar.make(mLayout,R.string.info_username_changed,Snackbar.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else Snackbar.make(mLayout,R.string.insert_name_error,Snackbar.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                }
            });
        builder.create().show();

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

    private void changePlayerNameInDB(String playerName){
        String old = getPlayerName();
        List<User> users = User.listAll(User.class);
        for(User u:users){
            if(u.getName().equals(old)){
                u.setName(playerName);
                u.save();
            }
        }
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        preferences.edit().putString(NAME_KEY,playerName).apply();
    }
}
