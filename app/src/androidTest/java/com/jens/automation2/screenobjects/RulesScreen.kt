package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import org.hamcrest.Matcher

object RulesScreen : WithTabs {
    private val addRuleButton: Matcher<View> = withId(R.id.bAddRule)
    private val deleteRuleButton: Matcher<View> = withText("Delete")
    private val ruleNameInList: Matcher<View> = withId(R.id.tvRuleName)

    fun actionClickAddRuleButton() {
        actionWaitForView(addRuleButton).perform(click())
    }

    fun assertRuleIsCreatedByName(name: String) {
        actionWaitForView(withText(name)).perform(click())
            .check(matches(withText(name)))
    }

    fun actionDeleteRuleByName(name: String) {
        actionWaitForView(ruleNameInList)
            .check(matches(withText(name))).perform(longClick())

        actionWaitForView(deleteRuleButton)
            .perform(click())
    }
}