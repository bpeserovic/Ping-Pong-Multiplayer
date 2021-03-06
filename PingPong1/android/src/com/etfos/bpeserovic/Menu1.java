package com.etfos.bpeserovic;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

public class Menu1 extends Activity implements View.OnClickListener {

    Button bNewGame;
    Button bScores;

    //UUID kod za Bluetooth, isti je za server i client
    public static String MY_UUID = "7c482781-528a-4384-84a3-7b5006a66f16";

    private static Context Main;

    public static Context getContext() {
        return Main.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Main = this;
        setContentView(R.layout.activity_main_menu1);
        inicijalizacija();

    }

    public void inicijalizacija(){
        bNewGame = (Button) findViewById(R.id.newGame);
        bScores  = (Button) findViewById(R.id.scores);

        bNewGame.setOnClickListener(this);
        bScores.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Intent i = new Intent();

        switch (v.getId()) {
            case R.id.newGame:
                i.setClass(Menu1.this, Menu2.class);
                break;
            /*case R.id.scores:
                i.setClass(MainMenu1.this, Scores.class);
                break;*/
        }
        startActivity(i);
    }

}