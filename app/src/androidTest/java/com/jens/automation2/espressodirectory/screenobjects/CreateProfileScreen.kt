package com.jens.automation2.espressodirectory.screenobjects

import android.view.View
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espressodirectory.espresso.EspressoExtended.actionFindElement
import com.jens.automation2.espressodirectory.espresso.EspressoExtended.actionWaitForView
import com.jens.automation2.espressodirectory.matchers.setProgress
import org.hamcrest.Matcher

object CreateProfileScreen {
    private const val NORMAL_MODE = "Normal"
    private val profileNameField: Matcher<View> = withId(R.id.etName)
    private val changeSoundBox: Matcher<View> = withId(R.id.checkBoxChangeSoundMode)
    private val spinnerSound: Matcher<View> = withId(R.id.spinnerSoundMode)
    private val volumeBox: Matcher<View> = withId(R.id.checkBoxChangeVolumeNotifications)
    private val seekBarVolume: Matcher<View> = withId(R.id.seekBarVolumeNotifications)
    private val saveProfile: Matcher<View> = withId(R.id.bSaveProfile)

    fun actionEnterProfileName(profileName: String) {
        actionWaitForView(profileNameField).perform(
            clearText(), TypeTextAction(profileName), closeSoftKeyboard()
        )
    }

    fun actionChangeSoundMode() {
        actionWaitForView(changeSoundBox).perform(click())
        actionWaitForView(spinnerSound).perform(click())
        actionWaitForView(withText(NORMAL_MODE)).perform(click())
    }

    fun actionChangeVolume() {
        actionFindElement(volumeBox).perform(scrollTo(), click())
        actionFindElement(seekBarVolume).perform(setProgress(7))

    }

    fun actionSaveProfile() {
        actionFindElement(saveProfile).perform(scrollTo(), click())
    }
}