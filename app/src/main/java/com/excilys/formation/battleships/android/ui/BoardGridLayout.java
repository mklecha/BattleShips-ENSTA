package com.excilys.formation.battleships.android.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

import battleships.formation.excilys.com.battleships.BuildConfig;
import battleships.formation.excilys.com.battleships.R;

public class BoardGridLayout extends FrameLayout {

//    private final ArrayList<Integer> mSprites = new ArrayList<>();

    private static final class Default {
        static final float GRID_SPACING = 4;
        static final int COL_COUNT = 10;
    }

    private int mRefw = -1;
    private int mRefh = -1;
    private float mGridSpacing = Default.GRID_SPACING;
    private int mColCount = Default.COL_COUNT;

    private ImageView mTiles[][];
    private ImageView mLayer1[][];

    public BoardGridLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BoardGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BoardGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
////        // inflate layout
////        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        View rootView = inflater.inflate(R.layout.layout_board_grid, null, false);
//
//
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BoardGridLayout, defStyle, 0);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mGridSpacing = a.getDimension(
                R.styleable.BoardGridLayout_margins,
                mGridSpacing);

        a.recycle();
//
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (BuildConfig.DEBUG && getMeasuredWidth() != getMeasuredHeight()) {
            throw new AssertionError("non square size");
        }

        if (mTiles == null) {
            return;
        }
        final int size = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        double tileSize = (size - (mGridSpacing - 1) * mColCount) / mColCount;
        double scaleHFactor = tileSize / mRefh;
        double scaleWFactor = tileSize / mRefw;
        int curLeft, curTop;

        for (int y = 0; y < mColCount; ++y) {
            for (int x = 0; x < mColCount; ++x) {
                View tile = mTiles[x][y];

                if (tile != null) {
                    curLeft = (int) ((tileSize + mGridSpacing) * x);
                    curTop = (int) ((tileSize + mGridSpacing) * y);
                    tile.layout(curLeft, curTop, (int) (curLeft + tileSize), (int) (curTop + tileSize));
                }

                ImageView sprite = mLayer1[x][y];
                if (sprite != null) {
                    // convert intrinsect drawable size to gris scale
                    int h = (int) (sprite.getDrawable().getIntrinsicHeight() * scaleHFactor);
                    int w = (int) (sprite.getDrawable().getIntrinsicWidth() * scaleWFactor);

                    curLeft = (int) ((tileSize + mGridSpacing) * x);
                    curTop = (int) ((tileSize + mGridSpacing) * y);
                    sprite.layout(curLeft, curTop, curLeft + w, curTop + h);
                }

            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size;
//        if(widthMode == MeasureSpec.EXACTLY && widthSize > 0){
//            size = widthSize;
//        }
//        else if(heightMode == MeasureSpec.EXACTLY && heightSize > 0){
//            size = heightSize;
//        }
//        else{
        size = widthSize < heightSize ? widthSize : heightSize;
//        }

        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }

    public void setColumnCount(int columnCount) {
        mColCount = columnCount;
        if (mColCount > 0) {
            mTiles = new ImageView[mColCount][mColCount];
            mLayer1 = new ImageView[mColCount][mColCount];
        }

    }

    public ImageView setTile(int drawableId, int x, int y) {
        if (x >= mColCount || y >= mColCount) {
            String msg = String.format(Locale.US, "cannot set a tile out of grid boundaries. " +
                    "( tile : %d,%d ; size : %d)", x, y, mColCount);
            throw new IllegalArgumentException(msg);
        }

        if (mTiles == null) {
            throw new IllegalStateException("cannot set a tile before setting column count");
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, null);

        if (mRefh < 0) {
            setDimensionReference(drawable);
        }

        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(drawable);
        addView(iv);
//        synchronized (mSprites) {
//            mSprites.add(drawableId);
//            mSprites.add(x);
//            mSprites.add(y);
//        }

        return mTiles[x][y] = iv;
    }

    public void setDimensionReference(int w, int h) {
        mRefw = w;
        mRefh = h;

    }

    public void setDimensionReference(Drawable drawable) {
        setDimensionReference(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }


    public ImageView setSprite(int drawableId, int x, int y) {
        if (x >= mColCount || y >= mColCount) {
            String msg = String.format(Locale.US, "cannot set a tile out of grid boundaries. " +
                    "( tile : %d,%d ; size : %d)", x, y, mColCount);
            throw new IllegalArgumentException(msg);
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, null);
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(drawable);
        addView(iv);

//        synchronized (mSprites) {
//            mSprites.add(drawableId);
//            mSprites.add(x);
//            mSprites.add(y);
//        }
        return mLayer1[x][y] = iv;

    }

//    @Override
//    public Parcelable onSaveInstanceState() {
//        //begin boilerplate code that allows parent classes to save state
//        Parcelable superState = super.onSaveInstanceState();
//
//        SavedState ss = new SavedState(superState);
//        //end
//
//        synchronized (mSprites) {
//            ss.sprites = toIntArray(mSprites);
//        }
//
//        return ss;
//    }
//
//
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//        //begin boilerplate code so parent classes can restore state
//        if (!(state instanceof SavedState)) {
//            super.onRestoreInstanceState(state);
//            return;
//        }
//
//        SavedState ss = (SavedState) state;
//        super.onRestoreInstanceState(ss.getSuperState());
//        //end
//
//        synchronized (mSprites) {
//            toIntegerList(ss.sprites, mSprites);
//            for (Iterator<Integer> it = mSprites.iterator(); it.hasNext();) {
//                setSprite(it.next(), it.next(), it.next());
//            }
//        }
//    }

//    static class SavedState extends BaseSavedState {
//        int[] sprites;
//
//        SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        private SavedState(Parcel in) {
//            super(in);
//            sprites = in.createIntArray();
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeIntArray(sprites);
//        }
//
//        //required field that makes Parcelables from a Parcel
//        public static final Parcelable.Creator<SavedState> CREATOR =
//                new Parcelable.Creator<SavedState>() {
//                    public SavedState createFromParcel(Parcel in) {
//                        return new SavedState(in);
//                    }
//
//                    public SavedState[] newArray(int size) {
//                        return new SavedState[size];
//                    }
//                };
//
//    }
//
//    private static int[] toIntArray(ArrayList<Integer> integerList) {
//        int[] res = new int[integerList.size()];
//        int k = 0;
//        for (int i : integerList) {
//            res[k++] = i;
//        }
//        return res;
//    }
//
//    private static void toIntegerList(int[] array, ArrayList<Integer> list) {
//        int k = 0;
//        while(!list.isEmpty()) {
//            list.remove(0);
//        }
//
//        for (int i : array) {
//            list.add(k++, i);
//        }
//    }
}
