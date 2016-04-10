package com.littlesword.puzzle.test;

import android.test.ActivityInstrumentationTestCase2;

import com.littlesword.puzzle.R;
import com.littlesword.puzzle.gameboard.GameBoardPresenter;
import com.littlesword.puzzle.mainscreen.MainActivity;

import cucumber.api.CucumberOptions;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;
import static com.littlesword.puzzle.test.util.TestUtils.hasExactChildren;
import static com.littlesword.puzzle.test.util.TestUtils.rotateScreen;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by kongw1 on 8/04/16.
 */
@CucumberOptions(features = "features")
public class MainActivitySteps extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mActivity;

    public MainActivitySteps() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Given("^I am on home screen$")
    public void iAmOnHomeScreen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // always good!
    }

    @When("^I open puzzle app$")
    public void iOpenPuzzleApp() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        mActivity = getActivity();
        assertThat(mActivity).isNotNull();
    }

    @Then("^I see the main game board$")
    public void iSeeTheMainGameBoard() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //verify main game board is displayed
        onView(withId(R.id.game_board)).check(matches(isDisplayed()));
    }

    @Then("^I see the (\\d+)x(\\d+) game board$")
    public void iSeeTheXGameBoard(int arg0, int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertThat(GameBoardPresenter.GRID_SIZE).isEqualTo(arg0);//4 x 4 grid size for game board
        assertThat(GameBoardPresenter.GRID_SIZE).isEqualTo(arg1);//4 x 4 grid size for game board

        onView(withId(R.id.game_board)).check(matches(isDisplayed()));
        //grids should match the views
        onView(withId(R.id.game_board)).check(matches(hasExactChildren(is(arg0 * arg1))));
        //error scenario check, make sure the test is valid by test again an odd number
        onView(withId(R.id.game_board)).check(matches(not(hasExactChildren(is(13)))));



    }

    @And("^I rotate the device$")
    public void iRotateTheDevice() throws Throwable {
        // rotate device
        rotateScreen(getActivity());
        // app still shows the main board
        onView(withId(R.id.game_board)).check(matches(isDisplayed()));
    }
}
