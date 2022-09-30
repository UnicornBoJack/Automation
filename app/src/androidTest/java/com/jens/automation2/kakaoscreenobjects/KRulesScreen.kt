package com.jens.automation2.kakaoscreenobjects

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.jens.automation2.R
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class KRulesScreen : Screen<KRulesScreen>() {

    val addRuleButton = KButton { withId(R.id.bAddRule) }
    val deleteRuleButton = KTextView { withText("Delete") }
    val ruleNameInList = KTextView { withId(R.id.tvRuleName) }

    fun kActionClickAddRuleButton() {
        addRuleButton.isDisplayed()
        addRuleButton.click()
    }

    //TODO need to rewrite
    fun kAssertRuleIsCreatedByName(name: String) {
        KTextView {
            withText(name)
        } perform { click() }
        matches(withText(name))

//        actionWaitForView(withText(name)).perform(click())
//            .check(matches(withText(name)))
    }

    fun kActionDeleteRuleByName(name: String) {
        ruleNameInList {
            matches {
                withText(name)
            }
            perform {
                longClick()
            }
        }

        deleteRuleButton.click()
    }
}