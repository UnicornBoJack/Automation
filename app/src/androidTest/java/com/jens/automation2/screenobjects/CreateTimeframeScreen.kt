package com.jens.automation2.screenobjects

import android.view.View
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.PickerActions.setTime
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.jens.automation2.R
import com.jens.automation2.espresso.EspressoExtended.actionFindElement
import com.jens.automation2.espresso.EspressoExtended.actionWaitForView
import org.hamcrest.Matcher

object CreateTimeframeScreen {
    private val monday: Matcher<View> = withId(R.id.checkMonday)
    private val tuesday: Matcher<View> = withId(R.id.checkTuesday)
    private val wednesday: Matcher<View> = withId(R.id.checkWednesday)
    private val thursday: Matcher<View> = withId(R.id.checkThursday)
    private val friday: Matcher<View> = withId(R.id.checkFriday)
    private val repeat: Matcher<View> = withId(R.id.chkRepeat)
    private val startDate: Matcher<View> = withId(R.id.tpTimeFrameStart)
    private val endDate: Matcher<View> = withId(R.id.tpTimeFrameStop)
    private val dateRadioButton: Matcher<View> = withId(R.id.radioTimeFrameLeaving)
    private val repeatEvery: Matcher<View> = withId(R.id.etRepeatEvery)
    private val saveTimeFrame: Matcher<View> = withId(R.id.bSaveTimeFrame)

    fun actionCreateTimeFrame(
        hoursStart: Int,
        minutesStart: Int,
        hoursEnd: Int,
        minutesEnd: Int,
        repeatEverySeconds: String
    ) {
        setStartDate(hoursStart, minutesStart)
        setEndDate(hoursEnd, minutesEnd)
        actionWaitForView(dateRadioButton).perform(click())
        actionChooseAllDays()
        actionFindElement(repeat).perform(scrollTo(), click())
        actionWaitForView(repeatEvery).perform(
            clearText(), TypeTextAction(repeatEverySeconds), closeSoftKeyboard()
        )
        actionWaitForView(saveTimeFrame).perform(click())
    }

    private fun setStartDate(hoursStart: Int, minutesStart: Int) {
        actionFindElement(startDate).perform(setTime(hoursStart, minutesStart))
    }

    private fun setEndDate(hoursEnd: Int, minutesEnd: Int) {
        actionFindElement(endDate).perform(setTime(hoursEnd, minutesEnd))
    }

    private fun actionChooseAllDays() {
        actionWaitForView(monday).perform(scrollTo(), click())
        actionFindElement(tuesday).perform(scrollTo(), click())
        actionFindElement(wednesday).perform(scrollTo(), click())
        actionFindElement(thursday).perform(scrollTo(), click())
        actionFindElement(friday).perform(scrollTo(), click())
    }
}