package com.agilie.controller.animation

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

abstract class Painter {

    companion object {
        val INNER_CIRCLE_STROKE_WIDTH = 60f
        val LINE_LENGTH = 55f
        val FULL_CIRCLE = 360
        val CAPTURE_ANGLE = 10
        val DELTA_TIME = 0.09
        val INCREASE_FACTOR = 6.0
        val DECREASE_FACTOR = 6.0
        val BLUR_MASK_RADIUS = 40f
        val OUTER_BLUR_MASK_RADIUS = 60f
    }

    open var startPoint = PointF()
    open var endPoint = PointF()
    open var paint: Paint? = null
    open var radius = 0f

    open fun onSizeChanged(width: Int, height: Int) {}

    open fun onDraw(canvas: Canvas) {
        canvas.drawCircle(startPoint.x, startPoint.y, radius, paint)
    }

    open fun setRadius(width: Int, height: Int) {}
}