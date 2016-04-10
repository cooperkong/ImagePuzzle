package com.littlesword.puzzle.test.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.view.ViewGroup;

import com.littlesword.puzzle.customview.TileView;
import com.littlesword.puzzle.model.Point;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by kongw1 on 9/04/16.
 */
public class TestUtils {
    /**
     * Match the exact number of child view in a view group.
     * @param numChildrenMatcher
     * @return
     */
    public static Matcher<View> hasExactChildren(final Matcher<Integer> numChildrenMatcher) {
        return new TypeSafeMatcher<View>() {
            /**
             * matching with view group.getChildCount()
             */
            @Override
            public boolean matchesSafely(View view) {
                return view instanceof ViewGroup && numChildrenMatcher.matches(((ViewGroup)view).getChildCount());
            }

            /**
             * gets the description
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" a view with # children is ");
                numChildrenMatcher.describeTo(description);
            }
        };
    }

    /**
     * Call to rotate device screen
     */
    public static void rotateScreen(Activity activity) {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Verify if tile is in desire position
     * @param view
     * @param point
     */
    public static void checkTileInPosition(TileView view, Point point){
        assertThat(view.coordinate.column).isEqualTo(point.column);
        assertThat(view.coordinate.row).isEqualTo(point.row);
    }
}
