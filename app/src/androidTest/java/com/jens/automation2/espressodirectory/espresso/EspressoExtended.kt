package com.jens.automation2.espressodirectory.espresso

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingRootException
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher

object EspressoExtended {
    const val DEFAULT_TIMEOUT_MILLS = 10_000L
    const val DEFAULT_INTERVAL_MILLS = 250L

    fun actionFindElement(resource: Matcher<View>): ViewInteraction {
        return onView(resource)
    }

    fun actionWaitForView(
        matcher: Matcher<View>,
        timeoutMills: Long = DEFAULT_TIMEOUT_MILLS,
        intervalMills: Long = DEFAULT_INTERVAL_MILLS
    ): ViewInteraction {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeoutMills

        var lastException: Throwable

        do {
            try {
                return onView(matcher).check(matches(isDisplayed()))
            } catch (e: NoMatchingRootException) {
                lastException = e
                Thread.sleep(intervalMills)
            } catch (e: NoMatchingViewException) {
                lastException = e
                Thread.sleep(intervalMills)
            } catch (e: AssertionFailedError) {
                lastException = e
                Thread.sleep(intervalMills)
            }
        } while (System.currentTimeMillis() < endTime)

        throw lastException
    }
}