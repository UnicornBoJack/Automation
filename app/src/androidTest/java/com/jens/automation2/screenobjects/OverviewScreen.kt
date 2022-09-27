package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import com.jens.automation2.matchers.ToastMatcher.Companion.onToast
import org.hamcrest.Matcher

object OverviewScreen : WithTabs {
    private val serviceButton: Matcher<View> = ViewMatchers.withId(R.id.tbArmMastListener)

    fun actionClickOnServiceButton() {
        actionWaitForView(serviceButton).perform(click())
    }

    fun assertionIsTextExist(name: String) {
        actionWaitForView(serviceButton).check(matches(withText(name)))
    }

    fun assertIsToastMessagePresent(message: String) {
        onToast(message).check(matches(isDisplayed()))
    }
}