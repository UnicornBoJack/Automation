package com.jens.automation2.screenobjects

object Strings {
    //Overview  Screen
    val MESSAGE_START_SERVICE = "Automation Service started."
    val MESSAGE_NO_RULES_ADDED = "No rules defined. Service won't start."
    val OFF = "OFF"
    val ON = "ON"

    //Locations Screen
    val locationName = "SimpleLocation"

    //Riles Screen
    val SIMPLE_RULE_NAME = "simpleRule"
    val TRIGGER_TEXT = "leaving Timeframe: 06:00:00 until 06:05:00 on days " +
            "[2, 3, 4, 5, 6], repeat every 30 seconds"
    val ACTION_TEXT = "turn USB Tethering on"
    val timeframe = "Timeframe"
    val elementInActionList = "USB Tethering"

    //Profile Screen
    val simpleProfileName = "simpleProfileName"

    //Permission Screen
    val whileUsingApp = "While using the app"

    //Time Frame Screen
    val hoursStart: Int = 6
    val minutesStart: Int = 0
    val hoursEnd: Int = 6
    val minutesEnd: Int = 5
    val repeatEverySeconds = "30"
}