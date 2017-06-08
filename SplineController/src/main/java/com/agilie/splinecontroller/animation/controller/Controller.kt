package com.agilie.splinecontroller.animation.controller

import android.graphics.Canvas

interface Controller {

    fun onDraw(canvas: Canvas)
    fun onSizeChanged(width: Int, height: Int)
}