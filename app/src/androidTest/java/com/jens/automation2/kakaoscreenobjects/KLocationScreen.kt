package com.jens.automation2.kakaoscreenobjects

import com.jens.automation2.R
import com.jens.automation2.kespresso.KEspressoExtended.actionFindText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class KLocationScreen : Screen<KLocationScreen>() {
    private val deleteLocationButton = KTextView { withText(R.string.deleteCapital) }
    private val addLocationButton = KButton { withId(R.id.bAddPoi) }

    fun kActionClickOnAddLocationButton() =
        addLocationButton.click()

    fun kAssertLocationIsCreatedByName(name: String) =
        actionFindText(name).hasText(name)

    fun kActionDeleteLocationByName(name: String) {
        actionFindText(name).invoke {
            hasText(name)
            longClick()
        }
        deleteLocationButton.click()
    }
}