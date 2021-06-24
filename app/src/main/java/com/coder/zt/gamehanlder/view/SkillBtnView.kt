package com.coder.zt.gamehanlder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View

class SkillBtnView(context: Context, attrs: AttributeSet): View(context, attrs) {

    val btnColor:List<Int> = listOf<Int>(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW)
    val btnSize:Int = 100
    val btnR:Int = 200
    val paint:Paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centerPoint = Point(width/2, height/2)
        paint.setColor(btnColor[0])
        canvas?.drawCircle(centerPoint.x.toFloat(), (centerPoint.y - btnR).toFloat(),
            btnSize.toFloat(), paint)
        paint.setColor(btnColor[1])
        canvas?.drawCircle(centerPoint.x.toFloat() - btnR, (centerPoint.y).toFloat(),
            btnSize.toFloat(), paint)
        paint.setColor(btnColor[2])
        canvas?.drawCircle(centerPoint.x.toFloat(), (centerPoint.y + btnR).toFloat(),
            btnSize.toFloat(), paint)
        paint.setColor(btnColor[3])
        canvas?.drawCircle(centerPoint.x.toFloat() + btnR, (centerPoint.y).toFloat(),
            btnSize.toFloat(), paint)
    }
}