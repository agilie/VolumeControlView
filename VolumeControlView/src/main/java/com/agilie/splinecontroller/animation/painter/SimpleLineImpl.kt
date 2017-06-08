package com.agilie.splinecontroller.animation.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class SimpleLineImpl(val paint: Paint) : Painter {

    var startPoint = PointF()
    var endPoint = PointF()

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(startPoint.x,
                startPoint.y, endPoint.x, endPoint.y, paint)
    }

    override fun onSizeChanged(w: Int, h: Int) {
    }

}

