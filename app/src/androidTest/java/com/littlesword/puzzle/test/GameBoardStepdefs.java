package com.littlesword.puzzle.test;

import android.test.ActivityInstrumentationTestCase2;

import com.littlesword.puzzle.R;
import com.littlesword.puzzle.customview.TileView;
import com.littlesword.puzzle.mainscreen.MainActivity;
import com.littlesword.puzzle.model.Point;
import com.littlesword.puzzle.test.util.TestUtils;

import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;

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

    @Then("^the tile will move into position$")
    public void theTileWillMoveIntoPosition() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^I drag on the available tile set$")
    public void iDragOnTheAvailableTileSet() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^the available tiles will move vertically$")
    public void theAvailableTilesWillMoveVertically() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^the available tiles will move horizontally$")
    public void theAvailableTilesWillMoveHorizontally() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^the tile order will remain the same$")
    public void theTileOrderWillRemainTheSame() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^I click on the empty$")
    public void iClickOnTheEmpty() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("^I click on the available tile (\\d+)$")
    public void iClickOnTheAvailableTileIndex(final int index) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        Point destination = new Point(3,3);//bottom right corner
        onView(withId(R.id.tileView + index)).perform(click()).check(matches(isDisplayed()));
        TestUtils.checkTileInPosition((TileView) mActivity.findViewById(R.id.tileView + index), destination);
    }
}
