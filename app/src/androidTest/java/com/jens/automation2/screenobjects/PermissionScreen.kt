package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import com.jens.automation2.matchers.actionClickOnText
import org.hamcrest.Matcher


object PermissionScreen : WithTabs {
    private val requestPermissions: Matcher<View> = withId(R.id.bRequestPermissions)

    fun allowPermissions() {
        actionWaitForView(requestPermissions).perform(click())
        actionClickOnText("Allow")
        actionWaitForView(withText("Yes")).perform(click())
    }

    fun actionClickContinueBtn() {
        actionWaitForView(requestPermissions).perform(click())
    }

    fun whileUsingTheApp(text: String) {
        actionClickOnText(text)
    }
}