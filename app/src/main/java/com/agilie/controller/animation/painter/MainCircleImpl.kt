package com.agilie.controller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.SweepGradient

class MainCircleImpl(val paint: Paint, val colors: IntArray) : Painter {

    var radius: Float = 0f
    var center = PointF()

    override fun onDraw(canvas: Canvas) {
        paint.shader = SweepGradient(center.x, center.y, colors, null)
        canvas.drawCircle(center.x, center.y, radius, paint)

    }

    override fun onSizeChanged(w: Int, h: Int) {
    }
}