package com.jens.automation2.screenobjects

import android.R
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

interface WithTabs {
    companion object {
        private const val LOCATION = "Locations"
        private const val PROFILE = "Profiles"
        private const val RULES = "Rules"
        private const val OVERVIEW = "Overview"

        val dashboard: Matcher<View> = withId(R.id.tabs)
    }

    fun actionOpenRules() {
        onView(allOf(hasDescendant(withText(RULES)), withParent(dashboard))).perform(click())
    }

    fun actionOpenProfile() {
        onView(allOf(hasDescendant(withText(PROFILE)), withParent(dashboard))).perform(click())
    }

    fun actionOpenLocationTab() {
        onView(allOf(hasDescendant(withText(LOCATION)), withParent(dashboard))).perform(click())
    }

    fun actionOpenOverviewTab() {
        onView(allOf(hasDescendant(withText(OVERVIEW)), withParent(dashboard))).perform(click())
    }
}