package com.agilie.controller

import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = BlurMaskFilter(40f, BlurMaskFilter.Blur.OUTER)

        text.paint.maskFilter = filter

        text.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        controller.setOnClickListener { startActivity(ControllerActivity.getCallingIntent(this)) }
    }
}
