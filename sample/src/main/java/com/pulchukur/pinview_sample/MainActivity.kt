package com.pulchukur.pinview_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.pulchukur.pinview.PinView
import com.pulchukur.pinview.standard.behaviors.appearance.PinBehaviorAppearance

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pinView = findViewById<PinView>(R.id.pin_view_5)

        pinView.pinAddVisualBehaviorProducer(R.id.placeholder) { position, target ->
            object :PinView.VisualBehavior(target){
                override fun onStateChanged(state: PinView.ItemState) {
                    target.isInvisible = state is PinView.ItemState.InActiveFilled
                }
            }
        }
        pinView.pinRecompose()
    }
}

