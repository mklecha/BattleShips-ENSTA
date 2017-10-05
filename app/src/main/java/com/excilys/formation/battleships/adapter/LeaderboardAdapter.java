package com.excilys.formation.battleships.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.excilys.formation.battleships.dbentities.User;

import java.util.List;

import battleships.formation.excilys.com.battleships.R;

/**
 * trident 05/10/17.
 */

public class LeaderboardAdapter extends BaseAdapter {

    private List<User> users;
    private Context context;

    public LeaderboardAdapter(Context context){
        users = User.listAll(User.class);
        this.context = context;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.leaderboard_list_item, null);
        }

        TextView tt1 = (TextView) v.findViewById(R.id.tvPlayerName);
        TextView tt2 = (TextView) v.findViewById(R.id.tvPlayerGamesWon);
        TextView tt3 = (TextView) v.findViewById(R.id.tvPlayerGamesDone);

        tt1.setText(users.get(i).getName());
        tt2.setText(""+users.get(i).getGamesWin());
        tt3.setText(""+users.get(i).getGamesDone());

        return v;
    }
}
