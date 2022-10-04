package com.jens.automation2.kakaodirectory.kakaoscreenobjects

import com.jens.automation2.R
import com.jens.automation2.kakaodirectory.kespresso.KEspressoExtended.actionFindText
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class KCreateRuleScreen : Screen<KCreateRuleScreen>() {
    private val saveRule = KTextView { withText(R.string.saveRule) }
    private val okBtn = KTextView { withText(R.string.ok) }
    private val activate = KTextView { withText(R.string.activate) }
    private val kTimeframe = KTextView { withText(R.string.triggerTimeFrame) }
    private val kElementInActionList = KTextView { withText(R.string.actionSetUsbTethering) }
    private val ruleNameTextField = KEditText { withId(R.id.etRuleName) }
    private val addTriggerButton = KButton { withId(R.id.cmdTriggerAdd) }
    private val addAction = KButton { withId(R.id.cmdActionAdd) }

    fun kActionTypeRuleName(text: String) {
        ruleNameTextField.clearText()
        ruleNameTextField.typeText(text)
        closeSoftKeyboard()
    }

    fun kActionAddAction(actionName: String) {
        addAction.click()
        kElementInActionList.click()
        okBtn.click()
        activate.click()
    }

    fun kActionClickAddTriggerButton() =
        addTriggerButton.click()

    fun kActionChooseTriggerWithName() =
        kTimeframe.click()

    fun kActionSaveRule() =
        saveRule.click()

    fun kAssertionCheckTriggerNameInList(text: String) =
        actionFindText(text).hasText(text)

    fun kAssertionCheckActionNameInList(text: String) =
        actionFindText(text).hasText(text)
}