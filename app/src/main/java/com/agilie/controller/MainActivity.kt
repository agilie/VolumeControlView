package com.agilie.controller

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import com.agilie.splinecontroller.animation.controller.ControllerImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_pick.*


class MainActivity : AppCompatActivity() {

    private var dialogLayout: ViewGroup? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogLayout = inflater.inflate(R.layout.dialog_pick, dialogPick) as ViewGroup
        dialog = Dialog(this)
        dialog?.setContentView(dialogLayout)

        initController()
        initDialog()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dialog_pick, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.controller_setting -> dialog?.show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initController() {
        controllerView.setStartPercent(50)
        controllerView.backgroundLayoutColor = Color.BLACK
        controllerView.controller?.onTouchControllerListener = (object : ControllerImpl.OnTouchControllerListener {
            override fun onControllerDown(angle: Int, percent: Int) {
                value.text = percent.toString() + "%"
            }

            override fun onControllerMove(angle: Int, percent: Int) {
                value.text = percent.toString() + "%"
            }

            override fun onAngleChange(angle: Int, percent: Int) {
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

    private fun initDialog() {
        val dialogApply = dialogLayout?.findViewById(R.id.apply) as Button
        val dialogCancel = dialogLayout?.findViewById(R.id.cancel) as Button
        val dialogSeekBarMinRadius = dialogLayout?.findViewById(R.id.seekBarMinRadius) as SeekBar?
        val dialogSeekBarMaxRadius = dialogLayout?.findViewById(R.id.seekBarMaxRadius) as SeekBar?
        val dialogFrequency = dialogLayout?.findViewById(R.id.frequency) as SeekBar?
        var minRadius = 1f
        var maxRadius = 1f
        var frequency = 0f

        dialogSeekBarMinRadius?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //controllerView.setShiningMinRadius(1 + progress / 100f)
                minRadius = (1 + progress / 100f)
                if (dialogSeekBarMaxRadius!!.progress - progress < 10) {
                    dialogSeekBarMaxRadius?.progress = (progress + controllerView.getShiningMaxRadius()!! * 10).toInt()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        dialogSeekBarMaxRadius?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //controllerView.setShiningMaxRadius(1 + progress / 100f)
                maxRadius = (1 + progress / 100f)
                if (progress - dialogSeekBarMinRadius!!.progress < 10) {
                    dialogSeekBarMinRadius?.progress = (progress - controllerView.getShiningMaxRadius()!! * 10).toInt()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        dialogFrequency?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //controllerView.setShiningFrequency(progress / 10000f)
                frequency = progress / 10000f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        dialogApply.setOnClickListener {
            controllerView.setShiningMinRadius(minRadius)
            controllerView.setShiningMaxRadius(maxRadius)
            controllerView.setShiningFrequency(frequency)
            dialog?.dismiss()
        }

        dialogCancel.setOnClickListener { dialog?.dismiss() }

    }

}

