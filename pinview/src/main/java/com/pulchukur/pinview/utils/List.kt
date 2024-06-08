package com.pulchukur.pinview.utils

import android.util.SparseArray

internal fun <T> SparseArray<MutableList<T>>.createNewOrAddToExistingList(
    key: Int,
    value: List<T>
) {
    val oldValue = get(key)
    if (oldValue == null) append(key, value.toMutableList())
    else oldValue += value
}
