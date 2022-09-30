package com.jens.automation2.kakaoscreenobjects

import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import com.jens.automation2.R
import com.jens.automation2.matchers.actionClickOnText
import com.jens.automation2.matchers.actionItemWithName
import com.jens.automation2.screenobjects.Strings.elementInActionList
import com.jens.automation2.screenobjects.Strings.timeframe
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.list.KAbsListView
import io.github.kakaocup.kakao.text.KButton

class KCreateRuleScreen : Screen<KCreateRuleScreen>() {
    val SAVE_RULE = KTextView{withText("Save Rule")}
    val OK = KTextView{withText("Ok")}
    val ACTIVATE = KTextView{withText("Activate")}
    val kTimeframe = KTextView{withText(timeframe)}
    val kElementInActionList = KTextView{withText(elementInActionList)}

    val ruleNameTextField = KEditText{withId(R.id.etRuleName)}
    val addTriggerButton = KButton{withId(R.id.cmdTriggerAdd)}
    val addAction = KButton{withId(R.id.cmdActionAdd)}

    val listView = KView{withId(R.id.select_dialog_listview)}


    val triggerListView = KTextView{withId(R.id.lvTriggerListView)}
    val actionListView = KTextView{withId(R.id.lvActionListView)}

    fun kActionTypeRuleName(text: String) {
        ruleNameTextField.clearText()
        ruleNameTextField.typeText(text)
        closeSoftKeyboard()
    }

    fun kActionClickAddTriggerButton() {
        addTriggerButton.click()
    }

    fun kActionChooseTriggerWithName() {
        kTimeframe.click()
    }

    fun kActionAddAction(actionName: String) {
        addAction.isDisplayed()
        addAction.click()
        kElementInActionList.click()
//        KTextView{
//            actionItemWithName(actionName)
//            isClickable()
//        } perform {click()}


        OK.isClickable()
        OK.click()
        ACTIVATE.isDisplayed()
        ACTIVATE.click()



//        actionWaitForView(addAction).perform(click())
//        onData(actionItemWithName(itemInActionList)).perform(click())
//        actionWaitForView(withText("Ok")).perform(click())
//        actionWaitForView(withText("Activate")).perform(click())
    }

    fun kActionSaveRule() {
        SAVE_RULE.click()
    }

    fun kAssertionCheckTriggerNameInList(text: String) {
        triggerListView.hasText(text)

//        return actionWaitForView(withText(text)).check(matches(isDisplayed()))
    }

    fun kAssertionCheckActionNameInList(text: String) {
        actionListView.hasText(text)
//        return actionWaitForView(withText(text)).check(matches(isDisplayed()))
    }


}