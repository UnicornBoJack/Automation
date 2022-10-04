package com.jens.automation2.kakaodirectory.kakaotests

import androidx.test.core.app.ActivityScenario
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KPermissionScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Before
import org.junit.Test

class KAllowPermissionsTest {
    @Before
    fun setup() {
        ActivityScenario.launch(ActivityMainTabLayout::class.java)
    }

    @Test
    fun allowPermissions() {
        onScreen<KPermissionScreen> {
            kAllowPermissions()
        }
    }
}