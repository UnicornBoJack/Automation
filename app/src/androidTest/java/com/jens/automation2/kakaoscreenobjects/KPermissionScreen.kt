package com.jens.automation2.kakaoscreenobjects

import com.jens.automation2.R
import com.jens.automation2.matchers.actionClickOnText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class KPermissionScreen : Screen<KPermissionScreen>() {
    private val requestPermissions = KButton { withId(R.id.bRequestPermissions) }
    private val yes = KButton { withText("Yes") }
    private val allow = "Allow"

    fun kAllowPermissions() {
        requestPermissions.click()
        actionClickOnText(allow)
        yes.click()
    }

    fun kActionClickContinueBtn() =
        requestPermissions.click()

    fun kActionClickOnWhileUsingTheApp(text: String) =
        actionClickOnText(text)
}

