package com.agilie.controller.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.agilie.controller.animation.controller.ControllerImpl
import com.agilie.controller.animation.painter.InnerCircleImpl
import com.agilie.controller.animation.painter.MainCircleImpl
import com.agilie.controller.animation.painter.MovableCircleImpl
import com.agilie.controller.animation.painter.SplinelPath


class ControllerView : View, View.OnTouchListener {

    companion object {
        val INNER_CIRCLE_STROKE_WIDTH = 4f
        val SECTOR_STEP = 6
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

    private var controller: ControllerImpl? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
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

    private fun init(attrs: AttributeSet?) {



        //val array =  a.getTextArray(R.styleable.ControllerView_controllerColor, controllerColor)

        setLayerType(ViewGroup.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
        setOnTouchListener(this)
        controller = ControllerImpl(
                InnerCircleImpl(setInnerCirclePaint()),
                MovableCircleImpl(setMovableCirclePaint()),
                SplinelPath(Path(), setSpiralPathPaint()),
                MainCircleImpl(setMainCirclePaint()))
    }

    private fun setInnerCirclePaint() = Paint().apply {
        color = Color.rgb(80, 254, 253)
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = INNER_CIRCLE_STROKE_WIDTH
    }

    private fun setMovableCirclePaint() = Paint().apply {
        color = Color.rgb(80, 254, 253)
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private fun setSpiralPathPaint() = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 2f
    }

    private fun setMainCirclePaint(): Paint {
        val colors = intArrayOf(
                Color.parseColor("#6000FF"),
                Color.parseColor("#C467FF"),
                Color.parseColor("#FFB6C2"),
                Color.parseColor("#E7FBE1"),
                Color.parseColor("#53FFFF"))
        val paint = Paint()
        paint.apply {
            strokeCap = Paint.Cap.SQUARE
            strokeWidth = 1F
            style = Paint.Style.FILL

        }
        return paint
    }

}


