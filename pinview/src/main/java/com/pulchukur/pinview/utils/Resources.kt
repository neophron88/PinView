package com.pulchukur.pinview.utils

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StyleableRes

@ColorInt
internal fun Resources.Theme.getColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

@AnyRes
internal fun TypedArray.getResourceId(@StyleableRes index: Int, defValue: Int?): Int? {
    val id = getResourceId(index, defValue ?: -1)
    return if (id == -1) null else id
}

@ColorInt
internal fun TypedArray.getColor(@StyleableRes index: Int, defValue: Int?): Int? {
    val color = getColor(index, defValue ?: -1)
    return if (color == -1) null else color
}