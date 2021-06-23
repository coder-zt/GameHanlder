package com.coder.zt.gamehanlder.viewmode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.gamehanlder.net.Api
import com.coder.zt.gamehanlder.net.ResponseResult
import kotlinx.coroutines.launch

class KeyBoardViewModel:ViewModel() {

    private val TAG = "KeyBoardViewModel"

    fun keyDown(keyCode:Int){
        viewModelScope.launch {
            val message: ResponseResult = Api.getInstance().keyDown(keyCode)
            Log.d(TAG, "keyDown: " + message.keyCode)
        }
    }

    fun keyUp(keyCode:Int){
        viewModelScope.launch {
            val message:ResponseResult = Api.getInstance().keyUp(keyCode)
            Log.d(TAG, "keyUp: " + message.keyCode)
        }
    }
}