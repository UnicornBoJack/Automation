package com.jens.automation2.matchers

fun actionItemWithName(itemName: String) = ActionItemTextMatcher(itemName)
fun actionScrollToPosition(position: Int) = ScrollToPositionAction(position)
