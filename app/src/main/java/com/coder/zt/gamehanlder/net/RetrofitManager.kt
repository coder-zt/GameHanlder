package com.coder.zt.gamehanlder.net

import com.coder.zt.gamehanlder.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private var retrofit:Retrofit? = null

    init {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL) // 设置网络请求的Url地址
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
            .build()
    }

    fun getRetrofit():Retrofit{
        return retrofit!!
    }

    companion object{
        private var instance:RetrofitManager? = null
        fun getInstance():RetrofitManager{
            val i = instance
            if( i != null){
                return i
            }
            return synchronized(this){
                val i2 = instance
                if(i2 != null){
                    i2
                }else{
                    val created = RetrofitManager()
                    instance = created
                    created
                }
            }

        }
    }
}