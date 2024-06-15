package com.pulchukur.pinview_sample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.standard.behaviors.appearance.PinBehaviorAnimatedAppearance
import com.pulchukur.pinview.standard.behaviors.appearance.animation.PinAppearanceViewPropertyAnimator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pinView2 = findViewById<PinView>(R.id.pin_pin)


        pinView2.pinInvalidate()
        val listener = PinView.PinChangeListener {
            Log.d("it0088", "onCreate: ${it}")

        }
        pinView2.pinAddOnTextChangeListener(listener)
        pinView2.pinAddOnTextChangeListener {
            Log.d("it0088", "der: ${it}")
        }
//        pinView2.pinAddVisualBehaviorProducer(R.id.pin_container, ::PinErrorBehavior)

        pinView2.pinAddVisualBehaviorProducer(R.id.pin_input) { p, v ->

            PinBehaviorAnimatedAppearance(v,
                PinAppearanceViewPropertyAnimator(
                    appearing = { a, t ->
                        a.scaleY(1.5f).scaleX(1.5f).start()
                        t.postDelayed(200){a.scaleY(1f).scaleX(1f).start()}
                    },
                    disappearing = { a, t ->
                        a.scaleY(0f).scaleX(0f).start()
                    }
                )
            )
        }
        pinView2.pinInvalidate()


    }
}

class PinErrorBehavior(
    private val position: Int,
    private val target: View
) : PinView.VisualBehavior(target) {

    override fun onStateChanged(state: PinView.ItemState) {
        if (state !is PinView.ItemState.Active) return
        target.animate()
            .scaleX(-10f)
            .setDuration(100)
            .start()

        target.postDelayed(100) {
            target.animate()
                .translationX(10f)
                .setDuration(100)
                .start()
        }
        target.postDelayed(200) {
            target.animate()
                .translationX(10f)
                .setDuration(100)
                .start()
        }

    }


}