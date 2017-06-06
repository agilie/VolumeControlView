package com.agilie.controller.animation.painter

import android.content.Context
import android.graphics.*
import android.view.View


class BackgroundShining {

    class CustomView(context: Context) : View(context) {
        val paint = Paint()
        val paint1 = Paint()
        val paint2 = Paint()
        val linearColors = intArrayOf(
                Color.parseColor("#000000"),
                Color.parseColor("#000000"),
                Color.parseColor("#000000"),
                Color.parseColor("#00000000")
        )
        val colorPositions = floatArrayOf(0.15f, 0.4f, 0.5F)

        override fun onDraw(canvas: Canvas?) {
            canvas?.drawColor(Color.parseColor("#000000"))

            setRadialGradient(paint, intArrayOf(Color.parseColor("#FF4081"), Color.parseColor("#FF4081")), null)
            setRadialGradient(paint1, intArrayOf(Color.parseColor("#FF4081"), Color.parseColor("#000000"), Color.parseColor("#000000")), colorPositions)
            setLinearGradien(paint2, linearColors, null)

            canvas?.drawCircle(400F, 400F, 400F, paint)
            canvas?.drawCircle(400F, 400F, 400F, paint1)
            canvas?.drawCircle(400F, 400F, 400F, paint2)
        }

        fun setRadialGradient(paint: Paint, colorArray: IntArray, positionArray: FloatArray?) {
            paint.apply {
                shader = RadialGradient(400F, 400F, 400F, colorArray, positionArray, Shader.TileMode.CLAMP)
            }
        }

        fun setLinearGradien(paint: Paint, colorArray: IntArray, positionArray: FloatArray?){
            paint.apply {
                paint.apply {
                    shader = LinearGradient(400F, 200F, 400F, 420F, colorArray, positionArray, Shader.TileMode.CLAMP)
                }
            }
        }



    }
}