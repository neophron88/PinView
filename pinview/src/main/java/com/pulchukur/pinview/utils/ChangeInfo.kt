package com.pulchukur.pinview.utils

internal class ChangeInfo {
    var isPinItemLayoutChanged: Boolean = false
    var isPinDecorationChanged: Boolean = false
    var isPinCountChanged: Boolean = false
    var isAddedBehavior: Boolean = false


    fun reset() {
        isPinDecorationChanged = false
        isPinItemLayoutChanged = false
        isPinCountChanged = false
        isAddedBehavior = false
    }
}