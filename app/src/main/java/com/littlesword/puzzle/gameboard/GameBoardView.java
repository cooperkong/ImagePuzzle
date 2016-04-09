package com.littlesword.puzzle.gameboard;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.littlesword.puzzle.R;
import com.littlesword.puzzle.customview.TileView;

/**
 *
 * Layout handling creation and interaction of the game tiles. Captures gestures
 * and performs animations.
 *
 * Based on:
 * https://github.com/thillerson/Android-Slider-Puzzle/blob/master/src/
 * com/tackmobile/GameboardView.java
 *
 * @author David Vavra
 *
 */
public class GameBoardView extends RelativeLayout implements GameBoardContract.View, OnTouchListener {

	private static final String TILE_ORDER_TAG = "tile_order_tag";
	private static final String STATE_TAG = "game_board_view_state";
	private boolean boardCreated;
	private GameBoardContract.ViewPresenter viewPresenter;

	public GameBoardView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		viewPresenter = new GameBoardPresenter(this);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (!boardCreated) {
			viewPresenter.calculateBoardSize();
			viewPresenter.initTiles(getResources().getDrawable(R.drawable.globe));
			boardCreated = true;
		}
	}

	/**
	 * Places tile on appropriate place in the layout.
	 *
	 * @param tile
	 *            Tile to place
	 */
	@Override
	public void placeTile(TileView tile, int tileSize) {
		Rect tileRect = viewPresenter.rectFromPoint(tile.coordinate);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(tileSize, tileSize);
		params.topMargin = tileRect.top;
		params.leftMargin = tileRect.left;
		addView(tile, params);
		tile.setOnTouchListener(this);
		tile.setOnLongClickListener(new TileDragListener());
		tile.setOnDragListener(new TargetTileDropListener(viewPresenter));
	}
	@Override
	public void removeTile(TileView view) {
		removeView(view);
	}

	/**
	 * Handling of touch events. High-level logic for moving tiles on the game
	 * board.
	 */
	public boolean onTouch(View v, MotionEvent event) {
		return viewPresenter.handleOnTouch(v, event);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(STATE_TAG, super.onSaveInstanceState());
		bundle.putIntegerArrayList(TILE_ORDER_TAG, viewPresenter.getTileOrder()); // save the view state
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle){
			Bundle bundle = (Bundle) state;
			state = bundle.getParcelable(STATE_TAG);
			viewPresenter.setTileOrder(bundle.getIntegerArrayList(TILE_ORDER_TAG)); // restore the view state
		}
		super.onRestoreInstanceState(state);
	}


}
