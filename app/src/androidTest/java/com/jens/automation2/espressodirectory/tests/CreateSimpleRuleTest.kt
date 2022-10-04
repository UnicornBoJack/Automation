package com.jens.automation2.espressodirectory.tests

import androidx.test.ext.junit.rules.activityScenarioRule
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.espressodirectory.screenobjects.CreateRuleScreen
import com.jens.automation2.espressodirectory.screenobjects.CreateTimeframeScreen
import com.jens.automation2.espressodirectory.screenobjects.RulesScreen
import com.jens.automation2.espressodirectory.screenobjects.Strings.ACTION_TEXT
import com.jens.automation2.espressodirectory.screenobjects.Strings.SIMPLE_RULE_NAME
import com.jens.automation2.espressodirectory.screenobjects.Strings.TRIGGER_TEXT
import com.jens.automation2.espressodirectory.screenobjects.Strings.elementInActionList
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.repeatEverySeconds
import com.jens.automation2.espressodirectory.screenobjects.Strings.timeframe
import org.junit.Rule
import org.junit.Test

class CreateSimpleRuleTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<ActivityMainTabLayout>()
    /**
     * Scenario
     * Step 1. Open Rule tab;
     * Step 2. Click 'Add rule';
     * Step 3. Enter rules name;
     * Step 4. Click 'add trigger' (Timeframe);
     * Step 5. Find and check the added trigger;
     * Step 6. Click 'Add action' (USB Tethering, status - Activate);
     * Step 7. Find and check the added action;
     * Step 8. Save the rule;
     * Step 9. Find and check the added rule;
     * Step 10. Delete and check the added rule;
     */
    @Test
    fun createSimpleRule() {
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
                hoursStart,
                minutesStart,
                hoursEnd,
                minutesEnd,
                repeatEverySeconds
            )
        }
        with(CreateRuleScreen) {
            assertionCheckTriggerNameInList(TRIGGER_TEXT)
            actionAddAction(elementInActionList)
            assertionCheckActionNameInList(ACTION_TEXT)
            actionSaveRule()
        }
        with(RulesScreen) {
            assertRuleIsCreatedByName(SIMPLE_RULE_NAME)
            actionDeleteRuleByName(SIMPLE_RULE_NAME)
        }
    }
}