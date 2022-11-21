package com.kotlin.androidsocket;

import android.app.Application;
import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketApplication extends Application {

    private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public Socket getSocket(String room) {

        try {
//            IO.Options options = IO.Options.builder()
//                    .setQuery("room=rohit")
//                    .setTimeout(10000)
//                    .build();

            IO.Options options = new IO.Options();
            options.query="room=rohit";

          //  mSocket = IO.socket(Constants.CHAT_SERVER_URL+room);
            mSocket = IO.socket(URI.create("http://192.168.1.3:8085/"),options);

        } catch (Exception e) {
            Log.e("url_error",""+e.getMessage()+" reason : "+e.getMessage());
            throw new RuntimeException(e);
        }
        return mSocket;
    }

}
