package com.littlesword.puzzle;

import android.graphics.Bitmap;

import com.littlesword.puzzle.setup.BoardMaker;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongw1 on 13/03/16.
 */
public class DevBoardMakerImp implements BoardMaker{



    @Override
    public void setupBoardElement(List<Bitmap> tileImages) {
        //setup in a way that is easier to complete
        // win in 1 step
        // ---------
        // 0 4 8  12
        // 1 5 9  13
        // 2 6 10 15 ->null element
        // 3 7 11 14
        // ----------
        List<Bitmap> copyOfImages = new ArrayList<>(tileImages);
        tileImages.set(0, copyOfImages.get(0));
        tileImages.set(1, copyOfImages.get(4));
        tileImages.set(2, copyOfImages.get(8));
        tileImages.set(3, copyOfImages.get(12));
        tileImages.set(4, copyOfImages.get(1));
        tileImages.set(5, copyOfImages.get(5));
        tileImages.set(6, copyOfImages.get(9));
        tileImages.set(7, copyOfImages.get(13));
        tileImages.set(8, copyOfImages.get(2));
        tileImages.set(9, copyOfImages.get(6));
        tileImages.set(10, copyOfImages.get(10));
        tileImages.set(11, copyOfImages.get(15));
        tileImages.set(12, copyOfImages.get(3));
        tileImages.set(13, copyOfImages.get(7));
        tileImages.set(14, copyOfImages.get(11));
        tileImages.set(15, copyOfImages.get(14));
    }
}
