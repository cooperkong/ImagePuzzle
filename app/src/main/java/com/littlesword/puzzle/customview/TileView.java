package com.littlesword.puzzle.customview;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.littlesword.puzzle.model.Point;

/**
 * 
 * ImageView displaying tile of the puzzle. Contains useful functions for
 * comparing with other tiles.
 * 
 * Based on:
 * https://github.com/thillerson/Android-Slider-Puzzle/blob/master/src/
 * com/tackmobile/GameTile.java
 * 
 * @author David Vavra
 */
public class TileView extends ImageView {

	public Point coordinate;
	public int originalIndex;
	public int numberOfDrags;
	private boolean empty;
	public int tileSize;

	public TileView(Context context, int originalIndex) {
		super(context);
		this.originalIndex = originalIndex;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
		if (empty) {
			setBackgroundDrawable(null);
			setAlpha(0);
		}
	}

	public boolean isInRowOrColumnOf(TileView otherTile) {
		return (coordinate.sharesAxisWith(otherTile.coordinate));
	}

	public boolean isToRightOf(TileView tile) {
		return coordinate.isToRightOf(tile.coordinate);
	}

	public boolean isToLeftOf(TileView tile) {
		return coordinate.isToLeftOf(tile.coordinate);
	}

	public boolean isAbove(TileView tile) {
		return coordinate.isAbove(tile.coordinate);
	}

	public boolean isBelow(TileView tile) {
		return coordinate.isBelow(tile.coordinate);
	}

	/**
	 * Sets X Y coordinate for the view - works for all Android versions.
	 *
	 * @param x
	 * @param y
	 */
	public void setXY(float x, float y) {
		if (Build.VERSION.SDK_INT >= 11) {
			// native and more precise
			setX(x);
			setY(y);
		} else {
			// emulated on older versions of Android
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
			params.leftMargin = (int) x;
			params.topMargin = (int) y;
			setLayoutParams(params);
		}
	}

	/**
	 * @return get x position for all versions of Android
	 */
	public float getXPos() {
		return getX();
	}

	/**
	 * @return get y position for all versions of Android
	 */
	public float getYPos() {
		return getY();
	}

	public static TileView newInstance(TileView input, int originalIndex){
		TileView ret =  new TileView(input.getContext(), originalIndex);//use the end index
		ret.coordinate = input.coordinate;
		ret.tileSize = input.tileSize;
		ret.numberOfDrags = input.numberOfDrags;
		return ret;
	}

}
