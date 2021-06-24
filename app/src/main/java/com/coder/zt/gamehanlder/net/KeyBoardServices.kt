package com.coder.zt.gamehanlder.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface KeyBoardServices {

    @POST("/keyboard/keys")
    fun sendKeys(@Body result:Result): Call<Result>
}