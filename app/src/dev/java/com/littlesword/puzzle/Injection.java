package com.littlesword.puzzle;

import com.littlesword.puzzle.setup.BoardMaker;

/**
 * Created by kongw1 on 13/03/16.
 */
public class Injection {

    private Injection(){
        //no instance
    }

    public static BoardMaker getBoardMaker(){
        return new DevBoardMakerImp();
    }
}
