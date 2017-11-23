package com.example.wrsadmin.maker_1120;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    Button scan_button;
    Button connect_button;
    BluetoothLeScanner mBluetoothLeScanner;
    BluetoothGatt mBluetoothGatt;

    LinearLayout mLinearLayout;

    //Bluetooth related
    BluetoothAdapter mBluetoothAdapter;


    //Bluetooth scan
    Handler mHandler;
    ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice bt = result.getDevice();
            if (bt!=null) {
                if (bt.getName()!=null) {

                    if (bt.getName().contains("LEDCB")) {
                        if(mBluetoothDevice==null){
                            mBluetoothDevice = bt;
                            mTextView.setText("Arduino 101 connected!\n");
                        }
                    }
                }
            }
        }
    };

    //Bluetooth device
    public static BluetoothDevice mBluetoothDevice;

    //Bluetooth gatt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.textView);
        connect_button = (Button)findViewById(R.id.button6);
        scan_button = (Button)findViewById(R.id.button7);

        //Start bluetooth
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter!=null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 0);
            }
        }else {
            Toast.makeText(getApplicationContext(), "You device doesn't support the Bluetooth", Toast.LENGTH_LONG).show();
        }

        mHandler = new Handler();
    }

    public void onClickScan (View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            };
        }

        if(mBluetoothDevice!=null){
            mTextView.setText("In a connection!");
        }else {
            mTextView.setText("Waiting for arudino 101 to connectting ...");
        }
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        if (mBluetoothLeScanner!=null) {
            mBluetoothLeScanner.startScan(mScanCallback);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothLeScanner.stopScan(mScanCallback);
                }
            }, 60000);
        }
    }

    public void onClickConnect (View view) {
        if (mBluetoothDevice!=null) {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("mBluetoothDevice",mBluetoothDevice);
            startActivity(intent);
        }
        else{
            mTextView.setText("No bluetooth device\n");
        }
    }
}
