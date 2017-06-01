package com.agilie.controller.animation.controller

import android.graphics.Canvas

interface Controller {

    fun onDraw(canvas: android.graphics.Canvas)
    fun onSizeChanged(width: Int, height: Int)
}