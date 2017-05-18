package com.etfos.bpeserovic;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.etfos.bpeserovic.Client.serverIP;

/**
 * Created by Bobo on 12.2.2017..
 */

public class ClientService extends IntentService {

    private boolean connected = false;
    private static final int SERVERPORT = 6000;
    private Socket socket;
    private String serverIP = Client.serverIP;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {



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

                        Intent intent = new Intent();
                        intent.setClass(ClientService.this, AndroidLauncher.class);

                    }

                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        }

        new Thread(new ClientThread()).start();
        return super.onBind(intent);

    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ClientService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


    }
}
