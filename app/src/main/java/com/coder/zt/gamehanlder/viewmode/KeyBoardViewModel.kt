package com.coder.zt.gamehanlder.viewmode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coder.zt.gamehanlder.net.Api
import kotlinx.coroutines.launch

class KeyBoardViewModel:ViewModel() {

    private val TAG = "KeyBoardViewModel"

    val keyResult = MutableLiveData<com.coder.zt.gamehanlder.net.Result>()

    fun sendKeys(keySender:com.coder.zt.gamehanlder.net.Result){
        Log.d(TAG, "sendKeys: ${keySender.curCodes}")
        viewModelScope.launch {
            keyResult.value = Api.getInstance().sendKeys(keySender)
        }
    }

}