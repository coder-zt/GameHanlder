package com.coder.zt.gamehanlder.net

import retrofit2.http.POST
import retrofit2.http.Query

interface KeyBoardServices {

    @POST("/keyboard/keydown")
    suspend fun keyDown(@Query("keycode") keyCode:Int):ResponseResult

    @POST("/keyboard/keyup")
    suspend fun keyUp(@Query("keycode") keyCode:Int):ResponseResult
}