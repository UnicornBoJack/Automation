package com.jens.automation2.espressodirectory.matchers

import android.view.View
import android.widget.SeekBar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

fun setProgress(progress: Int) = object : ViewAction {
    override fun perform(uiController: UiController, view: View) {
        val seekBar = view as SeekBar
        if (progress !in seekBar.min..seekBar.max)
            throw IllegalArgumentException(
                "progress should be in range " +
                        "${seekBar.min}..${seekBar.max} but got $progress"
            )
        seekBar.progress = progress
    }

    override fun getDescription() = "Set a progress on a SeekBar"
    override fun getConstraints() = isAssignableFrom(SeekBar::class.java)
}