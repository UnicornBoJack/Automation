package com.jens.automation2.kakaoscreenobjects

import android.view.View
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import com.jens.automation2.R
import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.picker.time.KTimePicker
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import org.hamcrest.Matcher

class KCreateTimeframeScreen : Screen<KCreateTimeframeScreen>() {
    val monday = KCheckBox{withId(R.id.checkMonday)}
    val tuesday = KCheckBox{withId(R.id.checkTuesday)}
    val wednesday = KCheckBox{withId(R.id.checkWednesday)}
    val thursday = KCheckBox{withId(R.id.checkThursday)}
    val friday = KCheckBox{withId(R.id.checkFriday)}
    val repeat = KCheckBox{withId(R.id.chkRepeat)}
    val startDate = KTimePicker{withId(R.id.tpTimeFrameStart)}
    val endDate = KTimePicker{withId(R.id.tpTimeFrameStop)}
    val dateRadioButton = KButton{withId(R.id.radioTimeFrameLeaving)}
    val repeatEvery = KEditText{withId(R.id.etRepeatEvery)}
    val saveTimeFrame = KButton{withId(R.id.bSaveTimeFrame)}

    fun actionCreateTimeFrame(
        hoursStart: Int,
        minutesStart: Int,
        hoursEnd: Int,
        minutesEnd: Int,
        repeatEverySeconds: String
    ) {

        setStartDate(hoursStart, minutesStart)
        setEndDate(hoursEnd, minutesEnd)
        dateRadioButton.click()
        actionChooseAllDays()
        repeat{
            scrollTo()
            click()
        }
        repeatEvery{
            scrollTo()
            clearText()
            typeText(repeatEverySeconds)
            ViewActions.closeSoftKeyboard()
        }
        saveTimeFrame{
            scrollTo()
            click()
        }
    }

    private fun setStartDate(hoursStart: Int, minutesStart: Int) {
        startDate.setTime(hoursStart, minutesStart)
    }

    private fun setEndDate(hoursEnd: Int, minutesEnd: Int) {
        endDate.setTime(hoursEnd, minutesEnd)
    }

    private fun actionChooseAllDays() {
        friday {
            scrollTo()
            click()
        }
        thursday.click()
        wednesday.click()
        tuesday.click()
        monday.click()
    }
}