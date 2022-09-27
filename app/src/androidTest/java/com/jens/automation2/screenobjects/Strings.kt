package com.jens.automation2.screenobjects

object Strings {
    //TODO Overview  Screen
    val MESSAGE_START_SERVICE = "Automation Service started."
    val MESSAGE_NO_RULES_ADDED = "No rules defined. Service won't start."
    val OFF = "OFF"
    val ON = "ON"

    //TODO Locations Screen
    val locationName = "SimpleLocation"

    //TODO Riles Screen
    val SIMPLE_RULE_NAME = "simpleRule"
    val TRIGGER_TEXT = "leaving Timeframe: 06:00:00 until 06:05:00 on days " +
            "[2, 3, 4, 5, 6], repeat every 30 seconds"
    val ACTION_TEXT = "turn USB Tethering on"
    val timeframe = "Timeframe"
    val elementInActionList = "USB Tethering"


    //TODO Profile Screen
    val simpleProfileName = "simpleProfileName"

    //TODO Permission Screen
    val whileUsingApp = "While using the app"

    //TODO Time Frame Screen
    val hoursStart: Int = 6
    val minutesStart: Int = 0
    val hoursEnd: Int = 6
    val minutesEnd: Int = 5
    val repeatEverySeconds = "30"
}