package com.etfos.bpeserovic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

public class Server extends Activity implements PingPongMultiplayer.CommunicationInterface{

    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;

    private TextView textIP;

    public static final int SERVERPORT = 8080;

    public boolean isServer;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);
        isServer = true;

        //text = (TextView) findViewById(R.id.text2);
        textIP=(TextView)findViewById(R.id.textIP);

        updateConversationHandler = new Handler();

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();

        //dobavljanje ip adrese
        //WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE); // zastarjelo
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String serverIP = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        textIP.setText(serverIP);
        Log.d("serverIP", serverIP);


    }

    @Override
    protected void onStop() {
        super.onStop();
        isServer = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(SERVERPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                    socket = serverSocket.accept();

                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        private BufferedWriter output;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                this.output = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();

                    updateConversationHandler.post(new updateUIThread(read));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {
        private String msg;
        private boolean connected = false;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            //text.setText(text.getText().toString() + "Client Says: " + msg + "\n");
            connected = true;

            if (connected){
                Intent i = new Intent();
                i.setClass(Server.this, AndroidLauncher.class);
                startActivity(i);
            }
        }
    }
}
