package com.pulchukur.pinview.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

internal inline fun TextView.onTextChanged(
    crossinline action: (text: CharSequence?, start: Int, before: Int, count: Int)-> Unit
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            action(text, start, before, count)
        }
    }
    addTextChangedListener(textWatcher)

    return textWatcher
}