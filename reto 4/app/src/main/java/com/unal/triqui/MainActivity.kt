package com.unal.triqui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    var mBoardButtons= arrayOfNulls<Button>(TicTacToeGame.BOARD_SIZE)
    lateinit var nInfoTextView:TextView
    private lateinit var mGame:TicTacToeGame
    companion object{
        const val DIALOG_DIFFICULTY_ID:Int=0
        const val DIALOG_QUIT_ID:Int=1
    }
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
        var inflater:MenuInflater=getMenuInflater()
        inflater.inflate(R.menu.options_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_game -> {
                startGame()
                return true
            }
            R.id.ai_difficulty->{
                showDialog(DIALOG_DIFFICULTY_ID)
                return true
            }
            R.id.quit->{
                showDialog(DIALOG_QUIT_ID)
                return true
            }
        }
        return false
    }

    override fun onCreateDialog(id: Int): Dialog? {
        var dialog: Dialog? =null
        var difficult: String? =null
        var builder:AlertDialog.Builder=AlertDialog.Builder(this)
        when(id){
            0->{

                var levels:Array<String> = arrayOf(
                    resources.getString(R.string.difficulty_easy),
                    resources.getString(R.string.difficulty_harder),
                    resources.getString(R.string.difficulty_expert)
                )

                builder.setSingleChoiceItems(levels,1,DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                    dialog?.dismiss()
                    when(i){

                        0->{
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy)
                            difficult="Facil"
                        }
                        1->{
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder)
                            difficult="Dificil"
                        }
                        2->{
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert)
                            difficult="Eperto"
                        }
                    }
                    Toast.makeText(this,"Se modifico la dificultad a: " + difficult,Toast.LENGTH_LONG).show()

                })
                    .setTitle(R.string.difficulty_choose)
                dialog=builder.create()

            }
            1-> {
                builder.setMessage(R.string.quit_question)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes,DialogInterface.OnClickListener{ dialog, which ->  
                        finish()
                    })
                    .setNegativeButton(R.string.no,null)
                dialog=builder.create()
            }
        }
        return dialog
    }

}

