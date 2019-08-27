package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundcount;

    private int player1points;
    private int player2points;

    private TextView player1TextView;
    private TextView player2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1TextView = findViewById(R.id.text_view_p1);
        player2TextView = findViewById(R.id.text_view_p2);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                String buttonId = "button_"+i+j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(!((Button)view).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button)view).setText("X");
        }

        else{
            ((Button)view).setText("O");
        }

        roundcount++;

        if (checkforWin()){

            if (player1Turn){
                player1wins();
            }

            else{
                player2wins();
            }

        }

        else if (roundcount == 9){
            draw();
        }

        else{
            player1Turn = !player1Turn;
        }

    }

    private boolean checkforWin(){

        String[][] button_text = new String[3][3];

        for (int i = 0; i<3; i++){
            for (int j=0; j<3; j++){
                button_text[i][j] = buttons[i][j].getText().toString();
            }
        }

        //checking rows
        for (int i=0; i<3; i++){
            if(button_text[i][0].equals(button_text[i][1]) && button_text[i][0].equals(button_text[i][2]) && !button_text[i][0].equals("")){
                return true;
            }
        }

        //checking columns
        for (int i=0; i<3; i++){
            if(button_text[0][i].equals(button_text[1][i]) && button_text[0][i].equals(button_text[2][i]) && !button_text[0][i].equals("")){
                return true;
            }
        }

        //checking diagonals
        if(button_text[0][0].equals(button_text[1][1]) && button_text[0][0].equals(button_text[2][2]) && !button_text[0][0].equals("")){
            return true;
        }
        if(button_text[0][2].equals(button_text[1][1]) && button_text[0][2].equals(button_text[2][0]) && !button_text[0][2].equals("")){
            return true;
        }

        return false;

    }

    private void player1wins(){

        player1points++;
        Toast.makeText(this,"Player1 Wins!",Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetboard();

    }

    private void player2wins(){

        player2points++;
        Toast.makeText(this,"Player2 Wins!",Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetboard();

    }

    private void draw(){

        Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        resetboard();

    }

    private void updateScoreText(){

        player1TextView.setText("Player 1 : "+player1points);
        player2TextView.setText("Player 2 : "+player2points);

    }

    private void resetboard(){

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }

        roundcount = 0;
        player1Turn = true;

    }

    private void resetGame(){

        player1points = 0;
        player2points = 0;
        resetboard();
        updateScoreText();
        Toast.makeText(this,"New Game!",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundcount);
        outState.putInt("player1Points",player1points);
        outState.putInt("player2Points",player2points);
        outState.putBoolean("player1Turn",player1Turn);

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        roundcount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1Points");
        player2points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }
}
