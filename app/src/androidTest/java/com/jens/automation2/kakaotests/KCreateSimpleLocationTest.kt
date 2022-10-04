package com.jens.automation2.kakaotests

import com.jens.automation2.kakaoscreenobjects.KCreateLocationScreen
import com.jens.automation2.kakaoscreenobjects.KLocationScreen
import com.jens.automation2.kakaoscreenobjects.KPermissionScreen
import com.jens.automation2.kakaoscreenobjects.KWithTabs.kActionOpenLocation
import com.jens.automation2.kespresso.BeforeTest
import com.jens.automation2.screenobjects.Strings.locationName
import com.jens.automation2.screenobjects.Strings.whileUsingApp
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