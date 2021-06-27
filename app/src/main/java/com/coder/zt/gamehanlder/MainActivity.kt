package com.coder.zt.gamehanlder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.coder.zt.gamehanlder.net.Result
import com.coder.zt.gamehanlder.view.SkillBtnView
import com.coder.zt.gamehanlder.view.SteeringYokeView
import com.coder.zt.gamehanlder.viewmode.KeyBoardViewModel
import kotlinx.coroutines.delay
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val result = Result()
    val pressKeys = MutableLiveData<MutableSet<Int>>()
    var index:Int = 0
    var selectBtnPressTime: Long = 0
    var sureBtnPressTime:Long = 0
    val mHandler:Handler by lazy{
        Handler(Looper.getMainLooper())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initData() {
        result.perCodes = mutableSetOf()
        result.curCodes = mutableSetOf()
        viewModel.keyResult.observe(this){
            result.index = it.index
//            result.curCodes?.clear()
            result.curCodes?.addAll(it.curCodes)
//            result.perCodes?.clear()
//            result.perCodes?.addAll(it.perCodes)
            result.success = it.success
            StringBuilder().apply {
                append("第${it.index}次：")
                for (code in result?.curCodes!!) {
                    append(code.toChar())
                    append(", ")
                }
                Log.d(TAG, "initData:Server key ---> ${toString()}")
            }
            if (it.curCodes.contains(10)) {
                mHandler.postDelayed({
                    val value1 = pressKeys.value
                    if (value1 != null) {
                        if (value1.contains(10)) {
                            value1.remove(10)
                        }
                    }
                    pressKeys.postValue(value1!!)
                }, 80 - (System.currentTimeMillis() - sureBtnPressTime))
            }
            if (it.curCodes.contains(66)) {
               mHandler.postDelayed({
                    val value1 = pressKeys.value
                    if (value1 != null) {
                        if (value1.contains(66)) {
                            value1.remove(66)
                        }
                    }
                    pressKeys.postValue(value1!!)
                }, 80 - (System.currentTimeMillis() - selectBtnPressTime))

            }
        }
        pressKeys.observe(this){
            Log.d(TAG, "initData: ${pressKeys.value}")
            index++
            StringBuilder().apply {
                append("第${index}次：")
                append("要释放的code:[")
                for (curCode in result.curCodes!!) {
                    append(curCode.toChar())
                    append(", ")
                }
                append("]  要按下的code:[")
                for (curCode in pressKeys.value!!) {
                    append(curCode.toChar())
                    append(", ")//
                }
                Log.d(TAG, "]  initData:Server key ---> ${toString()}")
            }
            viewModel.sendKeys(Result().Index(index).Success(true).PerCodes(result.curCodes).CurCodes(pressKeys.value))
        }

    }

    private val viewModel: KeyBoardViewModel by viewModels()

    private  val TAG = "MainActivity"
    private fun initView() {
        val yokeView = findViewById<SteeringYokeView>(R.id.yoke)
        yokeView.setListener {
            val clearKeys = yokeView.clearKeys(pressKeys, false)
            val keys = mutableSetOf<Int>()
            keys.apply {
                addAll(clearKeys)
                addAll(it)
                pressKeys.postValue(this)
            }
        }
        yokeView.setUpListener {
            yokeView.clearKeys(pressKeys, true)
        }
        val skillBtnView = findViewById<SkillBtnView>(R.id.skill)
        skillBtnView.setListener {
            var keys = pressKeys.value
            if(keys == null){
                keys = mutableSetOf()
            }
            val clearKeys = skillBtnView.clearKeys(pressKeys, false)
            keys.apply {
                addAll(it)
                addAll(clearKeys)
                pressKeys.postValue(this)
            }
        }
        skillBtnView.setUpListener {
            skillBtnView.clearKeys(pressKeys,true)
        }
        val sureBtn = findViewById<TextView>(R.id.sure_btn_tv)
        sureBtn.setOnClickListener(){
            sureBtnPressTime = System.currentTimeMillis()
            var keys = pressKeys.value
            if(keys == null){
                keys = mutableSetOf()
            }
            keys?.apply {
                add(10)
                pressKeys.postValue(this)
//                remove(13)bbb
//                pressKeys.postValue(this)

            }

        }
        val selectBtn = findViewById<TextView>(R.id.select_btn_tv)
        selectBtn.setOnClickListener(){//
            selectBtnPressTime = System.currentTimeMillis()
            var keys = pressKeys.value
            if(keys == null){
                keys = mutableSetOf()
            }
            if (keys != null) {
                Log.d(TAG, "initView: ")
            }else{
                Log.d(TAG, "initView: ")
            }
            keys?.apply {
                add(66)
                pressKeys.postValue(this)
            }
        }
    }
}