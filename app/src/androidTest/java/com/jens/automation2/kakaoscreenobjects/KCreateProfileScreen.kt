package com.jens.automation2.kakaoscreenobjects

import com.jens.automation2.R
import com.jens.automation2.kespresso.KEspressoExtended.actionFindText
import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.progress.KSeekBar
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class KCreateProfileScreen : Screen<KCreateProfileScreen>() {
    private val soundModeNormal = "Normal"
    private val profileNameField = KEditText { withId(R.id.etName) }
    private val changeSoundBox = KCheckBox { withId(R.id.checkBoxChangeSoundMode) }
    private val spinnerSound = KButton { withId(R.id.spinnerSoundMode) }
    private val volumeBox = KCheckBox { withId(R.id.checkBoxChangeVolumeNotifications) }
    private val seekBarVolume = KSeekBar { withId(R.id.seekBarVolumeNotifications) }
    private val saveProfile = KButton { withId(R.id.bSaveProfile) }

    fun kActionEnterProfileName(profileName: String) {
        profileNameField {
            clearText()
            typeText(profileName)
        }
        closeSoftKeyboard()
    }

    fun kActionChangeSoundMode() {
        changeSoundBox.setChecked(true)
        spinnerSound.click()
        actionFindText(soundModeNormal).click()
    }

    fun kActionChangeVolume() {
        volumeBox {
            scrollTo()
            click()
        }
        seekBarVolume.setProgress(3)
    }

    fun kActionSaveProfile() {
        saveProfile {
            scrollTo()
            click()
        }
    }
}