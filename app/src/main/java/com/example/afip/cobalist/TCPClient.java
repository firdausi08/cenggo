package com.example.afip.cobalist;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by afip on 7/25/2017.
 */

public class TCPClient {
    public static final String SERVER_IP = "192.168.43.3"; //server IP address
    public static final int SERVER_PORT = 4444;

    private OnMessageReceived listener;
    private boolean mRun = false;

    public TCPClient(OnMessageReceived listener){
        this.listener = listener;
    }

    public void stopClient(){
        mRun = false;
    }

    public void run(){
        mRun  = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVER_PORT);

            while (mRun){

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnMessageReceived{
        public void messageReceived(String message);
    }
}
