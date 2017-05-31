package com.agilie.controller.animation

import android.graphics.Canvas
import android.view.MotionEvent

interface Controller {

    fun onDraw(canvas: Canvas)
    fun onSizeChanged(width: Int, height: Int)
    fun onTouchEvent(event: MotionEvent?): Boolean
}