package com.agilie.controller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.agilie.controller.animation.controller.ControllerImpl
import kotlinx.android.synthetic.main.activity_controller.*

class ControllerActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: android.content.Context) = Intent(context, ControllerActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        controller_view.backgroundLayoutColor = Color.parseColor("#000000")
        controller_view.controller?.onTouchControllerListener = (object : ControllerImpl.OnTouchControllerListener {
            override fun onControllerDown(angle: Int) {


            }

            override fun onControllerMove(angle: Int) {

            }

            override fun onAngleChange(angle: Int) {
                when (angle) {
                    in 0..60 -> controller_view.setBackgroundShiningColor(Color.GREEN)
                    in 61..120 -> controller_view.setBackgroundShiningColor(Color.YELLOW)
                    in 121..180 -> controller_view.setBackgroundShiningColor(Color.WHITE)
                    in 181..240 -> controller_view.setBackgroundShiningColor(Color.RED)
                    in 241..300 -> controller_view.setBackgroundShiningColor(Color.MAGENTA)
                    in 301..360 -> controller_view.setBackgroundShiningColor(Color.BLUE)
                }
            }
        })

    }
}
