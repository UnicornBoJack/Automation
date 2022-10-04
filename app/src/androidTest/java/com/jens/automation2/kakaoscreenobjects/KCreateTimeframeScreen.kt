package com.jens.automation2.kakaoscreenobjects

import com.jens.automation2.R
import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.picker.time.KTimePicker
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class KCreateTimeframeScreen : Screen<KCreateTimeframeScreen>() {
    private val monday = KCheckBox { withId(R.id.checkMonday) }
    private val tuesday = KCheckBox { withId(R.id.checkTuesday) }
    private val wednesday = KCheckBox { withId(R.id.checkWednesday) }
    private val thursday = KCheckBox { withId(R.id.checkThursday) }
    private val friday = KCheckBox { withId(R.id.checkFriday) }
    private val repeat = KCheckBox { withId(R.id.chkRepeat) }
    private val startDate = KTimePicker { withId(R.id.tpTimeFrameStart) }
    private val endDate = KTimePicker { withId(R.id.tpTimeFrameStop) }
    private val dateRadioButton = KButton { withId(R.id.radioTimeFrameLeaving) }
    private val repeatEvery = KEditText { withId(R.id.etRepeatEvery) }
    private val saveTimeFrame = KButton { withId(R.id.bSaveTimeFrame) }

    fun actionCreateTimeFrame(
        hoursStart: Int,
        minutesStart: Int,
        hoursEnd: Int,
        minutesEnd: Int,
        repeatStatus: Boolean,
        repeatEverySeconds: String
    ) {
        kActionSetStartDate(hoursStart, minutesStart)
        kActionSetEndDate(hoursEnd, minutesEnd)
        dateRadioButton.click()
        kActionChooseAllDays()
        kActionSetRepeatStatus(repeatStatus)
        kActionRepeatEverySec(repeatEverySeconds)
        kActionSaveTimeFrame()
    }

    private fun kActionSetRepeatStatus(repeatStatus: Boolean) {
        repeat {
            scrollTo()
            setChecked(repeatStatus)
        }
    }

    private fun kActionRepeatEverySec(repeatEverySeconds: String) {
        repeatEvery {
            scrollTo()
            clearText()
            typeText(repeatEverySeconds)
        }
        closeSoftKeyboard()
    }

    private fun kActionSaveTimeFrame() {
        saveTimeFrame {
            scrollTo()
            click()
        }
    }

    private fun kActionSetStartDate(hoursStart: Int, minutesStart: Int) {
        startDate.setTime(hoursStart, minutesStart)
    }

    private fun kActionSetEndDate(hoursEnd: Int, minutesEnd: Int) {
        endDate.setTime(hoursEnd, minutesEnd)
    }

    private fun kActionChooseAllDays() {
        friday {
            scrollTo()
            setChecked(true)
        }
        thursday.setChecked(true)
        wednesday.setChecked(true)
        tuesday.setChecked(true)
        monday.setChecked(true)
    }
}