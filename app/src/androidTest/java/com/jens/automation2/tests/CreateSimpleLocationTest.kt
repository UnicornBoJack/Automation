package com.jens.automation2.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.screenobjects.CreateLocationScreen
import com.jens.automation2.screenobjects.LocationScreen
import com.jens.automation2.screenobjects.PermissionScreen
import com.jens.automation2.screenobjects.Strings.locationName
import com.jens.automation2.screenobjects.Strings.whileUsingApp
import org.junit.Rule
import org.junit.Test

class CreateSimpleLocationTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<ActivityMainTabLayout>()
    /**
     * Scenario
     * Step 1. Wipe data in 'Device Manager' for all Emulators
     * Step 2. Click 'Continue' button
     * Step 3. Click 'Allow' button
     * Step 4. Click 'Yes' button
     */
    @Test
    fun createLocation() {
        with(LocationScreen) {
            actionOpenLocationTab()
            actionClickOnAddLocationButton()
        }
        with(PermissionScreen) {
            actionClickContinueBtn()
            whileUsingTheApp(whileUsingApp)
        }
        with(CreateLocationScreen) {
            actionTypeLocationName(locationName)
            actionClickOnCurrentLocationButton()
            clickOnOkButton()
            waitAndClickOnYesBtn()
            actionClickOnSaveLocationButton()
        }
        with(LocationScreen) {
            assertLocationIsCreatedByName(locationName)
            actionDeleteLocationByName(locationName)
        }
    }
}