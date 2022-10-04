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

object ProfilesScreen : WithTabs {
    private val deleteProfileButton: Matcher<View> = withText("Delete")
    private val addProfileButton: Matcher<View> = ViewMatchers.withId(R.id.bAddProfile)

    fun actionClickOnAddProfile() {
        actionWaitForView(addProfileButton).perform(click())
    }

    fun assertionCheckCreatedProfile(profileName: String) {
        actionWaitForView(withText(profileName)).check(matches(isDisplayed()))
    }

    fun actionDeleteProfileByName(profileName: String) {
        actionWaitForView(withText(profileName)).perform(longClick())
        actionWaitForView(deleteProfileButton).perform(click())
    }
}