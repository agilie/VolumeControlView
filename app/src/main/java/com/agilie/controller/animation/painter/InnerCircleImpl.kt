package com.agilie.controller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class InnerCircleImpl(val paint: Paint) : InnerCircle {

    var radius: Float = 0f
    var center = PointF()

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(center.x, center.y, radius, paint)
    }

    override fun onSizeChanged(w: Int, h: Int) {
    }
}