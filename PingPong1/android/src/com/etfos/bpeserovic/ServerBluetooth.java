package com.etfos.bpeserovic;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.provider.Telephony.Carriers.NAME;


/**
 * Created by Bobo on 29.5.2017..
 */



public class ServerBluetooth extends Activity {
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID fromString (String Menu1.MY_UUID);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }

        class AcceptThread extends Thread {
            private final BluetoothServerSocket mmServerSocket;


            public AcceptThread() {
                // Use a temporary object that is later assigned to mmServerSocket
                // because mmServerSocket is final.
                BluetoothServerSocket tmp = null;
                try {
                    // MY_UUID is the app's UUID string, also used by the client code.
                    tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, UUID.fromString(Menu1.MY_UUID));
                } catch (IOException e) {
                    Log.e(TAG, "Socket's listen() method failed", e);
                }
                mmServerSocket = tmp;
            }

            public void run() {
                BluetoothSocket socket = null;
                // Keep listening until exception occurs or a socket is returned.
                while (true) {
                    try {
                        socket = mmServerSocket.accept();
                    } catch (IOException e) {
                        Log.e(TAG, "Socket's accept() method failed", e);
                        break;
                    }

                    if (socket != null) {
                        // A connection was accepted. Perform work associated with
                        // the connection in a separate thread.
                        //manageMyConnectedSocket(socket);
                        try {
                            mmServerSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            // Closes the connect socket and causes the thread to finish.
            public void cancel() {
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "Could not close the connect socket", e);
                }
            }
        }
    }

}


