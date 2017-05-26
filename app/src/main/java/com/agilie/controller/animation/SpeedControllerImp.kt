package com.agilie.controller.animation

import android.graphics.Canvas
import android.view.MotionEvent
import com.agilie.controller.animation.shape.BackGroundCircle
import com.agilie.controller.animation.shape.MainCircle

class SpeedControllerImp : SpeedController {


    private var mainCircle: MainCircle? = null
    private var backGroundCircle: BackGroundCircle? = null
    private var width = 0
    private var height = 0

    init {
        mainCircle = MainCircle()
        backGroundCircle = BackGroundCircle()
    }

    override fun onDraw(canvas: Canvas) {
        backGroundCircle?.onDraw(canvas)
        mainCircle?.onDraw(canvas)

    }

    override fun onSizeChanged(width: Int, height: Int) {
        this.width = width
        this.height = height
        mainCircle?.onSizeChanged(width, height)
        backGroundCircle?.onSizeChanged(width, height)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mainCircle?.onActionDown(event.x, event.y)
                backGroundCircle?.increaseBackLight(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.eventTime - event.downTime > 200) {
                    mainCircle?.onActionMove(event.x, event.y)
                    backGroundCircle?.increaseBackLight(false)
                }
            }
            MotionEvent.ACTION_UP -> {
                mainCircle?.onActionUp(event.x, event.y)
            }
        }
        return true
    }

}