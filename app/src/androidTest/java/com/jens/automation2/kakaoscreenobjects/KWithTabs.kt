package com.jens.automation2.kakaoscreenobjects

import android.R
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView

interface KWithTabs {
    companion object {
        val LOCATION = KTextView { withText("Locations") }
        val PROFILE = KTextView { withText("Profiles") }
        val RULES = KTextView { withText("Rules") }
        val OVERVIEW = KTextView { withText("Overview") }

        val tabs = KImageView { withId(R.id.tabs) }
    }

    fun actionOpenRules() {
        tabs.isVisible()
        RULES.click()
    }

    fun actionOpenLocation() {
        tabs.isVisible()
        LOCATION.click()
    }

    fun actionOpenProfile() {
        tabs.isVisible()
        PROFILE.click()
    }

    fun actionOpenOverview() {
        tabs.isVisible()
        OVERVIEW.click()
    }
}