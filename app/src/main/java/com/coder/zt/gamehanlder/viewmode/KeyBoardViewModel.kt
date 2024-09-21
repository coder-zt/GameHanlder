package com.coder.zt.gamehanlder.viewmode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.gamehanlder.net.Api
import com.coder.zt.gamehanlder.net.Result
import com.coder.zt.gamehanlder.utils.ClientFinder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeyBoardViewModel:ViewModel() {

    private val TAG = "KeyBoardViewModel"

    val keyResult = MutableLiveData<Result>()
    val clientIPs = MutableLiveData<MutableList<String>>(mutableListOf())

    fun sendKeys(keySender:com.coder.zt.gamehanlder.net.Result){
        Log.d(TAG, "sendKeys: ${keySender.curCodes}")
        viewModelScope.launch {
            keyResult.value = Api.getInstance().sendKeys(keySender)
        }
    }

    fun getClientIP(){
       viewModelScope.launch {
           val t = System.currentTimeMillis()
           val res = ClientFinder.findTargetDevices()
           val time = System.currentTimeMillis() - t
           println("============> 耗时：${time}ms 可用设备：${res.size}")
           res.forEach{
               println("=========>　$it")
           }
       }
        }

}