package com.coder.zt.gamehanlder.net

import android.util.Log

class Api {
    private  val TAG = "Api"
    suspend fun keyDown(keyCode:Int):ResponseResult{
        Log.d(TAG, "keyDown: ")
        return RetrofitManager.getInstance().getRetrofit().create(KeyBoardServices::class.java).keyDown(keyCode)
    }

    suspend fun keyUp(keyCode:Int):ResponseResult{
        return RetrofitManager.getInstance().getRetrofit().create(KeyBoardServices::class.java).keyUp(keyCode)
    }

    companion object{
        private var instance:Api? = null
        fun getInstance():Api{
            val i = instance
            if( i != null){
                return i
            }
            return synchronized(this){
                val i2 = instance
                if(i2 != null){
                    i2
                }else{
                    val created = Api()
                    instance = created
                    created
                }
            }

        }
    }
}