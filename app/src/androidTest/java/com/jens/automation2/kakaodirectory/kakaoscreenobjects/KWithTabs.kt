package com.jens.automation2.kakaodirectory.kakaoscreenobjects

import android.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView

object KWithTabs {
    private val LOCATION = KTextView { withText("Locations") }
    private val PROFILE = KTextView { withText("Profiles") }
    private val RULES = KTextView { withText("Rules") }
    private val OVERVIEW = KTextView { withText("Overview") }
    private val tabs = KImageView { withId(R.id.tabs) }

    fun kActionOpenRules() {
        tabs.isVisible()
        RULES.click()
    }

    fun kActionOpenLocation() {
        tabs.isVisible()
        LOCATION.click()
    }

    fun kActionOpenProfile() {
        tabs.isVisible()
        PROFILE.click()
    }

    fun kActionOpenOverview() {
        tabs.isVisible()
        OVERVIEW.click()
    }
}