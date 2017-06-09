package com.agilie.controller

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.SeekBar
import com.agilie.splinecontroller.animation.controller.ControllerImpl
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initController()
        initSeekBars()
    }

    private fun initController() {
        value.setFactory { LayoutInflater.from(this).inflate(R.layout.percent_counter, value, false) }

        controllerView.setStartPercent(50)
        controllerView.backgroundLayoutColor = Color.parseColor("#000000")
        controllerView.controller?.onTouchControllerListener = (object : ControllerImpl.OnTouchControllerListener {
            override fun onControllerDown(angle: Int, percent: Int) {
                // nothing here
            }

            override fun onControllerMove(angle: Int, percent: Int) {
                // nothing here
            }

            override fun onAngleChange(angle: Int, percent: Int) {
                value.setText(percent.toString() + "%")

                when (angle) {
                    in 0..45 -> controllerView.setBackgroundShiningColor(Color.parseColor("#FF7F00"))
                    in 46..90 -> controllerView.setBackgroundShiningColor(Color.parseColor("#9FFF00"))
                    in 91..135 -> controllerView.setBackgroundShiningColor(Color.parseColor("#FACC00"))
                    in 136..180 -> controllerView.setBackgroundShiningColor(Color.parseColor("#3B9800"))
                    in 181..225 -> controllerView.setBackgroundShiningColor(Color.parseColor("#00493D"))
                    in 226..270 -> controllerView.setBackgroundShiningColor(Color.parseColor("#E7FBE1"))
                    in 271..315 -> controllerView.setBackgroundShiningColor(Color.parseColor("#53FFFF"))
                    in 316..360 -> controllerView.setBackgroundShiningColor(Color.parseColor("#FF7F00"))
                }
            }
        })
    }

    private fun initSeekBars() {
        seekBarMinRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                controllerView.setShiningMinRadius(1 + progress / 100f)
                if (seekBarMaxRadius.progress - progress < 10) {
                    seekBarMaxRadius.progress = (progress + controllerView.getShiningMaxRadius()!! * 10).toInt()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        seekBarMaxRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                controllerView.setShiningMaxRadius(1 + progress / 100f)
                if (progress - seekBarMinRadius.progress < 10) {
                    seekBarMinRadius.progress = (progress - controllerView.getShiningMaxRadius()!! * 10).toInt()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        frequency.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                controllerView.setShiningFrequency(progress / 10000f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

    }
}


