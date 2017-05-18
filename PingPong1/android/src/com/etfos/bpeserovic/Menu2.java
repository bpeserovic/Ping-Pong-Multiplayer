package com.etfos.bpeserovic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu2 extends Activity implements View.OnClickListener{

    Button bHostGame;
    Button bJoinGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);
        inicijalizacija();
    }

    public void inicijalizacija(){
        bHostGame = (Button) findViewById(R.id.hostGame);
        bJoinGame = (Button) findViewById(R.id.joinGame);

        bHostGame.setOnClickListener(this);
        bJoinGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();

        switch (v.getId()) {
            case R.id.hostGame:
                i.setClass(Menu2.this, Server.class);

                ////// PRIVREMENI KOD
                /////
                //i.setClass(Menu2.this, AndroidLauncher.class);
                /////

                break;
            case R.id.joinGame:
                i.setClass(Menu2.this, Client.class);
                break;
        }
        startActivity(i);

    }
}
