package com.jens.automation2.kakaodirectory.kakaoscreenobjects

import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.jens.automation2.R
import com.jens.automation2.espressodirectory.matchers.ToastMatcher.Companion.onToast
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class KOverviewScreen : Screen<KOverviewScreen>() {
    private val serviceButton = KButton { withId(R.id.tbArmMastListener) }

    fun kActionClickOnServiceButton() {
        serviceButton.click()
    }

    fun kAssertionIsTextExist(name: String) {
        serviceButton.hasText(name)
    }

    fun kAssertIsToastMessagePresent(toastMessage: String) {
        onToast(toastMessage).check(matches(isDisplayed()))
    }

    fun kAssertIsToastMessageDoesNotExist(toastMessage: String) {
        onToast(toastMessage).check(doesNotExist())
    }
}