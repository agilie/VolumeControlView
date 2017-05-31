package com.agilie.controller.animation

import android.graphics.Canvas
import android.view.MotionEvent
import com.agilie.controller.animation.shape.BackGroundCircle
import com.agilie.controller.animation.shape.MainCircle
import com.agilie.controller.animation.shape.MovableCircle

class ControllerImp : Controller, MovableCircle.MovableListener {

    interface ControllerMoveListener {
        fun onMove(value: Double)
        fun onStartMove(start: Boolean)
        fun onStopMove(stop: Boolean)
    }

    private var mainCircle: MainCircle? = null
    private var movableCircle: MovableCircle? = null
    private var backGroundCircle: BackGroundCircle? = null
    private var width = 0
    private var height = 0

    var controllerListener: ControllerMoveListener? = null

    init {
        movableCircle = MovableCircle(this)
        mainCircle = MainCircle(movableCircle!!)
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
                val increase = mainCircle?.onActionDown(event.x, event.y)
                increase?.let { onActionStart(it) }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.eventTime - event.downTime > 200) {
                    mainCircle?.onActionMove(event.x, event.y)
                }
            }
            MotionEvent.ACTION_UP -> {
                val decrease = mainCircle?.onActionUp(event.x, event.y)
                decrease?.let { onActionStop(decrease) }
            }
        }
        return true
    }

    override fun onMovableCircle(value: Double) {
        controllerListener?.onMove(value)
    }

    private fun onActionStart(start: Boolean) {
        backGroundCircle?.increaseBackLight(start)
        controllerListener?.onStartMove(start)
    }

    private fun onActionStop(stop: Boolean) {
        backGroundCircle?.increaseBackLight(!stop)
        controllerListener?.onStopMove(stop)
    }

}