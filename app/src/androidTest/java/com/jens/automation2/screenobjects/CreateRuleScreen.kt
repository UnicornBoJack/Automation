package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import com.jens.automation2.matchers.actionItemWithName
import org.hamcrest.Matcher

object CreateRuleScreen {
    private const val SAVE_RULE = "Save Rule"

    private val ruleNameTextField: Matcher<View> = withId(R.id.etRuleName)
    private val addTriggerButton: Matcher<View> = withId(R.id.cmdTriggerAdd)
    private val addAction: Matcher<View> = withId(R.id.cmdActionAdd)

    fun actionTypeRuleName(text: String) {
        actionWaitForView(ruleNameTextField).perform(
            clearText(), TypeTextAction(text),
            closeSoftKeyboard()
        )
    }

    fun actionClickAddTriggerButton() {
        actionWaitForView(addTriggerButton).perform(click())
    }

    fun actionChooseTriggerWithName(itemName: String) {
        onData(actionItemWithName(itemName)).perform(click())
    }

    fun actionAddAction(itemInActionList: String) {
        actionWaitForView(addAction).perform(click())
        onData(actionItemWithName(itemInActionList)).perform(click())
        actionWaitForView(withText("Ok")).perform(click())
        actionWaitForView(withText("Activate")).perform(click())
    }

    fun actionSaveRule(): ViewInteraction {
        return actionWaitForView(withText(SAVE_RULE)).perform(click())
    }

    fun assertionCheckTriggerNameInList(text: String): ViewInteraction {
        return actionWaitForView(withText(text)).check(matches(isDisplayed()))
    }

    fun assertionCheckActionNameInList(text: String): ViewInteraction {
        return actionWaitForView(withText(text)).check(matches(isDisplayed()))
    }

}