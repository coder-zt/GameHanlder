package com.coder.zt.gamehanlder.view


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlin.math.pow
import kotlin.math.sqrt


class SteeringYokeView(context: Context,attrs: AttributeSet): View(context, attrs) {

    private val TAG = "SteeringYokeView"
    private val backgroundColor: Int = Color.parseColor("#eeeeee")
    private val touchPointColor: Int = Color.parseColor("#ffffff")
    private val distance: Int = 100

    companion object{
        const val DIR_LEFT:Int = 65//a
        const val DIR_UP:Int = 87//w
        const val DIR_RIGHT:Int = 68//d
        const val DIR_DOWN:Int = 83//s
    }

    private val backGroundPaint by lazy {
        val paint = Paint()
        paint.color = backgroundColor
        paint
    }
    private val touchPointPaint by lazy {
        val paint = Paint()
        paint.color = touchPointColor
        paint
    }
    private val linePaint by lazy {
        val paint = Paint()
        paint.color = touchPointColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 9F
        paint
    }

    private var currentTouchPoint = Point(width/2, height/2)
    private val yokePoint by lazy{
        Point(width/2, height/2)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.drawCircle(width/2.0f, height/2.0f, height/2.0f, backGroundPaint)
        val dashPathEffect1 = DashPathEffect(floatArrayOf(60f, 60f), 0F)
        linePaint.setPathEffect(dashPathEffect1)
        val mPath = Path()
        mPath.reset()
        mPath.moveTo(0f, width / 2f)
        mPath.lineTo(height *1.0f, width/2f)
        canvas?.drawPath(mPath, linePaint)
        mPath.reset()
        mPath.moveTo(height/2.0f, 0F)
        mPath.lineTo(height/2.0f, width *1.0F)
        canvas?.drawPath(mPath, linePaint)
        Log.d(TAG, "onDraw: currentTouchPoint: $currentTouchPoint")
        if(currentTouchPoint.x <= 0){
            currentTouchPoint.x = width/2
        }
        if(currentTouchPoint.y <= 0){
            currentTouchPoint.y = height/2
        }
        canvas?.drawCircle(yokePoint.x.toFloat(), yokePoint.y.toFloat(), height/2.0f* 0.3f, touchPointPaint)
    }

    private var pointId = -1
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            currentTouchPoint.x = x.toInt()
            currentTouchPoint.y = y.toInt()
            Log.d(TAG, "onTouchEvent: currentTouchPoint $currentTouchPoint")
        }

        when(event?.action){

            MotionEvent.ACTION_DOWN ->{

            }
            MotionEvent.ACTION_MOVE ->{
//                val pointerId = event.getPointerId(0)
//                if(pointId == -1){
//                    pointId = pointerId
//                }else if(pointId != pointerId){
//                    return false
//                }
            }
            MotionEvent.ACTION_POINTER_UP->{
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_UP")
            }
            MotionEvent.ACTION_UP ->{
                Log.d(TAG, "onTouchEvent: ACTION_UP")
                reset()
            }
        }
        calculateYokeLocal()
        invalidate()
        return true
    }

    private fun reset() {
        currentTouchPoint.x = width/2
        currentTouchPoint.y = height/2
        keyUpListener?.invoke()
    }

    private fun calculateYokeLocal() {
        if(width == 0){
            yokePoint.x = currentTouchPoint.x
            yokePoint.y = currentTouchPoint.y
            return
        }
        val distance = getTwoPointDistance(currentTouchPoint, Point(width/2, height/2))
        val visDistance = width * 0.7 * 0.5
        if(distance <= visDistance){
            yokePoint.x = currentTouchPoint.x
            yokePoint.y = currentTouchPoint.y
        }else{
            yokePoint.x = (width/2 +  (currentTouchPoint.x - width/2) * (visDistance/distance)).toInt()
            yokePoint.y = (height/2 + (currentTouchPoint.y - height/2) * (visDistance/distance)).toInt()

        }
        callKeyBoard()
    }

    private fun callKeyBoard(){
        val x = yokePoint.x - width/2
        val y = yokePoint.y - height/2
        Log.d(TAG, "callKeyBoard: ($x ,$y)")
        val keyList = mutableListOf<Int>()
        if(x >distance){
            keyList.add(DIR_RIGHT)
        }else if(x < (-distance)){
            keyList.add(DIR_LEFT)
        }
        if(y >distance){
            keyList.add(DIR_DOWN)
        }else if(y < (-distance)){
            keyList.add(DIR_UP)
        }
        listener?.invoke(keyList)
    }

    private fun getTwoPointDistance(currentTouchPoint: Point, centerPoint: Point): Double {
        val pow = (centerPoint.x - currentTouchPoint.x * 1.0).pow(2.0) +
                    (centerPoint.y - currentTouchPoint.y * 1.0).pow(2.0)
        return sqrt(pow)
    }

    private var listener: ((List<Int>) -> Unit)? = null

    private var keyUpListener: (() -> Unit)? = null

    fun setUpListener(callback:(() -> Unit)){
        keyUpListener = callback
    }

    fun setListener(callback:((List<Int>) -> Unit)){
        listener = callback
    }

    fun clearKeys(pressKeys: MutableLiveData<MutableSet<Int>>, keyUp:Boolean):MutableSet<Int> {
        val temp = mutableSetOf<Int>();
        if (pressKeys.value != null) {
            for(i in pressKeys.value!!){
                if(i != DIR_UP && i != DIR_RIGHT && i != DIR_DOWN && i != DIR_LEFT){
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