package com.agilie.controller.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.agilie.controller.R
import com.agilie.controller.animation.controller.ControllerImpl
import com.agilie.controller.animation.painter.InnerCircleImpl
import com.agilie.controller.animation.painter.MainCircleImpl
import com.agilie.controller.animation.painter.MovableCircleImpl
import com.agilie.controller.animation.painter.SplinePath


class ControllerView : View, View.OnTouchListener {

    companion object {
        val INNER_CIRCLE_STROKE_WIDTH = 4f
        var SECTOR_STEP = 6
        var CONTROLLER_SPACE = 3f
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
    private var splineColor = Color.BLACK
    private var movableCircleColor = Color.rgb(80, 254, 253)
    private var innerCircleColor = Color.rgb(80, 254, 253)

    var colors = intArrayOf(
            Color.parseColor("#6000FF"),
            Color.parseColor("#C467FF"),
            Color.parseColor("#FFB6C2"),
            Color.parseColor("#E7FBE1"),
            Color.parseColor("#53FFFF"))

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        controller?.onSizeChanged(w, h)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        controller?.onTouchEvent(event)
        return true
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        controller?.onSaveInstanceState(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val bundle = state as Bundle
        controller?.onRestoreInstanceState(bundle)
        super.onRestoreInstanceState(bundle.getParcelable<Parcelable>("superState"))
    }

    private fun init(attrs: AttributeSet?) {

        val a = context
                .obtainStyledAttributes(attrs, R.styleable.ControllerView)
        innerCircleColor = a.getColor(R.styleable.ControllerView_innerCircleColor, innerCircleColor)
        movableCircleColor = a.getColor(R.styleable.ControllerView_movableCircleColor, movableCircleColor)
        splineColor = a.getColor(R.styleable.ControllerView_splineCircleColor, splineColor)

        val step = a.getInt(R.styleable.ControllerView_sectorRadius, SECTOR_STEP)
        val controllerSpace = a.getFloat(R.styleable.ControllerView_controllerSpace, CONTROLLER_SPACE)

        SECTOR_STEP = if (step > 0) step else SECTOR_STEP
        CONTROLLER_SPACE = if (controllerSpace > 0) controllerSpace else CONTROLLER_SPACE

        setLayerType(ViewGroup.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
        setOnTouchListener(this)
        controller = ControllerImpl(
                InnerCircleImpl(setInnerCirclePaint()),
                MovableCircleImpl(setMovableCirclePaint()),
                SplinePath(Path(), setSplinePathPaint()),
                MainCircleImpl(setMainCirclePaint(), colors))
    }


    private fun setInnerCirclePaint() = Paint().apply {
        color = innerCircleColor
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = INNER_CIRCLE_STROKE_WIDTH
    }

    private fun setMovableCirclePaint() = Paint().apply {
        color = movableCircleColor
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private fun setSplinePathPaint() = Paint().apply {
        color = splineColor
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 2f
    }

    private fun setMainCirclePaint() = Paint().apply {
        strokeCap = Paint.Cap.SQUARE
        strokeWidth = 1F
        style = Paint.Style.FILL

    }

}


