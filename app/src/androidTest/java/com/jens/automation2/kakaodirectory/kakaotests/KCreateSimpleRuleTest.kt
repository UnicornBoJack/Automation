package com.jens.automation2.kakaodirectory.kakaotests

import androidx.test.core.app.ActivityScenario.launch
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KCreateRuleScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KCreateTimeframeScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KRulesScreen
import com.jens.automation2.kakaodirectory.kakaoscreenobjects.KWithTabs.kActionOpenRules
import com.jens.automation2.espressodirectory.screenobjects.Strings.ACTION_TEXT
import com.jens.automation2.espressodirectory.screenobjects.Strings.SIMPLE_RULE_NAME
import com.jens.automation2.espressodirectory.screenobjects.Strings.TRIGGER_TEXT
import com.jens.automation2.espressodirectory.screenobjects.Strings.elementInActionList
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.hoursStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesEnd
import com.jens.automation2.espressodirectory.screenobjects.Strings.minutesStart
import com.jens.automation2.espressodirectory.screenobjects.Strings.repeatEverySeconds
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Before
import org.junit.Test

class KCreateSimpleRuleTest {
    @Before
    fun setup() {
        launch(ActivityMainTabLayout::class.java)
    }

    @Test
    fun createSimpleRule() {
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
            kAssertionCheckTriggerNameInList(TRIGGER_TEXT)
            kAssertionCheckActionNameInList(ACTION_TEXT)
            kActionSaveRule()
        }
        onScreen<KRulesScreen> {
            kAssertRuleIsCreatedByName(SIMPLE_RULE_NAME)
            kActionDeleteRuleByName(SIMPLE_RULE_NAME)
        }
    }
}