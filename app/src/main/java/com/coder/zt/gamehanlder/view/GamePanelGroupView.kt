package com.coder.zt.gamehanlder.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class GamePanelGroupView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs)  {

    companion object{
        private const val TAG = "GamePanelGroupView"
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (ev != null) {
//            Log.d(TAG, "dispatchTouchEvent:pointerCount =  ${ev.pointerCount}")
//            val pointerCount = ev.pointerCount
//            for(i in 0 until pointerCount){
//                Log.d(TAG, "dispatchTouchEvent: i --> $i")
//                val index = ev.findPointerIndex(i)
//                val x = ev.getX(index)
//                val y = ev.getY(index)
//                Log.d(TAG, "dispatchTouchEvent:第${i+1}个触摸点 ($x, $y)")
//                return x>1200
//            }
//            when (ev.actionMasked) {
//                MotionEvent.ACTION_POINTER_DOWN-> {
//                    Log.d(TAG, "dispatchTouchEvent: 屏幕上已存在点击事件")
//
//                }
//            }
//        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}