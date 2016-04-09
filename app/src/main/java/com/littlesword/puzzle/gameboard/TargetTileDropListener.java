package com.littlesword.puzzle.gameboard;

import android.view.DragEvent;
import android.view.View;

import com.littlesword.puzzle.customview.TileView;

import static com.littlesword.puzzle.customview.TileView.newInstance;

/**
 * Control the logic of dropping the view. Essential swap states between views.
 * Created by kongw1 on 9/04/16.
 */
public class TargetTileDropListener implements View.OnDragListener {

    private GameBoardContract.ViewPresenter viewPresenter;
    public TargetTileDropListener(GameBoardContract.ViewPresenter gameBoardView) {
        this.viewPresenter = gameBoardView;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                TileView dropTarget = (TileView) v;//end tile
                TileView startTile = (TileView) event.getLocalState();//start tile
                //make copy of each properties for original
                TileView draggedTile = newInstance(startTile, dropTarget.originalIndex);
                draggedTile.setImageDrawable(dropTarget.getDrawable());//change start tile states
                //make copy of each properties for destination
                TileView droppedTile = newInstance(dropTarget, startTile.originalIndex);
                droppedTile.setImageDrawable(startTile.getDrawable());
                viewPresenter.onRemoveTile(dropTarget);
                viewPresenter.onRemoveTile((startTile));
                viewPresenter.moveTile(dropTarget, draggedTile);
                viewPresenter.moveTile(startTile, droppedTile);
                viewPresenter.onPlaceTile(draggedTile, draggedTile.tileSize);
                viewPresenter.onPlaceTile(droppedTile, droppedTile.tileSize);
                break;
            default:
                break;
        }
        return true;
    }
}
