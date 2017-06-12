package com.agilie.volumecontrol.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.agilie.volomecontrolview.R
import com.agilie.volomecontrolview.R.styleable.*
import com.agilie.volumecontrol.animation.controller.ControllerImpl
import com.agilie.volumecontrol.animation.painter.*



class VolumeControlView : View, View.OnTouchListener {

    companion object {
        val INNER_CIRCLE_STROKE_WIDTH = 4f
        var SECTOR_STEP = 6
        var CONTROLLER_SPACE = 3f
        var MOVABLE_CIRCLE_RADIUS = 10f
    }

    var backgroundLayoutColor = Color.parseColor("#E3E4E5")
    private var splineColor = Color.BLACK
    private var movableCircleColor = Color.rgb(80, 254, 253)
    private var innerCircleColor = Color.rgb(80, 254, 253)
    private var minShiningRadius = 1.3f
    private var maxShiningRadius = 1.5f
    private var shiningStep = 0.004f

    var controller: ControllerImpl? = null
    var colors = intArrayOf(
            Color.parseColor("#0080ff"),
            Color.parseColor("#6000FF"),
            Color.parseColor("#0533FF"),
            Color.parseColor("#C467FF"),
            Color.parseColor("#FFB6C2"),
            Color.parseColor("#E7FBE1"),
            Color.parseColor("#53FFFF"),
            Color.parseColor("#0080ff"))

    private var backgroundColors = intArrayOf(
            Color.parseColor("#FF4081"),
            Color.parseColor("#000000"))

    private var backgroundColorsLine = intArrayOf(
            Color.parseColor("#000000"),
            Color.parseColor("#00000000"))


    constructor(context: Context) : super(context) {
        initAttrs(null)
        initController()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
        initController()
    }

    fun setStartAngle(angle: Int) {
        controller?.startAngle = angle
    }

    fun setStartPercent(percent: Int) {
        when (percent) {
            in 100..Int.MAX_VALUE -> {
                controller?.startAngle = 360
                Log.d("tag", "")
            }
            else -> controller?.startAngle = (percent * 360) / 100
        }
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

    fun setBackgroundShiningColor(fillColor: Int, backgroundColor: Int = backgroundLayoutColor) {
        backgroundColors = intArrayOf(fillColor, backgroundColor)
        backgroundColorsLine = intArrayOf(backgroundColor, Color.parseColor("#00000000"))

        controller?.backgroundShiningImpl?.colors = backgroundColors
        controller?.backgroundShiningImpl?.colors2 = backgroundColorsLine
    }

    fun setShiningMaxRadius(radius: Float) {
        controller?.backgroundShiningImpl?.maxShiningRadius = radius
    }

    fun setShiningMinRadius(radius: Float) {
        controller?.backgroundShiningImpl?.minShiningRadius = radius
    }

    fun setShiningFrequency(step: Float) {
        controller?.backgroundShiningImpl?.frequency = step
    }

    fun getShiningMaxRadius() = controller?.backgroundShiningImpl?.maxShiningRadius
    fun getShiningMinRadius() = controller?.backgroundShiningImpl?.minShiningRadius
    fun getFrequency() = controller?.backgroundShiningImpl?.frequency

    private fun initAttrs(attrs: AttributeSet?) {

        val attributes = context
                .obtainStyledAttributes(attrs, R.styleable.VolumeControlView)
        //Colors attrs
        innerCircleColor = attributes.getColor(VolumeControlView_innerCircleColor, innerCircleColor)
        movableCircleColor = attributes.getColor(VolumeControlView_movableCircleColor, movableCircleColor)
        splineColor = attributes.getColor(VolumeControlView_splineCircleColor, splineColor)

        //Shining attrs
        minShiningRadius = attributes.getFloat(VolumeControlView_minShiningRadius, minShiningRadius)
        maxShiningRadius = attributes.getFloat(VolumeControlView_maxShiningRadius, maxShiningRadius)
        shiningStep = attributes.getFloat(VolumeControlView_shiningFrequency, shiningStep)

        val step = attributes.getInt(VolumeControlView_sectorRadius, SECTOR_STEP)
        val controllerSpace = attributes.getFloat(VolumeControlView_controllerSpace, CONTROLLER_SPACE)
        val movableCircleRadius = attributes.getFloat(VolumeControlView_movableCircleRadius, MOVABLE_CIRCLE_RADIUS)

        //Checks start values
        SECTOR_STEP = if (step > 0) step else SECTOR_STEP
        CONTROLLER_SPACE = if (controllerSpace > 0) controllerSpace else CONTROLLER_SPACE
        MOVABLE_CIRCLE_RADIUS = if (movableCircleRadius > 0) movableCircleRadius else MOVABLE_CIRCLE_RADIUS

        setLayerType(ViewGroup.LAYER_TYPE_SOFTWARE, null)
        setWillNotDraw(false)
        setOnTouchListener(this)
    }

    private fun initController() {
        controller = ControllerImpl(
                InnerCircleImpl(setInnerCirclePaint()),
                MovableCircleImpl(setMovableCirclePaint()),
                SplinePath(Path(), setSplinePathPaint()),
                MainCircleImpl(setMainCirclePaint(), colors),
                BackgroundShiningImpl(Paint(),
                        Paint(),
                        backgroundColors,
                        backgroundColorsLine,
                        minShiningRadius,
                        maxShiningRadius,
                        shiningStep))
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


