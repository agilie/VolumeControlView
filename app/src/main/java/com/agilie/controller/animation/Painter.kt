package com.agilie.controller.animation

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

abstract class Painter {

    companion object {
        val INNER_CIRCLE_STROKE_WIDTH = 60f
        val LINE_LENGTH = 55f
        val FULL_CIRCLE = 360
        val CAPTURE_ANGLE = 10
    }

    open var startPoint = PointF()
    open var endPoint = PointF()
    open var paint: Paint? = null
    open var radius = 0f
    open var blurMaskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.OUTER)

    open fun onSizeChanged(width: Int, height: Int) {}

    open fun onDraw(canvas: Canvas) {
        canvas.drawCircle(startPoint.x, startPoint.y, radius, paint)
    }

    open fun setRadius(width: Int, height: Int) {}
}