package com.agilie.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.agilie.controller.animation.controller.ControllerImpl
import kotlinx.android.synthetic.main.activity_controller.*

class ControllerActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: android.content.Context) = Intent(context, ControllerActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        controller_view.controller?.onTouchControllerListener = (object : ControllerImpl.OnTouchControllerListener {
            override fun onControllerDown(angle: Int) {
                Log.d("ControllerActivity", angle.toString())
            }

            override fun onControllerMove(angle: Int) {
                Log.d("ControllerActivity", angle.toString())
            }
        })

    }
}
