package com.jens.automation2.kakaoscreenobjects

import com.jens.automation2.R
import com.jens.automation2.kespresso.KEspressoExtended.actionFindText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class KRulesScreen : Screen<KRulesScreen>() {
    private val addRuleButton = KButton { withId(R.id.bAddRule) }
    private val deleteRuleButton = KTextView { withText(R.string.deleteCapital) }
    private val ruleNameInList = KTextView { withId(R.id.tvRuleName) }

    fun kActionClickAddRuleButton() {
        addRuleButton.isDisplayed()
        addRuleButton.click()
    }

    fun kAssertRuleIsCreatedByName(name: String) =
        actionFindText(name).hasText(name)

    fun kActionDeleteRuleByName(name: String) {
        ruleNameInList {
            hasText(name)
            longClick()
        }
        deleteRuleButton.click()
    }
}