package com.littlesword.puzzle.gameboard;

import android.content.ClipData;
import android.view.View;

/**
 * Created by kongw1 on 9/04/16.
 */
public class TileDragListener implements View.OnLongClickListener {
    @Override
    public boolean onLongClick(View v) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        //start dragging the item touched
        v.startDrag(data, shadowBuilder, v, 0);
        return false;
    }
}
