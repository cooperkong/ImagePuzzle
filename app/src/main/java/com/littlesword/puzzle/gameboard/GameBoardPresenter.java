package com.littlesword.puzzle.gameboard;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.littlesword.puzzle.customview.TileView;
import com.littlesword.puzzle.model.Point;
import com.littlesword.puzzle.util.TileSlicer;

import java.util.ArrayList;

/**
 * Hold all states of the view, separate all logic from view layer
 * Created by kongw1 on 9/04/16.
 */
public class GameBoardPresenter implements GameBoardContract.ViewPresenter {

    public enum Direction {
        X, Y
    }; // movement along x or y axis
    public static final int GRID_SIZE = 4; // 4x4
    private ArrayList<Integer> tileOrder;
    private ArrayList<TileView> tiles;
    private TileView emptyTile;
    private GameBoardContract.View view;
    private int tileSize;
    private RectF gameboardRect;
    private TileView movedTile;
    private PointF lastDragPoint;
    private ArrayList<GameTileMotionDescriptor> currentMotionDescriptors;



    public GameBoardPresenter(GameBoardContract.View gameBoardView) {
        this.view = gameBoardView;
    }

    @Override
    public void initTiles(Drawable image) {
        view.removeAllViews();
        // load image to slicer
        Bitmap original = ((BitmapDrawable) image).getBitmap();
        TileSlicer tileSlicer = new TileSlicer(original, GRID_SIZE, view.getContext());
        // order slices
        if (tileOrder == null) {
            tileSlicer.randomizeSlices();
        } else {
            tileSlicer.setSliceOrder(tileOrder);
        }
        // fill game board with slices
        tiles = new ArrayList<>();
        for (int rowI = 0; rowI < GRID_SIZE; rowI++) {
            for (int colI = 0; colI < GRID_SIZE; colI++) {
                TileView tile;
                if (tileOrder == null) {
                    tile = tileSlicer.getTile();
                } else {
                    tile = tileSlicer.getTile();
                }
                tile.coordinate = new Point(rowI, colI);
                tile.tileSize = tileSize;
                if (tile.isEmpty()) {
                    emptyTile = tile;
                }
                view.placeTile(tile, tileSize);
                tiles.add(tile);
            }
        }
    }

    @Override
    public void calculateBoardSize() {
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        // fit in portrait or landscape
        if (viewWidth > viewHeight) {
            tileSize = viewHeight / GRID_SIZE;
        } else {
            tileSize = viewWidth / GRID_SIZE;
        }
        int gameboardSize = tileSize * GRID_SIZE;
        // center gameboard
        int gameboardTop = viewHeight / 2 - gameboardSize / 2;
        int gameboardLeft = viewWidth / 2 - gameboardSize / 2;
        gameboardRect = new RectF(gameboardLeft, gameboardTop, gameboardLeft + gameboardSize, gameboardTop
                + gameboardSize);
    }

    /**
     * @param coordinate
     * @return Rectangle for given coordinate
     */
    @Override
    public Rect rectFromPoint(Point coordinate) {
        int gameboardY = (int) Math.floor(gameboardRect.top);
        int gameboardX = (int) Math.floor(gameboardRect.left);
        int top = (coordinate.row * tileSize) + gameboardY;
        int left = (coordinate.column * tileSize) + gameboardX;
        return new Rect(left, top, left + tileSize, top + tileSize);
    }

    @Override
    public boolean handleOnTouch(View v, MotionEvent event) {
        TileView touchedTile = (TileView) v;
        if (touchedTile.isEmpty() || !touchedTile.isInRowOrColumnOf(emptyTile)) {
            return false;
        } else {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                // start of the gesture
                movedTile = touchedTile;
                currentMotionDescriptors = getTilesBetweenEmptyTileAndTile(movedTile);
                movedTile.numberOfDrags = 0;
            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                // during the gesture
                if (lastDragPoint != null) {
                    followFinger(event);
                }
                lastDragPoint = new PointF(event.getRawX(), event.getRawY());
            } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                // end of gesture
                // reload the motion descriptors in case of position change
                currentMotionDescriptors = getTilesBetweenEmptyTileAndTile(movedTile);
                // if drag was over 50% or it's click, do the move
                if (lastDragMovedAtLeastHalfWay() || isClick()) {
                    animateTilesToEmptySpace();
                } else {
                    animateTilesBackToOrigin();
                }
                currentMotionDescriptors = null;
                lastDragPoint = null;
                movedTile = null;
            }
            return false;
        }
    }

    @Override
    public void setTileOrder(ArrayList<Integer> orders) {
        tileOrder = orders;
    }

    @Override
    public ArrayList<Integer> getTileOrder() {
        ArrayList<Integer> tileLocations = new ArrayList<>();
        for (int rowI = 0; rowI < GRID_SIZE; rowI++) {
            for (int colI = 0; colI < GRID_SIZE; colI++) {
                TileView tile = getTileAtPoint(new Point(rowI, colI));
                if(tile != null)
                    tileLocations.add(tile.originalIndex);
            }
        }
        return tileLocations;
    }

    @Override
    public void onRemoveTile(TileView tileView) {
        view.removeTile(tileView);
    }

    @Override
    public void onPlaceTile(TileView tile, int tileSize) {
        view.placeTile(tile, tileSize);
    }

    @Override
    public void moveTile(TileView oldTile, TileView newTile) {
        TileView t = oldTile;
        TileView t2 = newTile;
        int index = tiles.indexOf(oldTile);
        if(index != -1)
            tiles.set(index, newTile);
    }


    private boolean lastDragMovedAtLeastHalfWay() {
        if (lastDragPoint != null && currentMotionDescriptors != null && currentMotionDescriptors.size() > 0) {
            GameTileMotionDescriptor firstMotionDescriptor = currentMotionDescriptors.get(0);
            if (firstMotionDescriptor.axialDelta > tileSize / 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detects click - either true click (no drags) or small involuntary drag
     *
     * @return Whether last gesture was a click
     */
    private boolean isClick() {
        if (lastDragPoint == null) {
            return true; // no drags
        }
        // just small amount of MOVE events counts as click
        if (currentMotionDescriptors != null && currentMotionDescriptors.size() > 0 && movedTile.numberOfDrags < 10) {
            GameTileMotionDescriptor firstMotionDescriptor = currentMotionDescriptors.get(0);
            // just very small drag counts as click
            if (firstMotionDescriptor.axialDelta < tileSize / 20) {
                return true;
            }
        }
        return false;
    }

    /**
     * Follows finger while dragging all currently moved tiles. Allows movement
     * only along x axis for row and y axis for column.
     *
     * @param event
     */
    private void followFinger(MotionEvent event) {
        boolean impossibleMove = true;
        float dxEvent = event.getRawX() - lastDragPoint.x;
        float dyEvent = event.getRawY() - lastDragPoint.y;
        TileView tile;
        movedTile.numberOfDrags++;
        for (GameTileMotionDescriptor descriptor : currentMotionDescriptors) {
            tile = descriptor.tile;
            Pair<Float, Float> xy = getXYFromEvent(tile, dxEvent, dyEvent, descriptor.direction);
            // detect if this move is valid
            RectF candidateRect = new RectF(xy.first, xy.second, xy.first + tile.getWidth(), xy.second
                    + tile.getHeight());
            ArrayList<TileView> tilesToCheck = null;
            if (tile.coordinate.row == emptyTile.coordinate.row) {
                tilesToCheck = allTilesInRow(tile.coordinate.row);
            } else if (tile.coordinate.column == emptyTile.coordinate.column) {
                tilesToCheck = allTilesInColumn(tile.coordinate.column);
            }

            boolean candidateRectInGameboard = (gameboardRect.contains(candidateRect));
            boolean collides = collidesWithTitles(candidateRect, tile, tilesToCheck);

            impossibleMove = impossibleMove && (!candidateRectInGameboard || collides);
        }
        if (!impossibleMove) {
            // perform the move for all moved tiles in the descriptors
            for (GameTileMotionDescriptor descriptor : currentMotionDescriptors) {
                tile = descriptor.tile;
                Pair<Float, Float> xy = getXYFromEvent(tile, dxEvent, dyEvent, descriptor.direction);
                tile.setXY(xy.first, xy.second);
            }
        }
    }

    /**
     * Computes new x,y coordinates for given tile in given direction (x or y).
     *
     * @param tile
     * @param dxEvent
     *            change of x coordinate from touch gesture
     * @param dyEvent
     *            change of y coordinate from touch gesture
     * @param direction
     *            x or y direction
     * @return pair of first x coordinates, second y coordinates
     */
    private Pair<Float, Float> getXYFromEvent(TileView tile, float dxEvent, float dyEvent, Direction direction) {
        float dxTile = 0, dyTile = 0;
        if (direction == Direction.X) {
            dxTile = tile.getXPos() + dxEvent;
            dyTile = tile.getYPos();
        }
        if (direction == Direction.Y) {
            dyTile = tile.getYPos() + dyEvent;
            dxTile = tile.getXPos();
        }
        return new Pair<>(dxTile, dyTile);
    }

    /**
     * @param candidateRect
     *            rectangle to check
     * @param tile
     *            tile belonging to rectangle
     * @param tilesToCheck
     *            list of tiles to check
     * @return Whether candidateRect collides with any tilesToCheck
     */
    private boolean collidesWithTitles(RectF candidateRect, TileView tile, ArrayList<TileView> tilesToCheck) {
        RectF otherTileRect;
        for (TileView otherTile : tilesToCheck) {
            if (!otherTile.isEmpty() && otherTile != tile) {
                otherTileRect = new RectF(otherTile.getXPos(), otherTile.getYPos(), otherTile.getXPos()
                        + otherTile.getWidth(), otherTile.getYPos() + otherTile.getHeight());
                if (RectF.intersects(otherTileRect, candidateRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Performs animation of currently moved tiles into empty space. Happens
     * when valid tile is clicked or is dragged over 50%.
     */
    private void animateTilesToEmptySpace() {
        emptyTile.setXY(movedTile.getXPos(), movedTile.getYPos());
        emptyTile.coordinate = movedTile.coordinate;
        ObjectAnimator animator;
        for (final GameTileMotionDescriptor motionDescriptor : currentMotionDescriptors) {
            animator = ObjectAnimator.ofObject(motionDescriptor.tile, motionDescriptor.direction.toString(),
                    new FloatEvaluator(), motionDescriptor.from, motionDescriptor.to);
            animator.setDuration(16);
            animator.addListener(new Animator.AnimatorListener() {

                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    motionDescriptor.tile.coordinate = motionDescriptor.finalPoint;
                    motionDescriptor.tile.setXY(motionDescriptor.finalRect.left, motionDescriptor.finalRect.top);
                }
            });
            animator.start();
        }
    }

    /**
     * Performs animation of currently moved tiles back to origin. Happens when
     * the drag was less than 50%.
     */
    private void animateTilesBackToOrigin() {
        ObjectAnimator animator;
        if (currentMotionDescriptors != null) {
            for (final GameTileMotionDescriptor motionDescriptor : currentMotionDescriptors) {
                animator = ObjectAnimator.ofObject(motionDescriptor.tile, motionDescriptor.direction.toString(),
                        new FloatEvaluator(), motionDescriptor.currentPosition(), motionDescriptor.originalPosition());
                animator.setDuration(16);
                animator.addListener(new Animator.AnimatorListener() {

                    public void onAnimationStart(Animator animation) {
                    }

                    public void onAnimationCancel(Animator animation) {
                    }

                    public void onAnimationRepeat(Animator animation) {
                    }

                    public void onAnimationEnd(Animator animation) {
                        motionDescriptor.tile.setXY(motionDescriptor.originalRect.left,
                                motionDescriptor.originalRect.top);
                    }
                });
                animator.start();
            }
        }
    }

    /**
     * @param row
     *            number of row
     * @return list of tiles in the row
     */
    private ArrayList<TileView> allTilesInRow(int row) {
        ArrayList<TileView> tilesInRow = new ArrayList<>();
        for (TileView tile : tiles) {
            if (tile.coordinate.row == row) {
                tilesInRow.add(tile);
            }
        }
        return tilesInRow;
    }

    /**
     * @param column
     *            number of column
     * @return list of tiles in the column
     */
    private ArrayList<TileView> allTilesInColumn(int column) {
        ArrayList<TileView> tilesInColumn = new ArrayList<TileView>();
        for (TileView tile : tiles) {
            if (tile.coordinate.column == column) {
                tilesInColumn.add(tile);
            }
        }
        return tilesInColumn;
    }

    /**
     * Finds tiles between checked tile and empty tile and initializes motion
     * descriptors for those tiles.
     *
     * @param tile
     *            A tile to be checked
     * @return list of tiles between checked tile and empty tile
     */
    private ArrayList<GameTileMotionDescriptor> getTilesBetweenEmptyTileAndTile(TileView tile) {
        ArrayList<GameTileMotionDescriptor> descriptors = new ArrayList<>();
        Point coordinate, finalPoint;
        TileView foundTile;
        GameTileMotionDescriptor motionDescriptor;
        Rect finalRect, currentRect;
        float axialDelta;
        if (tile.isToRightOf(emptyTile)) {
            // add all tiles left of the tile
            for (int i = tile.coordinate.column; i > emptyTile.coordinate.column; i--) {
                coordinate = new Point(tile.coordinate.row, i);
                foundTile = (tile.coordinate.matches(coordinate)) ? tile : getTileAtPoint(coordinate);
                finalPoint = new Point(tile.coordinate.row, i - 1);
                currentRect = rectFromPoint(foundTile.coordinate);
                finalRect = rectFromPoint(finalPoint);
                axialDelta = Math.abs(foundTile.getXPos() - currentRect.left);
                motionDescriptor = new GameTileMotionDescriptor(foundTile, Direction.X, foundTile.getXPos(),
                        finalRect.left);
                motionDescriptor.finalPoint = finalPoint;
                motionDescriptor.finalRect = finalRect;
                motionDescriptor.axialDelta = axialDelta;
                descriptors.add(motionDescriptor);
            }
        } else if (tile.isToLeftOf(emptyTile)) {
            // add all tiles right of the tile
            for (int i = tile.coordinate.column; i < emptyTile.coordinate.column; i++) {
                coordinate = new Point(tile.coordinate.row, i);
                foundTile = (tile.coordinate.matches(coordinate)) ? tile : getTileAtPoint(coordinate);
                finalPoint = new Point(tile.coordinate.row, i + 1);
                currentRect = rectFromPoint(foundTile.coordinate);
                finalRect = rectFromPoint(finalPoint);
                axialDelta = Math.abs(foundTile.getXPos() - currentRect.left);
                motionDescriptor = new GameTileMotionDescriptor(foundTile, Direction.X, foundTile.getXPos(),
                        finalRect.left);
                motionDescriptor.finalPoint = finalPoint;
                motionDescriptor.finalRect = finalRect;
                motionDescriptor.axialDelta = axialDelta;
                descriptors.add(motionDescriptor);
            }
        } else if (tile.isAbove(emptyTile)) {
            // add all tiles bellow the tile
            for (int i = tile.coordinate.row; i < emptyTile.coordinate.row; i++) {
                coordinate = new Point(i, tile.coordinate.column);
                foundTile = (tile.coordinate.matches(coordinate)) ? tile : getTileAtPoint(coordinate);
                finalPoint = new Point(i + 1, tile.coordinate.column);
                currentRect = rectFromPoint(foundTile.coordinate);
                finalRect = rectFromPoint(finalPoint);
                axialDelta = Math.abs(foundTile.getYPos() - currentRect.top);
                motionDescriptor = new GameTileMotionDescriptor(foundTile, Direction.Y, foundTile.getYPos(),
                        finalRect.top);
                motionDescriptor.finalPoint = finalPoint;
                motionDescriptor.finalRect = finalRect;
                motionDescriptor.axialDelta = axialDelta;
                descriptors.add(motionDescriptor);
            }
        } else if (tile.isBelow(emptyTile)) {
            // add all tiles above the tile
            for (int i = tile.coordinate.row; i > emptyTile.coordinate.row; i--) {
                coordinate = new Point(i, tile.coordinate.column);
                foundTile = (tile.coordinate.matches(coordinate)) ? tile : getTileAtPoint(coordinate);
                finalPoint = new Point(i - 1, tile.coordinate.column);
                currentRect = rectFromPoint(foundTile.coordinate);
                finalRect = rectFromPoint(finalPoint);
                axialDelta = Math.abs(foundTile.getYPos() - currentRect.top);
                motionDescriptor = new GameTileMotionDescriptor(foundTile, Direction.Y, foundTile.getYPos(),
                        finalRect.top);
                motionDescriptor.finalPoint = finalPoint;
                motionDescriptor.finalRect = finalRect;
                motionDescriptor.axialDelta = axialDelta;
                descriptors.add(motionDescriptor);
            }
        }
        return descriptors;
    }

    /**
     * @param coordinate
     *            coordinate of the tile
     * @return tile at given coordinate
     */
    private TileView getTileAtPoint(Point coordinate) {
        for (TileView tile : tiles) {
            if (tile.coordinate.matches(coordinate)) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Describes movement of the tile. It is used to move several tiles at once.
     */
    public class GameTileMotionDescriptor {

        public Rect finalRect, originalRect;
        public Direction direction; // x or y
        public TileView tile;
        public float from, to, axialDelta;
        public Point finalPoint;

        public GameTileMotionDescriptor(TileView tile, Direction direction, float from, float to) {
            super();
            this.tile = tile;
            this.from = from;
            this.to = to;
            this.direction = direction;
            this.originalRect = rectFromPoint(tile.coordinate);
        }

        /**
         * @return current position of the tile
         */
        public float currentPosition() {
            if (direction == Direction.X) {
                return tile.getXPos();
            } else if (direction == Direction.Y) {
                return tile.getYPos();
            }
            return 0;
        }

        /**
         * @return original position of the tile. It is used in movement to
         *         original position.
         */
        public float originalPosition() {
            if (direction == Direction.X) {
                return originalRect.left;
            } else if (direction == Direction.Y) {
                return originalRect.top;
            }
            return 0;
        }

    }
}
