package com.littlesword.puzzle.setup;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by kongw1 on 9/04/16.
 */
public interface BoardMaker {
    //method to layout the correct sequence of tiles
    void setupBoardElement(List<Bitmap> tileImages);
}
