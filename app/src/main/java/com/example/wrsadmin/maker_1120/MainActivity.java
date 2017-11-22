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
                    Log.d("TAG",bt.getName());
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

    BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState==STATE_CONNECTED){
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //this.getActionBar().setTitle("Connected!");
                        mTextView.append("Connected\n");
                    }
                });

            }else {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.append("Disconnected\n");
                    }
                });
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
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
        mTextView.setText("Disconnected!");
        if(mBluetoothDevice!=null){
            mTextView.setText("In a connection!");
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
