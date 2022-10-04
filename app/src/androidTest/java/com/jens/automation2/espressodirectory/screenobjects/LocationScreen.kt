package com.jens.automation2.espressodirectory.screenobjects

import android.view.View
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espressodirectory.espresso.EspressoExtended.actionWaitForView
import org.hamcrest.Matcher

object LocationScreen : WithTabs {
    private val deleteLocationButton: Matcher<View> = withText("Delete")
    private val addLocationButton: Matcher<View> = ViewMatchers.withId(R.id.bAddPoi)

    //TODO click add location
    fun actionClickOnAddLocationButton() {
        actionWaitForView(addLocationButton).perform(click())
    }

    //TODO assert location by name vPoiList
    fun assertLocationIsCreatedByName(name: String) {
        actionWaitForView(withText(name))
            .check(matches(isDisplayed()))
    }

    //TODO delete location by name
    fun actionDeleteLocationByName(name: String) {
        actionWaitForView(withText(name)).perform(longClick())

        actionWaitForView(deleteLocationButton)
            .perform(click())
    }
}