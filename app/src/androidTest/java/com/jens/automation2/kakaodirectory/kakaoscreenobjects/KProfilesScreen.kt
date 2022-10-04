package com.jens.automation2.kakaodirectory.kakaoscreenobjects

import com.jens.automation2.R
import com.jens.automation2.kakaodirectory.kespresso.KEspressoExtended.actionFindText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class KProfilesScreen : Screen<KProfilesScreen>() {
    private val deleteProfileButton = KTextView { withText(R.string.deleteCapital) }
    private val addProfileButton = KButton { withId(R.id.bAddProfile) }

    fun kActionClickOnAddProfile() =
        addProfileButton.click()

    fun kAssertionCheckCreatedProfile(profileName: String) =
        actionFindText(profileName).hasText(profileName)

    fun kActionDeleteProfileByName(profileName: String) {
        actionFindText(profileName).invoke {
            hasText(profileName)
            longClick()
        }
        deleteProfileButton.click()
    }
}