package com.jens.automation2.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.screenobjects.CreateProfileScreen
import com.jens.automation2.screenobjects.ProfilesScreen
import com.jens.automation2.screenobjects.Strings.simpleProfileName
import org.junit.Rule
import org.junit.Test

class CreateSimpleProfileTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<ActivityMainTabLayout>()
    /**
    Test 2.
     * Scenario
     * Step 1. Open the Profiles;
     * Step 2. Tap 'Add profile';
     * Step 3. Enter the name of the rule;
     * Step 4. Change sound mode;
     * Step 5. Change volumes (Ringtone and notifications);
     * Step 6. Save the profile;
     * Step 7. Find and check the added profile;
     * Step 8. Delete and check the added profile;
     */
    @Test
    fun createProfile() {
        with(ProfilesScreen) {
            actionOpenProfile()
            actionClickOnAddProfile()
        }
        with(CreateProfileScreen) {
            actionEnterProfileName(simpleProfileName)
            actionChangeSoundMode()
            actionChangeVolume()
            actionSaveProfile()
        }
        with(ProfilesScreen) {
            assertionCheckCreatedProfile(simpleProfileName)
            actionDeleteProfileByName(simpleProfileName)
        }
    }
}