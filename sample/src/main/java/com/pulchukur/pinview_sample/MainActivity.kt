package com.pulchukur.pinview_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.standard.behaviors.appearance.PinBehaviorAnimatedAppearance
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinBubbleAnim

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pinView = findViewById<PinView>(R.id.pin_pin)

        pinView.pinInvalidate()

        pinView.pinAddVisualBehaviorProducer(R.id.pin_input) { p, v ->
            PinBehaviorAnimatedAppearance(v, PinBubbleAnim())
        }
        pinView.pinInvalidate()

        val pinView2 = findViewById<PinView>(R.id.pinView2)
        pinView2.pinRemoveVisualBehaviorForViewById(R.id.pin_input)
        pinView2.pinAddVisualBehaviorProducer(R.id.pin_input) { p, v ->
            PinBehaviorAnimatedAppearance(v, PinBubbleAnim())
        }
        pinView2.pinInvalidate()

    }
}

