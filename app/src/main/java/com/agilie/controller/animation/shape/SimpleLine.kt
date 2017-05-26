package com.agilie.controller.animation.shape

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.agilie.controller.animation.Painter

class SimpleLine(var angle: Double) : Painter() {

    private var modify = false

    init {
        paint = getLinePaint()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint)

    }

    fun updateLinePaint(update: Boolean) {
        when (update) {
            true -> {
                modify = true
                paint?.color = Color.rgb(239, 77, 30)
            }
            else -> {
                modify = false
                paint = getLinePaint()
            }
        }
    }

    private fun getLinePaint(): Paint {
        val filter = BlurMaskFilter(2f, BlurMaskFilter.Blur.NORMAL)
        paint = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 2f
            maskFilter = filter
        }
        return paint as Paint
    }
}