package com.coder.zt.gamehanlder.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.location.GnssAntennaInfo
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlin.math.pow
import kotlin.math.sqrt

class SkillBtnView(context: Context, attrs: AttributeSet): View(context, attrs) {

    val btnColor:List<Int> = listOf(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW)
    val codes:List<Int> = listOf(75, 74, 73,85)
    var btnLocal:MutableList<Point> = mutableListOf()
    var mCenterPoint:Point? = null
    val btnSize:Int = 80
    val btnR:Int = 180
    val paint:Paint = Paint()

    companion object{
        private const val TAG = "SkillBtnView"
    }

    private fun calaBtnLocal(){
        btnLocal.clear()
        mCenterPoint!!.apply {
            btnLocal.add(Point(x.toInt(), (y - btnR).toInt()))
            btnLocal.add(Point((x + btnR).toInt(), y.toInt()))
            btnLocal.add(Point(x.toInt(), ((y + btnR).toInt())))
            btnLocal.add(Point((x - btnR).toInt(), y.toInt()))
        }
        if (mCenterPoint != null) {

        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerPoint = Point(width/2, height/2)
        mCenterPoint = centerPoint
        if(btnLocal.size < 4) {
            calaBtnLocal()
        }
        for(i in 0 until btnColor.size){
            Log.d(TAG, "onDraw: i ---> $i")
            Log.d(TAG, "onDraw: point ---> ${btnLocal[i]}")
            paint.setColor(btnColor[i])
            canvas?.drawCircle(btnLocal[i].x.toFloat(), btnLocal[i].y.toFloat(),
                btnSize.toFloat(), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val downCodes:MutableList<Int> = mutableListOf()
                var i = -1
                for (point in btnLocal) {
                    i += 1
                    val distance:Int = getTwoPointDistance(point, Point(event.x.toInt(), event.y.toInt()))
                    Log.d(TAG, "onTouchEvent: $distance")
                    if(distance<160){
                        downCodes.add(codes[i])
                    }
                }
                Log.d(TAG, "onTouchEvent: $downCodes")
                listener?.invoke(downCodes)//aaaajjjjjjkjkjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjkkkkkjjjjjiiiiuuuuiijijijiiiijiiijijjjjjjjjjj

            }
            if (event.action == MotionEvent.ACTION_UP) {
                keyUpListener?.invoke()

            }
        }
        return true
    }

    private fun getTwoPointDistance(point: Point, mCenterPoint: Point?): Int {
        if (mCenterPoint != null) {
            val pow = (point.x.toDouble() - mCenterPoint.x.toDouble()).pow(2.0) + (point.y.toDouble() - mCenterPoint.y).pow(2.0)
            return sqrt(pow.toFloat()).toInt()
        }
        return -1
    }

    private var listener: ((List<Int>) -> Unit)? = null

    private var keyUpListener: (() -> Unit)? = null

    fun setUpListener(callback:(() -> Unit)){
        keyUpListener = callback
    }

    fun setListener(callback:((List<Int>) -> Unit)){
        listener = callback
    }

    fun clearKeys(pressKeys: MutableLiveData<MutableSet<Int>>, keyUp:Boolean):MutableSet<Int>{
        val temp = mutableSetOf<Int>();
        if (pressKeys.value != null) {
            for(i in pressKeys.value!!){
                if(i !in codes){
                    temp.add(i)
                }
            }
            if(keyUp){
                pressKeys.value = temp
            }
        }
        return temp
    }
}