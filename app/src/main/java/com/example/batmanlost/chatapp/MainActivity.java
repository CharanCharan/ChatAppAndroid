package com.example.batmanlost.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class MainActivity extends AppCompatActivity {
    private  static  final String TAG = "MainActivity" ;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            Log.i(TAG, "Attempting socket connection");
            mSocket = IO.socket("http://10.10.11.44:3000/");
        }
        catch (URISyntaxException e){
            Toast.makeText(getApplicationContext(),"Not connected",Toast.LENGTH_LONG).show();
            Log.i(TAG, "ERROR : Socket connection failed");
            throw new RuntimeException();
        }

        mSocket.connect();
        if(mSocket.connected()){
            Log.i(TAG, "Socket connection successful");
        }
        else{
            Log.i(TAG, "Socket connection failed");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        Log.i(TAG, "Socket connection destroyed");
    }
}
