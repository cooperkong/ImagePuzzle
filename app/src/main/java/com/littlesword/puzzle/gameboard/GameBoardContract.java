package com.littlesword.puzzle.gameboard;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.littlesword.puzzle.customview.TileView;
import com.littlesword.puzzle.model.Point;

import java.util.ArrayList;

/**
 * Logic between view and presenter
 * Created by kongw1 on 9/04/16.
 */
public interface GameBoardContract {

    //all UI related operations
    interface View{

        void removeAllViews();

        Context getContext();

        int getWidth();

        int getHeight();

        void removeTile(TileView view);

        void placeTile(TileView tile, int tileSize);
    }

    interface ViewPresenter {

        void initTiles(Drawable image);

        void calculateBoardSize();

        Rect rectFromPoint(Point point);

        boolean handleOnTouch(android.view.View v, MotionEvent event);

        void setTileOrder(ArrayList<Integer> orders);

        //for persist tile order state when configuration is changed
        ArrayList<Integer> getTileOrder();

        //tell view to remove a certain tile
        void onRemoveTile(TileView view);
        //tell a view to draw a certain tile
        void onPlaceTile(TileView tile, int tileSize);

        //used when drag and drop
        void moveTile(TileView oldTile, TileView newTile);
    }
}
