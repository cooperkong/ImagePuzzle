package com.littlesword.puzzle.test;

import android.test.ActivityInstrumentationTestCase2;

import com.littlesword.puzzle.mainscreen.MainActivity;

import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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

    @When("^I click on the available tile$")
    public void iClickOnTheAvailableTile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the tile will move into position$")
    public void theTileWillMoveIntoPosition() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
