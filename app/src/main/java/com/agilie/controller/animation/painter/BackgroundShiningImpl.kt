package com.agilie.controller.animation.painter

import android.graphics.*

class BackgroundShiningImpl(val paint: Paint,
                            val paint2: Paint,
                            var colors: IntArray,
                            var colors2: IntArray) : Painter {

    var radius: Float = 0f
    var center = PointF()

    override fun onDraw(canvas: Canvas) {
        paint.shader = RadialGradient(center.x, center.y, radius * 1.5f, colors,
                floatArrayOf(0.01F, 0.99F), Shader.TileMode.CLAMP)

        paint2.shader = LinearGradient(400F, 200F, 400F, 420F, colors2,
                null,
                Shader.TileMode.CLAMP)

        canvas.drawCircle(center.x, center.y, radius * 1.5f, paint)
        canvas.drawCircle(center.x, center.y, radius * 1.5f, paint2)
    }

    override fun onSizeChanged(w: Int, h: Int) {

    }
}