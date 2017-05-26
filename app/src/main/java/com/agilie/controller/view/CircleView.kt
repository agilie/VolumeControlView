package com.agilie.controller.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.agilie.controller.animation.SpeedControllerImp


/** Action Flow
 * Get screen size
 * Create background Act
 * Create top circle
 *
 * */

class CircleView : View {

    private var controller: SpeedControllerImp? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        controller?.onDraw(canvas)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        controller?.onSizeChanged(w, h)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        controller?.onTouchEvent(event)
        return true
    }

    private fun init() {
        setWillNotDraw(false)
        controller = SpeedControllerImp()
    }


}