package com.jens.automation2.kespresso

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.espresso.EspressoExtended.DEFAULT_INTERVAL_MILLS
import com.jens.automation2.espresso.EspressoExtended.DEFAULT_TIMEOUT_MILLS
import io.github.kakaocup.kakao.Kakao
import org.junit.Before

interface BeforeTest {
    @Before
    fun setup() {
        ActivityScenario.launch(ActivityMainTabLayout::class.java)
        Kakao { // Kakao runtime
            intercept {
                onViewInteraction { // Intercepting calls on ViewInteraction classes across whole runtime
                    onPerform { interaction, _ ->
                        val startTime = System.currentTimeMillis()
                        val endTime = startTime + DEFAULT_TIMEOUT_MILLS
                        var lastException: Throwable
                        do {
                            try {
                                interaction.check(matches(isDisplayed()))
                                return@onPerform
                            } catch (e: NoMatchingViewException) {
                                lastException = e
                                Thread.sleep(DEFAULT_INTERVAL_MILLS)
                            }
                        } while (System.currentTimeMillis() < endTime)

                        throw lastException
                    }
                }
            }
        }
    }
}