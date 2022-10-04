package com.jens.automation2.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.screenobjects.PermissionScreen
import org.junit.Rule
import org.junit.Test

class AllowPermissionsTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<ActivityMainTabLayout>()
    /**
     * Scenario
     * Step 1. Wipe data in 'Device Manager' for all Emulators
     * Step 2. Click 'Continue' button
     * Step 3. Click 'Allow' button (Grand Permission)
     * Step 4. Click 'Yes' button
     */
    @Test
    fun allowPermissions() {
        with(PermissionScreen) {
            allowPermissions()
        }
    }
}