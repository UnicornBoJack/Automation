package com.jens.automation2.kakaotests

import androidx.test.core.app.ActivityScenario.launch
import com.jens.automation2.ActivityMainTabLayout
import com.jens.automation2.kakaoscreenobjects.KCreateRuleScreen
import com.jens.automation2.kakaoscreenobjects.KCreateTimeframeScreen
import com.jens.automation2.kakaoscreenobjects.KRulesScreen
import com.jens.automation2.kakaoscreenobjects.KWithTabs
import com.jens.automation2.screenobjects.Strings.ACTION_TEXT
import com.jens.automation2.screenobjects.Strings.SIMPLE_RULE_NAME
import com.jens.automation2.screenobjects.Strings.TRIGGER_TEXT
import com.jens.automation2.screenobjects.Strings.elementInActionList
import com.jens.automation2.screenobjects.Strings.hoursEnd
import com.jens.automation2.screenobjects.Strings.hoursStart
import com.jens.automation2.screenobjects.Strings.minutesEnd
import com.jens.automation2.screenobjects.Strings.minutesStart
import com.jens.automation2.screenobjects.Strings.repeatEverySeconds
import com.jens.automation2.screenobjects.Strings.timeframe
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Before
import org.junit.Test

class KCreateSimpleRuleTest : KWithTabs {

    @Before
    fun setup(){
        launch(ActivityMainTabLayout::class.java)
    }

    @Test
    fun createSimpleRule() {
        actionOpenRules()
//        kActionClickAddRuleButton()

        onScreen<KRulesScreen> {
            kActionClickAddRuleButton()
//            Thread.sleep(5000)
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
                repeatEverySeconds
            )
        }

        Thread.sleep(1000)

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