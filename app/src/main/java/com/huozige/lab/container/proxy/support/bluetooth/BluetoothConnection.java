package com.huozige.lab.container.proxy.support.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

public class BluetoothConnection {

    protected OutputStream outputStream;
    protected byte[] data;

    /**
     * Add data to send.
     */
    public void write(byte[] bytes) {
        byte[] data = new byte[bytes.length + this.data.length];
        System.arraycopy(this.data, 0, data, 0, this.data.length);
        System.arraycopy(bytes, 0, data, this.data.length, bytes.length);
        this.data = data;
    }


    /**
     * Send data to the device.
     */
    public void send() throws BluetoothConnectionException {
        this.send(0);
    }
    /**
     * Send data to the device.
     */
    public void send(int addWaitingTime) throws BluetoothConnectionException {
        if(!this.isConnected()) {
            throw new BluetoothConnectionException("Unable to send data to device.");
        }
        try {
            this.outputStream.write(this.data);
            this.outputStream.flush();
            int waitingTime = addWaitingTime + this.data.length / 16;
            this.data = new byte[0];
            if(waitingTime > 0) {
                Thread.sleep(waitingTime);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new BluetoothConnectionException(e.getMessage());
        }
    }

    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805f8b33fd");

    private BluetoothDevice device;
    private BluetoothSocket socket = null;

    /**
     * Create un instance of BluetoothConnection.
     *
     * @param device an instance of BluetoothDevice
     */
    public BluetoothConnection(BluetoothDevice device) {
        this.device = device;
        this.outputStream = null;
        this.data = new byte[0];
    }

    /**
     * Get the instance BluetoothDevice connected.
     *
     * @return an instance of BluetoothDevice
     */
    public BluetoothDevice getDevice() {
        return this.device;
    }

    /**
     * Check if OutputStream is open.
     *
     * @return true if is connected
     */
    public boolean isConnected() {
        return this.socket != null && this.socket.isConnected() && this.outputStream != null;
    }

    /**
     * Start socket connection with the bluetooth device.
     */
    @SuppressLint("MissingPermission")
    public BluetoothConnection connect() throws BluetoothConnectionException {
        if (this.isConnected()) {
            return this;
        }

        if (this.device == null) {
            throw new BluetoothConnectionException("Bluetooth device is not connected.");
        }

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        UUID uuid = this.getDeviceUUID();

        try {
            this.socket = this.device.createRfcommSocketToServiceRecord(uuid);
            bluetoothAdapter.cancelDiscovery();
            this.socket.connect();
            this.outputStream = this.socket.getOutputStream();
            this.data = new byte[0];
        } catch (IOException e) {
            e.printStackTrace();
            this.disconnect();
            throw new BluetoothConnectionException("Unable to connect to bluetooth device.");
        }
        return this;
    }

    /**
     * Get bluetooth device UUID
     */
    @SuppressLint("MissingPermission")
    protected UUID getDeviceUUID() {
       ParcelUuid[] uuids = device.getUuids();
        if (uuids != null && uuids.length > 0) {
            if (Arrays.asList(uuids).contains(new ParcelUuid(BluetoothConnection.SPP_UUID))) {
                return BluetoothConnection.SPP_UUID;
            }
            return uuids[0].getUuid();
        } else {
            return BluetoothConnection.SPP_UUID;
        }
    }

    /**
     * Close the socket connection with the bluetooth device.
     */
    public BluetoothConnection disconnect() {
        this.data = new byte[0];
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.outputStream = null;
        }
        if (this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.socket = null;
        }
        return this;
    }
}
