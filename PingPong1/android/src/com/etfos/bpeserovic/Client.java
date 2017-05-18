package com.etfos.bpeserovic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

//import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class Client extends Activity {


    private Socket socket;

    private static final int SERVERPORT = 6000;
    //private static final String SERVER_IP = "192.168.1.7";

    //ovo gledaj, ovako isto za string
    //private String serverIP = "";
    //private String port;

    private Button connectPhones;

    private boolean connected = false;

    private EditText serverIPText;

    //moje varijable

    public static String serverIP = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);

        serverIPText = (EditText) findViewById(R.id.etServerIP);
        //EditText et = (EditText) findViewById(R.id.EditText01);
        //EditText et2 = (EditText) findViewById(R.id.port);
        connectPhones = (Button) findViewById(R.id.connectPhones);
        connectPhones.setOnClickListener(connectListener);
        //ovo gledaj
        //port = et2.getText().toString();


        //new Thread(new ClientThread()).start();//ovo se treba napraviti nakon što se unese ip adresa
    }

    private View.OnClickListener connectListener = new View.OnClickListener() {
/*
        @Override
        public void onClick(View v) {
            if (!connected) {
                serverIP = serverIPText.getText().toString();
                if (!serverIP.equals("")) {
                    //Thread cThread = new Thread(new ClientThread());
                    //cThread.start();
                    Intent intent = new Intent();
                    intent.setClass(Client.this, ClientService.class);
                }
            }
        }*/

        @Override
        public void onClick(View view) {
            try {
                Thread cThread = new Thread(new ClientThread());
                cThread.start(); //pokreće thread nakon unosa adrese
                //String str = et.getText().toString();
                //PrintWriter out = new PrintWriter(new BufferedWriter(
                //        new OutputStreamWriter(socket.getOutputStream())),
                //        true);
                //out.println(str);
            } /*catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/ catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    ///////
    //////////////ovo bi trebalo biti u ClientService skripti sada
    //////


    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                //ovo gledaj
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                socket = new Socket(serverAddr, SERVERPORT);
                connected = true;
                while (connected){
                    try {
                        PrintWriter out = new PrintWriter(new BufferedWriter((new OutputStreamWriter(socket.getOutputStream()))), true);
                        out.println("Connected");

                        //pokretanje igre s client strane


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent();
                    i.setClass(Client.this, AndroidLauncher.class);

                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}
