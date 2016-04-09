package com.littlesword.puzzle.model;

/**
 * Created by kongw1 on 8/04/16.
 */
public class Point {
    public int row;
    public int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean matches(Point Point) {
        return Point.row == row && Point.column == column;
    }

    public boolean sharesAxisWith(Point Point) {
        return (row == Point.row || column == Point.column);
    }

    public boolean isToRightOf(Point Point) {
        return sharesAxisWith(Point) && (column > Point.column);
    }

    public boolean isToLeftOf(Point Point) {
        return sharesAxisWith(Point) && (column < Point.column);
    }

    public boolean isAbove(Point Point) {
        return sharesAxisWith(Point) && (row < Point.row);
    }

    public boolean isBelow(Point Point) {
        return sharesAxisWith(Point) && (row > Point.row);
    }

    @Override
    public String toString() {
        return "Row: " + row + " Column:"+column+"]";
    }
}
