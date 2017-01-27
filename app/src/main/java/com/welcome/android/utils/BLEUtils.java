package com.welcome.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import com.welcome.android.objects.iBeacon;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.List;

/**
 * Created by Kraji on 1/26/2017.
 */

// TODO: BLE not working!!!
public class BLEUtils implements BeaconConsumer {
    private final String TAG = "BLE";
    private final String uuid = "2F235454-CF6D-4A0F-ADF2-F4911BA9AFA6";
    private final String uniqueIdentifer = "welcome-android-ble-consumer";
    private List<iBeacon> firebaseBeacons;
    private BeaconManager beaconManager;
    private Activity activity;

    public BLEUtils(Activity activity, final List<iBeacon> firebaseBeacons) {
        this.activity = activity;
        this.firebaseBeacons = firebaseBeacons;
        beaconManager = BeaconManager.getInstanceForApplication(activity);
        beaconManager.bind(this);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return true;
    }

    @Override
    public Context getApplicationContext() {
        return activity.getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection s) {
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "Region Entered: " + region.toString());
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "Region Exited: " + region.toString());
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                Log.i(TAG, "Region State Determined (" + i + "): " + region.toString());
            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.i(TAG, "Region ranging: " + region.toString());
                if (beacons.size() == 0) Log.i(TAG, "no becons detected!");
                else Log.i(TAG, "beacons detected!!!");
                for (Beacon beacon : beacons) {
                    int detectedMajor = beacon.getId2().toInt();
                    int detectedMinor = beacon.getId3().toInt();
                    boolean existsOnFirebase = false;
                    for (iBeacon firebaseBeacon : firebaseBeacons) {
                        int major = firebaseBeacon.getMajor();
                        int minor = firebaseBeacon.getMinor();
                        if (major == detectedMajor && minor == detectedMinor) {
                            Log.i(TAG, "Firebase iBeacon detected " + beacon.getDistance() + " meters away.");
                            existsOnFirebase = true;
                            break;
                        }
                    }
                    if (!existsOnFirebase) {
                        Log.i(TAG, "iBeacon detected " + beacon.getDistance() + " meters away, but not on Firebase");
                    }
                }
            }
        });
        try {
            Region r = new Region(uniqueIdentifer, Identifier.parse(uuid), null, null);
            Log.i(TAG, "Region created: " + r.toString());
            beaconManager.startMonitoringBeaconsInRegion(r);
            beaconManager.startRangingBeaconsInRegion(r);
        } catch (RemoteException e) {
            Log.i(TAG, e.toString());
        }
    }
}