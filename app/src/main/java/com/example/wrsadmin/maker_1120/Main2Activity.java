package com.example.wrsadmin.maker_1120;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.UUID;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;

public class Main2Activity extends AppCompatActivity {

    Button forward, back, left, right, stop;
    boolean flag_f=true, flag_l=true, flag_b=true, flag_r=true, flag_s=true;

    TextView mTextView;
    BluetoothGatt mBluetoothGatt;
    BluetoothGattService mBluetoothGattService;
    BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    BluetoothDevice mBluetoothDevice;

    UUID serviceid = UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1214");
    UUID characterid = UUID.fromString("19b10001-e8f2-537e-4f6c-d104768a1214");

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
                gatt.discoverServices();
                Main2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("Connected");
                    }
                });
            }else {
                MainActivity.mBluetoothDevice = null;
                Main2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("Disconnected");
                    }
                });
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            mBluetoothGattService = gatt.getService(serviceid);
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

    public void onClickForward (View view) {
        mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(characterid);
        byte[] forward = new byte[4];
        if(flag_f) {
            forward[0] = 'f';
            forward[1] = '0';
            forward[2] = '0';
            forward[3] = '0';
            flag_f=false;
        }else{
            forward[0] = 'f';
            forward[1] = '1';
            forward[2] = '0';
            forward[3] = '0';
            flag_f=true;
        }
        mBluetoothGattCharacteristic.setValue(forward);
        mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
    }

    public void onClickLeft (View view) {
        mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(characterid);
        byte[] forward = new byte[4];
        if(flag_l) {
            forward[0] = 'l';
            forward[1] = '0';
            forward[2] = '0';
            forward[3] = '0';
            flag_l=false;
        }else{
            forward[0] = 'l';
            forward[1] = '1';
            forward[2] = '0';
            forward[3] = '0';
            flag_l=true;
        }
        mBluetoothGattCharacteristic.setValue(forward);
        mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
    }

    public void onClickBack (View view) {
        mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(characterid);
        byte[] forward = new byte[4];
        if(flag_b) {
            forward[0] = 'b';
            forward[1] = '0';
            forward[2] = '0';
            forward[3] = '0';
            flag_b=false;
        }else{
            forward[0] = 'b';
            forward[1] = '1';
            forward[2] = '0';
            forward[3] = '0';
            flag_b=true;
        }
        mBluetoothGattCharacteristic.setValue(forward);
        mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
    }

    public void onClickRight (View view) {
        mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(characterid);
        byte[] forward = new byte[4];
        if(flag_r) {
            forward[0] = 'r';
            forward[1] = '0';
            forward[2] = '0';
            forward[3] = '0';
            flag_r=false;
        }else{
            forward[0] = 'r';
            forward[1] = '1';
            forward[2] = '0';
            forward[3] = '0';
            flag_r=true;
        }
        mBluetoothGattCharacteristic.setValue(forward);
        mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
    }

    public void onClickStop (View view) {
        mBluetoothGattCharacteristic = mBluetoothGattService.getCharacteristic(characterid);
        byte[] forward = new byte[4];
        if(flag_s) {
            forward[0] = 's';
            forward[1] = '0';
            forward[2] = '0';
            forward[3] = '0';
            flag_s=false;
        }else{
            forward[0] = 's';
            forward[1] = '1';
            forward[2] = '0';
            forward[3] = '0';
            flag_s=true;
        }
        mBluetoothGattCharacteristic.setValue(forward);
        mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
    }
}
