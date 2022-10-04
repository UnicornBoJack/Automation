package com.jens.automation2.espressodirectory.matchers

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertFalse

fun actionClickOnText(text: String) {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    val grandPermission = device.findObject(UiSelector().text(text))
    grandPermission.click()
}

val device: UiDevice
    get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

fun assertPopupIsNotDisplayed(text: String) {
    device.waitForIdle()
    assertFalse(device.hasObject(By.text(text)))
}
