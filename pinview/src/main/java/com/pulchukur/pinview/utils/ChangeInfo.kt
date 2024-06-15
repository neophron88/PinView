package com.pulchukur.pinview.utils

internal class ChangeInfo {
    var isPinLayoutChanged: Boolean = false
    var isPinDecorationChanged: Boolean = false
    var isPinCountChanged: Boolean = false
    var isAddedBehavior: Boolean = false


    fun reset() {
        isPinLayoutChanged = false
        isPinCountChanged = false
        isAddedBehavior = false
    }
}