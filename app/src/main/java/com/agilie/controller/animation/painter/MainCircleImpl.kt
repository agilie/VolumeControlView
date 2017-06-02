package com.agilie.controller.animation.painter

import android.graphics.*

class MainCircleImpl(val paint: Paint) : Painter {

    var radius: Float = 0f
    var center = PointF()

    val colors = intArrayOf(
            Color.parseColor("#6000FF"),
            Color.parseColor("#C467FF"),
            Color.parseColor("#FFB6C2"),
            Color.parseColor("#E7FBE1"),
            Color.parseColor("#53FFFF"))

    override fun onDraw(canvas: Canvas) {
        paint.shader = SweepGradient(center.x, center.y, colors, null)

        canvas.drawCircle(center.x, center.y, radius, paint)
    }

    override fun onSizeChanged(w: Int, h: Int) {
    }
}