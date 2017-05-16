package pt.ulisboa.tecnico.cmov.locdev.wifiP2p;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.locdev.R;
import pt.ulisboa.tecnico.cmov.projcmu.Client;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;

/**
 * Created by Pedro Alcobia on 12/05/2017.
 */

public class WifiP2pActivity extends AppCompatActivity implements SimWifiP2pManager.PeerListListener, SimWifiP2pManager.GroupInfoListener {
    public static final String TAG = "WifiP2pActivity";

    private SimWifiP2pManager mManager = null;
    private SimWifiP2pManager.Channel mChannel = null;
    private Messenger mService = null;
    private boolean mBound = false;
    private SimWifiP2pSocketServer mSrvSocket = null;
    private SimWifiP2pSocket mCliSocket = null;
    private SimWifiP2pBroadcastReceiver mReceiver;
    private List<SimWifiP2pDevice> nearDevices = new ArrayList<SimWifiP2pDevice>();

    private ServiceConnection mConnection = new ServiceConnection() {
        // callbacks for service binding, passed to bindService()

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mManager = new SimWifiP2pManager(mService);
            mChannel = mManager.initialize(getApplication(), getMainLooper(), null);
            mBound = true;
            Log.d(this.getClass().getName(),"onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
            mManager = null;
            mChannel = null;
            mBound = false;
            Log.d(this.getClass().getName(),"onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getName(),"onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // initialize the WDSim API
        SimWifiP2pSocketManager.Init(getApplicationContext());
        // register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        mReceiver = new SimWifiP2pBroadcastReceiver(this);
        registerReceiver(mReceiver, filter);

        Intent intent = new Intent(this, SimWifiP2pService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound = true;

        //Execute SocketServer
        new IncommingCommTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        Log.d(this.getClass().getName(),"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        mSrvSocket=null;

        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        Log.d(this.getClass().getName(),"onPause");
    }

/*
	 * Listeners associated to Termite
	 */

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList peers) {
        StringBuilder peersStr = new StringBuilder();
        Log.d(this.getClass().getName(), "onPeersAvailable");
        // compile list of devices in range
        List<String> inRange = new ArrayList<String>();
        nearDevices = new ArrayList<SimWifiP2pDevice>();
        for (SimWifiP2pDevice device : peers.getDeviceList()) {
            String devstr = "" + device.deviceName + " (" + device.getVirtIp() + ")\n";
            peersStr.append(devstr);
            if(device.getVirtIp().equals("0.0.0.0")) {inRange.add(device.deviceName);}
            else{
                nearDevices.add(device);
            }
        }
        LocdevApp app = (LocdevApp) getApplicationContext();
        app.setNearBeacons(inRange);

        // display list of devices in range
        new AlertDialog.Builder(this)
                .setTitle("Devices in WiFi Range")
                .setMessage(peersStr.toString())
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public void requestPeers(){
        Log.d(this.getClass().getName(), "requestPeers");
        if (mBound) {
            mManager.requestPeers(mChannel, WifiP2pActivity.this);
        } else {
            Toast.makeText(this, "Service not bound",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGroupInfoAvailable(SimWifiP2pDeviceList devices,
                                     SimWifiP2pInfo groupInfo) {

        // compile list of network members
        StringBuilder peersStr = new StringBuilder();
        for (String deviceName : groupInfo.getDevicesInNetwork()) {
            SimWifiP2pDevice device = devices.getByName(deviceName);
            String devstr = "" + deviceName + " (" +
                    ((device == null)?"??":device.getVirtIp()) + ")\n";
            peersStr.append(devstr);
        }

        // display list of network members
        new AlertDialog.Builder(this)
                .setTitle("Devices in WiFi Network")
                .setMessage(peersStr.toString())
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    public List<SimWifiP2pDevice> getNearDevices(){
        return nearDevices;
    }

    /*
	 * Asynctasks implementing message exchange
	 */

    public class IncommingCommTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Log.d(TAG, "IncommingCommTask started (" + this.hashCode() + ").");
            Log.d(TAG, "Wifi P2p Server received message");
            try {
                mSrvSocket = new SimWifiP2pSocketServer(
                        Integer.parseInt(getString(R.string.port)));
                while (!Thread.currentThread().isInterrupted() && mSrvSocket != null) {
                    try {
                        Log.d(TAG, "Accepting Message");
                        SimWifiP2pSocket sock = mSrvSocket.accept();
                        try {
                            Log.d(TAG, "Message Accepted");

                            ObjectOutputStream outToClient = new ObjectOutputStream(sock.getOutputStream());
                            ObjectInputStream inFromClient = new ObjectInputStream(sock.getInputStream());

                            Request req = (Request) inFromClient.readObject();
                            Client cli = new Client();
                            Log.d(TAG, "got Request");

                            outToClient.writeObject(cli.SendRequest(req));
                            outToClient.flush();
                            Log.d(TAG, "Write Response");


//                            BufferedReader sockIn = new BufferedReader(
//                                    new InputStreamReader(sock.getInputStream()));
//                            String st = sockIn.readLine();
//                            publishProgress(st);
//                            sock.getOutputStream().write(("\n").getBytes());
                        } catch (IOException | ClassNotFoundException e) {
                            Log.d("Error reading socket:", e.getMessage());
                        } finally {
                            sock.close();
                        }
                    } catch (IOException e) {
                        Log.d("Error socket:", e.getMessage());
                        break;
                        //e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
//            mTextOutput.append(values[0] + "\n");
        }
    }
}
