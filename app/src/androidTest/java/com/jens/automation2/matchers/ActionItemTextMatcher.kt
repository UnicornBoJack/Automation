package com.jens.automation2.matchers

import com.jens.automation2.ActivityManageRule.Item
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ActionItemTextMatcher(private val itemName: String) : TypeSafeMatcher<Item>() {
    override fun describeTo(description: Description) {
        description.appendText("action item with text: $itemName")
    }

    override fun matchesSafely(item: Item): Boolean = (item.text == itemName)
}