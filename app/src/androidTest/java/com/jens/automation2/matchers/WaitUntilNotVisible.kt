package com.jens.automation2.matchers

import androidx.test.espresso.NoMatchingRootException
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import junit.framework.AssertionFailedError
import java.util.concurrent.TimeoutException

fun ViewInteraction.waitForViewDoesNotExist(timeout: Long, text: String): ViewInteraction {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeout

    var lastException: Throwable?

    do {
        try {
            assertPopupIsNotDisplayed(text)
            return this
        } catch (e: NoMatchingRootException) {
            return this
        } catch (e: NoMatchingViewException) {
            lastException = e
            Thread.sleep(50)
        } catch (e: AssertionFailedError) {
            lastException = e
            Thread.sleep(50)
        }
    } while (System.currentTimeMillis() < endTime)

    throw lastException ?: TimeoutException("Element still on View")
}