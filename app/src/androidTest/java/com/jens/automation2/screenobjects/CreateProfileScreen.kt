package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionFindElement
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import com.jens.automation2.matchers.setProgress
import org.hamcrest.Matcher

object CreateProfileScreen {
    private const val NORMAL_MODE = "Normal"
    private val profileNameField: Matcher<View> = withId(R.id.etName)
    private val changeSoundBox: Matcher<View> = withId(R.id.checkBoxChangeSoundMode)
    private val spinnerSound: Matcher<View> = withId(R.id.spinnerSoundMode)
    private val volumeBox: Matcher<View> = withId(R.id.checkBoxChangeVolumeNotifications)
    private val seekBarVolume: Matcher<View> = withId(R.id.seekBarVolumeNotifications)
    private val saveProfile: Matcher<View> = withId(R.id.bSaveProfile)

    //TODO Enter the name of the rule etName;
    fun actionEnterProfileName(profileName: String) {
        actionWaitForView(profileNameField).perform(
            clearText(), TypeTextAction(profileName),
            closeSoftKeyboard()
        )
    }

    //TODO Change sound mode; checkBoxChangeSoundMode (choose - Normal) spinnerSoud
    fun actionChangeSoundMode() {
        actionWaitForView(changeSoundBox).perform(click())
        actionWaitForView(spinnerSound).perform(click())
        actionWaitForView(withText(NORMAL_MODE)).perform(click())
    }

    //TODO Change volumes (Ringtone and notifications); checkBoxChangeVolumeNotification (seekBarVolumeNotif)
    fun actionChangeVolume() {
        actionFindElement(volumeBox).perform(scrollTo(), click())
        actionFindElement(seekBarVolume).perform(setProgress(7))

    }

    //TODO Save the profile; bSaveProfile
    fun actionSaveProfile() {
        actionFindElement(saveProfile).perform(scrollTo(), click())
    }
}