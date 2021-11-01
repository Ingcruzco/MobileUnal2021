package com.unal.triqui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var mBoardButtons= arrayOfNulls<Button>(TicTacToeGame.BOARD_SIZE)
    lateinit var nInfoTextView:TextView
    private lateinit var mGame:TicTacToeGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBoardButtons[0]=findViewById(R.id.one)
        mBoardButtons[1]=findViewById(R.id.two)
        mBoardButtons[2]=findViewById(R.id.three)
        mBoardButtons[3]=findViewById(R.id.four)
        mBoardButtons[4]=findViewById(R.id.five)
        mBoardButtons[5]=findViewById(R.id.six)
        mBoardButtons[6]=findViewById(R.id.seven)
        mBoardButtons[7]=findViewById(R.id.eight)
        mBoardButtons[8]=findViewById(R.id.nine)

        nInfoTextView=findViewById(R.id.information)

        mGame=TicTacToeGame()

        startGame()
    }

    private fun startGame(){
        mGame.clearBoard()

        for (i in mBoardButtons.indices){
            mBoardButtons[i]?.setText(TicTacToeGame.OPEN_SPOT.toString())
            mBoardButtons[i]?.setEnabled(true)
            mBoardButtons[i]?.setOnClickListener( ButtonClickListener(i))
        }
        nInfoTextView.setText("Vas primero")
    }

    private fun setMove(player:Char,location: Int){
        mGame.setMove(player,location)
        mBoardButtons[location]?.setEnabled(false)
        mBoardButtons[location]?.setText(player.toString())
        if (player==TicTacToeGame.HUMAN_PLAYER){
            mBoardButtons[location]?.setTextColor(Color.rgb(0,200,0))
        }else{
            mBoardButtons[location]?.setTextColor(Color.rgb(200,0,0))
        }
    }

    inner class ButtonClickListener:View.OnClickListener {
        var location:Int
        constructor(location:Int){
            this.location=location
        }
        override fun onClick(v: View?) {
            if(mBoardButtons[location]?.isEnabled() == true){
                setMove(TicTacToeGame.HUMAN_PLAYER,location)
                var winner: Int=mGame.checkForWinner()
                if (winner==0){
                    nInfoTextView.setText("Turno de Android ")
                    var move:Int = mGame.getComputerMove()
                    setMove(TicTacToeGame.COPUTER_PLAYER,move)
                    winner=mGame.checkForWinner()
                }
                if(winner==0){
                    nInfoTextView.setText("Es tu turno")
                }else if (winner==1){
                    nInfoTextView.setText("Es Empate")
                }else if (winner==2){
                    nInfoTextView.setText("Ganaste!!")
                }else{
                    nInfoTextView.setText("Android Gano")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add("Nuevo Juego")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startGame()
        return true
    }


}