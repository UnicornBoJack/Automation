package com.jens.automation2.kakaodirectory.kakaotests

import com.jens.automation2.espressodirectory.screenobjects.Strings.locationName
import com.jens.automation2.espressodirectory.screenobjects.Strings.whileUsingApp
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KCreateLocationScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KLocationScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KPermissionScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KWithTabs.kActionOpenLocation
import com.jens.automation2.kakaodirectory.kespresso.BeforeTest
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Test

class KCreateSimpleLocationTest : BeforeTest {
    @Test
    fun createLocation() {
        onScreen<KPermissionScreen> {
            kAllowPermissions()
        }

        kActionOpenLocation()

        onScreen<KLocationScreen> {
            kActionClickOnAddLocationButton()
        }
        onScreen<KPermissionScreen> {
            kActionClickContinueBtn()
            kActionClickOnWhileUsingTheApp(whileUsingApp)
        }
        onScreen<KCreateLocationScreen> {
            kActionTypeLocationName(locationName)
            kActionClickOnCurrentLocationButton()
            kActionClickOnOkButton()
            kActionClickOnYesButton()
            kActionClickOnSaveLocationButton()
        }
        onScreen<KLocationScreen> {
            kAssertLocationIsCreatedByName(locationName)
            kActionDeleteLocationByName(locationName)
        }
    }
}