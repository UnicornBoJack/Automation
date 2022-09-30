package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import org.hamcrest.Matcher

object CreateLocationScreen {
    private const val B_OK = "Ok"
    private const val B_YES = "Yes"

    private val locationNameTextField: Matcher<View> = ViewMatchers.withId(R.id.etPoiName)
    private val getCurrentLocation: Matcher<View> = ViewMatchers.withId(R.id.bGetPosition)
    private val saveLocation: Matcher<View> = ViewMatchers.withId(R.id.bSavePoi)

    fun actionTypeLocationName(name: String) {
        actionWaitForView(locationNameTextField).perform(
            clearText(), TypeTextAction(name), closeSoftKeyboard()
        )
    }

    fun actionClickOnCurrentLocationButton() {
        actionWaitForView(getCurrentLocation).perform(click())
    }

    fun clickOnOkButton() {
        actionWaitForView(withText(B_OK)).perform(click())
    }

    fun waitAndClickOnYesBtn() {
        actionWaitForView(withText(B_YES)).perform(click())
    }

    fun clickOnYesButton() {
        actionWaitForView(withText(B_YES)).perform(click())
    }

    fun actionClickOnSaveLocationButton() {
        actionWaitForView(saveLocation).perform(click())
    }
}