package com.littlesword.puzzle;

import android.graphics.Bitmap;

import com.littlesword.puzzle.setup.BoardMaker;
import java.util.Collections;
import java.util.List;

/**
 * Created by kongw1 on 9/04/16.
 */
public class BoardMakerImpl implements BoardMaker {

    @Override
    public void setupBoardElement(List<Bitmap> tileImages) {
        Collections.shuffle(tileImages);

    }
}
