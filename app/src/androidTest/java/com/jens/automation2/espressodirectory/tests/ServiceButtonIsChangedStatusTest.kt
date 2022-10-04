package com.jens.automation2.espressodirectory.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.espressodirectory.screenobjects.CreateRuleScreen
import com.jens.automation2.espressodirectory.screenobjects.CreateTimeframeScreen
import com.jens.automation2.espressodirectory.screenobjects.OverviewScreen
import com.jens.automation2.espressodirectory.screenobjects.RulesScreen
import com.jens.automation2.espressodirectory.screenobjects.Strings.MESSAGE_NO_RULES_ADDED
import com.jens.automation2.espressodirectory.screenobjects.Strings.MESSAGE_START_SERVICE
import com.jens.automation2.espressodirectory.screenobjects.Strings.OFF
import com.jens.automation2.espressodirectory.screenobjects.Strings.ON
import com.jens.automation2.espressodirectory.screenobjects.Strings.SIMPLE_RULE_NAME
import com.jens.automation2.espressodirectory.screenobjects.Strings.elementInActionList
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.repeatEverySeconds
import com.jens.automation2.espressodirectory.screenobjects.Strings.timeframe
import org.junit.Rule
import org.junit.Test
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursStart as hoursStart1

class ServiceButtonIsChangedStatusTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<ActivityMainTabLayout>()

    @Test
    fun serviceButtonIsChangedStatus() {
        with(OverviewScreen) {
            actionClickOnServiceButton()
            assertionIsTextExist(OFF)
            assertIsToastMessagePresent(MESSAGE_NO_RULES_ADDED)
        }
        with(RulesScreen) {
            actionOpenRules()
            actionClickAddRuleButton()
        }
        with(CreateRuleScreen) {
            actionTypeRuleName(SIMPLE_RULE_NAME)
            actionClickAddTriggerButton()
            actionChooseTriggerWithName(timeframe)
        }
        with(CreateTimeframeScreen) {
            actionCreateTimeFrame(
                hoursStart1,
                minutesStart,
                hoursEnd,
                minutesEnd,
                repeatEverySeconds
            )
        }
        with(CreateRuleScreen) {
            actionAddAction(elementInActionList)
            actionSaveRule()
        }
        with(OverviewScreen) {
            actionOpenOverviewTab()
            actionClickOnServiceButton()
            assertionIsTextExist(ON)
            assertIsToastMessagePresent(MESSAGE_START_SERVICE)
            actionClickOnServiceButton()
            assertionIsTextExist(OFF)
        }
        with(RulesScreen) {
            actionOpenRules()
            actionDeleteRuleByName(SIMPLE_RULE_NAME)
            actionDeleteRuleByName(SIMPLE_RULE_NAME)
        }
    }
}