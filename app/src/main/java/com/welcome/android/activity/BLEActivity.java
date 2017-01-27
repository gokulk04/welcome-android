package com.welcome.android.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.welcome.android.R;
import com.welcome.android.objects.iBeacon;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kraji on 1/26/2017.
 */

public class BLEActivity extends Activity implements BeaconConsumer {
    private static final int PERMISSION_REQUEST_GRANTED = 1;
    private final String TAG = "BLEActivity";
    private final String uuid = "2F235454-CF6D-4A0F-ADF2-F4911BA9AFA6";
    private List<iBeacon> iBeaconList = new ArrayList<>();
    private BeaconManager beaconManager;
    private Task<Void> onCreateTask;

    private void setupBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || this.checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                    || this.checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                    || this.checkSelfPermission(Manifest.permission.BLUETOOTH_PRIVILEGED) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.BLUETOOTH_PRIVILEGED
                        }, PERMISSION_REQUEST_GRANTED);
                    }
                });
                builder.show();
            } else {
                setupBeaconManager();
            }
        } else {
            setupBeaconManager();
        }

        Log.i("test", "on create started");
        onCreateTask = iBeacon.getAll().continueWith(new Continuation<List<iBeacon>, Void>() {
            @Override
            public Void then(@NonNull Task<List<iBeacon>> task) throws Exception {
                iBeaconList = task.getResult();
                Log.i("test", "on create finished");
                return null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_GRANTED: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            setupBeaconManager();
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i("test", "entered region");
                Log.i("test", region.toString());
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i("test", "exit region");
                Log.i("test", region.toString());
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                Log.i("test", "determine state for region");
                Log.i("test", i + "");
                Log.i("test", region.toString());
            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.i("test", "Region in range: " + region.toString());
                if (beacons.size() == 0) Log.i("test", "no becons detected!");
                else Log.i("test", "beacons detected!!!");
                for (Beacon beacon : beacons) {
                    int detectedMajor = beacon.getId2().toInt();
                    int detectedMinor = beacon.getId3().toInt();
                    boolean existsOnFirebase = false;
//                    for (iBeacon firebaseBeacon : iBeaconList) {
//                        int major = firebaseBeacon.getMajor();
//                        int minor = firebaseBeacon.getMinor();
//                        if (major == detectedMajor && minor == detectedMinor) {
//                            Log.i(TAG, "Firebase iBeacon detected " + beacon.getDistance() + " meters away.");
//                            existsOnFirebase = true;
//                            break;
//                        }
//                    }
                    if (!existsOnFirebase) {
                        Log.i(TAG, "iBeacon detected " + beacon.getDistance() + " meters away, but not on Firebase");
                    }
                }
            }
        });

        try {
            Region r = new Region("myRangingUniqueId", null, null, null);
            Log.i("test", "Region created: " + r.toString());
            beaconManager.startMonitoringBeaconsInRegion(r);
            beaconManager.startRangingBeaconsInRegion(r);
        } catch (RemoteException e) {
            Log.i("test", e.toString());
        }
    }
}