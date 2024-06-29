package com.pulchukur.pinview_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.standard.behaviors.appearance.PinBehaviorAnimatedAppearance
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromBottomToBottom
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAnimFromBottomToTop
import com.pulchukur.pinview.standard.behaviors.cursor.PinBehaviorCursor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

