package com.jens.automation2.kakaotests

import androidx.test.core.app.ActivityScenario
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.kakaoscreenobjects.KCreateProfileScreen
import com.jens.automation2.kakaoscreenobjects.KProfilesScreen
import com.jens.automation2.kakaoscreenobjects.KWithTabs.kActionOpenProfile
import com.jens.automation2.screenobjects.Strings.simpleProfileName
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Before
import org.junit.Test

class KCreateSimpleProfileTest {
    @Before
    fun setup() {
        ActivityScenario.launch(ActivityMainTabLayout::class.java)
    }

    @Test
    fun createProfile() {
        kActionOpenProfile()

        onScreen<KProfilesScreen> {
            kActionClickOnAddProfile()
        }
        onScreen<KCreateProfileScreen> {
            kActionEnterProfileName(simpleProfileName)
            kActionChangeSoundMode()
            kActionChangeVolume()
            kActionSaveProfile()
        }
        onScreen<KProfilesScreen> {
            kAssertionCheckCreatedProfile(simpleProfileName)
            kActionDeleteProfileByName(simpleProfileName)
        }
    }
}