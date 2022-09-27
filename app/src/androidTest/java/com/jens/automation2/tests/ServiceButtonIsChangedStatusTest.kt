package com.jens.automation2.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.screenobjects.CreateRuleScreen
import com.jens.automation2.screenobjects.CreateTimeframeScreen
import com.jens.automation2.screenobjects.OverviewScreen
import com.jens.automation2.screenobjects.RulesScreen
import com.jens.automation2.screenobjects.Strings.MESSAGE_NO_RULES_ADDED
import com.jens.automation2.screenobjects.Strings.MESSAGE_START_SERVICE
import com.jens.automation2.screenobjects.Strings.OFF
import com.jens.automation2.screenobjects.Strings.ON
import com.jens.automation2.screenobjects.Strings.SIMPLE_RULE_NAME
import com.jens.automation2.screenobjects.Strings.elementInActionList
import com.jens.automation2.screenobjects.Strings.hoursEnd
import com.jens.automation2.screenobjects.Strings.minutesEnd
import com.jens.automation2.screenobjects.Strings.minutesStart
import com.jens.automation2.screenobjects.Strings.repeatEverySeconds
import com.jens.automation2.screenobjects.Strings.timeframe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.jens.automation2.screenobjects.Strings.hoursStart as hoursStart1

@RunWith(AndroidJUnit4::class)
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