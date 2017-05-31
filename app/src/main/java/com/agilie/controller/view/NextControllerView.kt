package com.agilie.controller.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.agilie.controller.animation.NextControllerImpl
import com.agilie.controller.animation.painter.ArcImp
import com.agilie.controller.animation.painter.InnerCircleImp
import com.agilie.controller.animation.painter.MainCircleImp
import com.agilie.controller.animation.painter.MovableCircleImp

class NextControllerView : ViewGroup, View.OnTouchListener {

    companion object {
        val INNER_CIRCLE_STROKE_WIDTH = 4f
        val LINE_LENGTH = 55f
        val FULL_CIRCLE = 360
        val CAPTURE_ANGLE = 10
        val DELTA_TIME = 0.09
        val INCREASE_FACTOR = 6.0
        val DECREASE_FACTOR = 6.0
        val BLUR_MASK_RADIUS = 40f
        val OUTER_BLUR_MASK_RADIUS = 60f
        val MOVABLE_CIRCLE_STROKE = 10f
        val MOVABLE_CIRCLE_RADIUS = 10f
    }

    private var controller: NextControllerImpl? = null

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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        controller?.onSizeChanged(w, h)
        super.onSizeChanged(w, h, oldw, oldh)
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        controller?.onTouchEvent(event)
        return true
    }


    private fun init() {
        setLayerType(ViewGroup.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
        setOnTouchListener(this)
        controller = NextControllerImpl(
                InnerCircleImp(setInnerCirclePaint()),
                MainCircleImp(setMainCirclePaint()),
                MovableCircleImp(setMovableCirclePaint()),
                ArcImp(setSimpleLinePaint()))
    }

    private fun setMainCirclePaint() = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private fun setInnerCirclePaint() = Paint().apply {
        color = Color.rgb(80, 254, 253)
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = INNER_CIRCLE_STROKE_WIDTH
    }

    private fun setSimpleLinePaint() = Paint().apply {
        color = Color.rgb(0, 0, 0)
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 2f
    }

    private fun setMovableCirclePaint() = Paint().apply {
        color = Color.rgb(80, 254, 253)
        isAntiAlias = true
        style = Paint.Style.FILL
    }
}