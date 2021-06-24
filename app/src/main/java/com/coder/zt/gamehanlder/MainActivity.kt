package com.coder.zt.gamehanlder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.coder.zt.gamehanlder.view.SteeringYokeView
import com.coder.zt.gamehanlder.viewmode.KeyBoardViewModel

class MainActivity : AppCompatActivity() {

    private var result: com.coder.zt.gamehanlder.net.Result? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private val viewModel: KeyBoardViewModel by viewModels()

    private  val TAG = "MainActivity"
    private fun initView() {
        val yokeView = findViewById<SteeringYokeView>(R.id.yoke)
        yokeView.setListener {
            Log.d(TAG, "initView: $it")
            var perCodes:List<Int>? = null
            if(result!=null) {
                perCodes = result?.perCodes
                if(perCodes == null){
                    perCodes = listOf()
                }
            }else{
                perCodes = listOf()
            }
            Log.d(TAG, "initView: perCodes--->$perCodes")
            viewModel.sendKeys(com.coder.zt.gamehanlder.net.Result().Success(true).PerCodes(perCodes).CurCodes(
                it))

        }
        yokeView.setUpListener {
            var perCodes:List<Int>? = null
            if(result==null) {
                perCodes = listOf()
            }else{
                perCodes = result?.curCodes
                if(perCodes == null){
                    perCodes = listOf()
                }
            }
            if (perCodes != null) {
                viewModel.sendKeys(com.coder.zt.gamehanlder.net.Result().Success(true).PerCodes(perCodes).CurCodes(
                    listOf<Int>()))
            }

        }
        viewModel.keyResult.observe(this){
            Log.d(TAG, "initView: curCodes---> ${it.curCodes}")
            Log.d(TAG, " initView: server: perCodes---> ${it.perCodes}")
            if (result == null) {
                result = it
            }

            result?.perCodes = it.curCodes
        }
    }
}