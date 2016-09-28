package com.excilys.formation.battleships.android.ui;

import android.content.Context;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.excilys.formation.battleships.IBoard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import battleships.formation.excilys.com.battleships.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BoardGridFragment.BoardGridFragmentListener} interface
 * to handle interaction events.
 * Use the {@link BoardGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardGridFragment extends Fragment {



    private static final class Args {
        public static final String NAME = "BUNDLE_NAME";
        public static final String SIZE = "BUDLE_SIZE";
        public static final String DRAWABLE = "BUNDLE_DRAWABLE";
        public static final String FRAGMENT_ID = "FRAGMENT_ID";
        public static final String PENDING_DRAWS = "BUNDLE_PENDING_DRAWS";
    }

    /* ***
     * Attributes
     */
    private BoardGridLayout mGrid;
    private int mSize;
    private int mTileDrawable;
    private String mName;
    private boolean mCreated;
    private int mId;
    private ArrayList<Integer> mPendingDraws = new ArrayList<>();

    private BoardGridFragmentListener mListener;


    public BoardGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @param fragmentID the id of the fragment, that will given back through onTileClick listener
     * @param size the size of the grid to create fragment for.
     * @param tileDrawableId the id of the tile's drawable.
     * @param titleId the id of the grid's title.
     * @return A new instance of fragment BoardGridFragment.
     */
    public static BoardGridFragment newInstance(int fragmentID, int size, int tileDrawableId, int titleId) {
        BoardGridFragment fragment = new BoardGridFragment();
        android.os.Bundle args = new android.os.Bundle();
        args.putInt(Args.FRAGMENT_ID, fragmentID);
        args.putInt(Args.SIZE, size);
        args.putInt(Args.DRAWABLE, tileDrawableId);
        args.putInt(Args.NAME,  titleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalStateException("no args provided");
        }

        mId = args.getInt(Args.FRAGMENT_ID);
        mSize = args.getInt(Args.SIZE);
        mName = getContext().getResources().getString(args.getInt(Args.NAME));
        mTileDrawable = args.getInt(Args.DRAWABLE);

        if (savedInstanceState != null) {
            mPendingDraws = savedInstanceState.getIntegerArrayList(Args.PENDING_DRAWS);
            mCreated = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             android.os.Bundle savedInstanceState) {

        // inflate view
        View rootView = inflater.inflate(R.layout.fragment_board_grid, container, false);
        mGrid = (BoardGridLayout) rootView.findViewById(R.id.board_grid_layout);
        mGrid.setColumnCount(mSize);

        // empty up tile container.
        mGrid.removeAllViews();

        ImageView currentTile;

        // re-fill it with proper tile count
        for (int x = 0; x < mSize; ++x) {
            for (int y = 0; y < mSize; ++y) {
                currentTile = mGrid.setTile(mTileDrawable, x, y);
                attachListener(currentTile, x, y);

            }
        }

        mCreated = true;
        synchronized (this) {

            for (Iterator<Integer> it = mPendingDraws.iterator(); it.hasNext();) {
                mGrid.setSprite(it.next(), it.next(), it.next());
            }
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BoardGridFragmentListener) {
            mListener = (BoardGridFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement " + BoardGridFragmentListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(Args.PENDING_DRAWS, mPendingDraws);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    /* ***
     * Public methods
     */

    public String getName() {
        return mName;
    }

    public void putDrawable(int drawableId, int x, int y) {

        if (mCreated) {
            mGrid.setSprite(drawableId, x, y);
        }

        mPendingDraws.add(drawableId);
        mPendingDraws.add(x);
        mPendingDraws.add(y);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface BoardGridFragmentListener {
        void onTileClick(int id, int x, int y);
    }

    /* ***
     * Private
     */

    private void attachListener(ImageView currentTile, final int x, final int y) {
        currentTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTileClick(mId, x, y);
            }
        });
    }

}
