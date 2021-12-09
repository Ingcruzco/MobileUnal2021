package com.unal.triqui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MultiPlayerGameSelectionActivity : AppCompatActivity() {
    lateinit var onlineBtn:Button
    lateinit var offlineBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_player_game_selection)
        onlineBtn=findViewById(R.id.onlineBtn)
        offlineBtn=findViewById(R.id.offlineBtn)
        onlineBtn.setOnClickListener{
            startActivity(Intent(this,OnlineCodeGeneratorActivity::class.java))
        }
        offlineBtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }



}