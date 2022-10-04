package com.jens.automation2.kakaoscreenobjects

import com.jens.automation2.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class KCreateLocationScreen : Screen<KCreateLocationScreen>() {
    private val okBtn = KTextView { withText(R.string.ok) }
    private val yesBtn = KTextView { withText("Yes") }
    private val locationNameTextField = KEditText { withId(R.id.etPoiName) }
    private val getCurrentLocation = KButton { withId(R.id.bGetPosition) }
    private val saveLocation = KButton { withId(R.id.bSavePoi) }

    fun kActionTypeLocationName(name: String) {
        locationNameTextField {
            clearText()
            typeText(name)
        }
        closeSoftKeyboard()
    }

    fun kActionClickOnCurrentLocationButton() =
        getCurrentLocation.click()

    fun kActionClickOnOkButton() =
        okBtn.click()

    fun kActionClickOnYesButton() =
        yesBtn.click()

    fun kActionClickOnSaveLocationButton() =
        saveLocation.click()
}