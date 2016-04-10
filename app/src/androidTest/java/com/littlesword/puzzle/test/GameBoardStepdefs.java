package com.littlesword.puzzle.test;

import android.test.ActivityInstrumentationTestCase2;

import com.littlesword.puzzle.R;
import com.littlesword.puzzle.customview.TileView;
import com.littlesword.puzzle.mainscreen.MainActivity;
import com.littlesword.puzzle.model.Point;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;
import static com.littlesword.puzzle.test.util.TestUtils.checkTileInPosition;

/**
 * Created by kongw1 on 9/04/16.
 */
@CucumberOptions(features = "features")
public class GameBoardStepdefs extends ActivityInstrumentationTestCase2<MainActivity>{
    private MainActivity mActivity;

    public GameBoardStepdefs() {
        super(MainActivity.class);
    }

    @Given("^I open the puzzle app$")
    public void iOpenThePuzzleApp() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        mActivity = getActivity();
        assertThat(mActivity).isNotNull();
    }

    @When("^I click on the available tile (\\d+)$")
    public void iClickOnTheAvailableTileIndex(final int index) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        onView(withId(R.id.tileView + index)).perform(click()).check(matches(isDisplayed()));
    }

    @When("^I swipe right on the available tile set (\\d+)$")
    public void iDragOnTheAvailableTileSetIndex(int index) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        onView(withId(R.id.tileView + index)).perform(swipeRight());
    }

    @When("^I swipe down on the available tile set (\\d+)$")
    public void iSwipeUpOnTheAvailableTileSet(int index) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        onView(withId(R.id.tileView + index)).perform(swipeDown());

    }

    @Then("^empty tile will move to (\\d+) (\\d+)$")
    public void emptyTileWillMoveToPosition(int x, int y) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        TileView view = (TileView) mActivity.findViewById(R.id.tileView + 15);//the empty tile
        checkTileInPosition(view, new Point(x,y));
    }
}
