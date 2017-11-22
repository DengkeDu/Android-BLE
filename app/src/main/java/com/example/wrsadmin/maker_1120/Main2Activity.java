package com.example.wrsadmin.maker_1120;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;

public class Main2Activity extends AppCompatActivity {

    Button forward, back, left, right, stop;

    TextView mTextView;
    BluetoothGatt mBluetoothGatt;
    BluetoothDevice mBluetoothDevice;
    BluetoothGattCallback mBluetoothGattCallback=new BluetoothGattCallback() {
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
            if (newState==STATE_CONNECTED) {
                Main2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("Connected");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttonlayout);
        //(BluetoothDevice)mBluetoothDevice = (BluetoothDevice)getIntent().getSerializableExtra("mBluetoothDevice");

        forward = (Button)findViewById(R.id.forward);
        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
        back = (Button)findViewById(R.id.back);
        stop = (Button)findViewById(R.id.stop);


        mTextView = (TextView)findViewById(R.id.textView2);
        mTextView.setText("");
        mBluetoothDevice = MainActivity.mBluetoothDevice;
        mBluetoothGatt = mBluetoothDevice.connectGatt(this, false, mBluetoothGattCallback);

    }
}
