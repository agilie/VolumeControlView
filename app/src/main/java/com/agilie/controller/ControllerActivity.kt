package com.agilie.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ControllerActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: android.content.Context) = Intent(context, ControllerActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

       /* controller_view.controller?.controllerListener = (object : ControllerImp.ControllerMoveListener {
            override fun onMove(value: Double) {
                Log.d("ControllerActivity", "onMove = " + value)
            }

            override fun onStartMove(start: Boolean) {
                Log.d("ControllerActivity", "onStart = " + start)
            }

            override fun onStopMove(stop: Boolean) {
                Log.d("ControllerActivity", "onStop = " + stop)
            }
        })*/
    }
}
