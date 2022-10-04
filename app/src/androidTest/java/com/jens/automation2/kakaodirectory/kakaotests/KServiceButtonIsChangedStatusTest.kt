package com.jens.automation2.kakaodirectory.kakaotests

import androidx.test.core.app.ActivityScenario
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KCreateRuleScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KCreateTimeframeScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KOverviewScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KRulesScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KWithTabs.kActionOpenOverview
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KWithTabs.kActionOpenRules
import com.jens.automation2.espressodirectory.screenobjects.Strings.MESSAGE_NO_RULES_ADDED
import com.jens.automation2.espressodirectory.screenobjects.Strings.MESSAGE_START_SERVICE
import com.jens.automation2.espressodirectory.screenobjects.Strings.OFF
import com.jens.automation2.espressodirectory.screenobjects.Strings.ON
import com.jens.automation2.espressodirectory.screenobjects.Strings.SIMPLE_RULE_NAME
import com.jens.automation2.espressodirectory.screenobjects.Strings.elementInActionList
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.repeatEverySeconds
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Before
import org.junit.Test

class KServiceButtonIsChangedStatusTest {
    @Before
    fun setup() {
        ActivityScenario.launch(ActivityMainTabLayout::class.java)
    }

    @Test
    fun kServiceButtonIsChangedStatus() {
        onScreen<KOverviewScreen> {
            kActionClickOnServiceButton()
            kAssertionIsTextExist(OFF)
            kAssertIsToastMessagePresent(MESSAGE_NO_RULES_ADDED)
        }

        kActionOpenRules()

        onScreen<KRulesScreen> {
            kActionClickAddRuleButton()
        }
        onScreen<KCreateRuleScreen> {
            kActionTypeRuleName(SIMPLE_RULE_NAME)
            kActionClickAddTriggerButton()
            kActionChooseTriggerWithName()
        }
        onScreen<KCreateTimeframeScreen> {
            actionCreateTimeFrame(
                hoursStart,
                minutesStart,
                hoursEnd,
                minutesEnd,
                true,
                repeatEverySeconds
            )
        }
        onScreen<KCreateRuleScreen> {
            kActionAddAction(elementInActionList)
            kActionSaveRule()
        }

        kActionOpenOverview()

        onScreen<KOverviewScreen> {
            kActionClickOnServiceButton()
            kAssertionIsTextExist(ON)
            kAssertIsToastMessagePresent(MESSAGE_START_SERVICE)
            kActionClickOnServiceButton()
            kAssertionIsTextExist(OFF)
        }

        kActionOpenRules()

        onScreen<KRulesScreen> {
            kActionDeleteRuleByName(SIMPLE_RULE_NAME)
            kActionDeleteRuleByName(SIMPLE_RULE_NAME)
        }
    }
}