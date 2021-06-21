package com.coder.zt.gamehanlder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import com.coder.zt.gamehanlder.view.SteeringYokeView
import com.coder.zt.gamehanlder.view.SteeringYokeView.OnKeyBoardListener
import com.coder.zt.gamehanlder.viewmode.KeyBoardViewModel
import java.lang.StringBuilder
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val keyDownList:MutableList<Int> by lazy {
        mutableListOf()
    }

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
            for( i in keyDownList){
                if(!it.contains(i)){
                    when(i){
                        SteeringYokeView.DIR_DOWN -> {
                            viewModel.keyUp(83)
                        }
                        SteeringYokeView.DIR_UP -> {
                            viewModel.keyUp(87)
                        }
                        SteeringYokeView.DIR_LEFT -> {
                            viewModel.keyUp(65)
                        }//a
                        SteeringYokeView.DIR_RIGHT -> {
                            viewModel.keyUp(68)
                        }
                    }
                    keyDownList.remove(i)
                }
            }

            for (i in it){
                when(i){
                    SteeringYokeView.DIR_DOWN -> {
                        if(!keyDownList.contains(SteeringYokeView.DIR_DOWN)){
                            viewModel.keyDown(83)
                            keyDownList.add(SteeringYokeView.DIR_DOWN)
                        }
                    }
                    SteeringYokeView.DIR_UP -> {
                        if(!keyDownList.contains(SteeringYokeView.DIR_UP)){
                            viewModel.keyDown(87)
                            keyDownList.add(SteeringYokeView.DIR_UP)
                        }
                    }
                    SteeringYokeView.DIR_LEFT -> {
                        if(!keyDownList.contains(SteeringYokeView.DIR_LEFT)){
                            viewModel.keyDown(65)
                            keyDownList.add(SteeringYokeView.DIR_LEFT)
                        }
                    }//a
                    SteeringYokeView.DIR_RIGHT -> {
                        if(!keyDownList.contains(SteeringYokeView.DIR_RIGHT)){
                            viewModel.keyDown(68)
                            keyDownList.add(SteeringYokeView.DIR_RIGHT)
                        }
                    }
                }
            }

        }
        yokeView.setUpListener {
            for (i in keyDownList){
                when(i){
                    SteeringYokeView.DIR_DOWN -> {
                        viewModel.keyUp(83)
                    }
                    SteeringYokeView.DIR_UP -> {
                        viewModel.keyUp(87)
                    }
                    SteeringYokeView.DIR_LEFT -> {
                        viewModel.keyUp(65)
                    }
                    SteeringYokeView.DIR_RIGHT -> {
                        viewModel.keyUp(68)
                    }
                }
                keyDownList.remove(i)
            }
        }
    }
}