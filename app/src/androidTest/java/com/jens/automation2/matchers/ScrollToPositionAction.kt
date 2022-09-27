package com.jens.automation2.matchers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ScrollToPositionAction(private val position: Int) : ViewAction {
    override fun getDescription(): String {
        return "Scroll RecyclerView to position: $position"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        recyclerView.layoutManager?.smoothScrollToPosition(
            recyclerView, null, position
        )
        Thread.sleep(500)
        uiController?.loopMainThreadUntilIdle()
    }
}