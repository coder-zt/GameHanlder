package com.coder.zt.gamehanlder.net

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Api {
    private  val TAG = "Api"
    suspend fun sendKeys(keyResult:Result):Result{
        Log.d(TAG, "keyDown: ")
        val sendKeyCall =
            RetrofitManager.getInstance().getRetrofit().create(KeyBoardServices::class.java).sendKeys(keyResult)

        return sendKeyCall.await()
    }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
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