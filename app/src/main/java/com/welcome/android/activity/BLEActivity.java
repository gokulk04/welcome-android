package com.welcome.android.activity;

import android.app.Activity;
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
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.List;

/**
 * Created by Kraji on 1/26/2017.
 */

public class BLEActivity extends Activity implements BeaconConsumer {
    private final String TAG = "BLEActivity";
    private final String uuid = "2F235454-CF6D-4A0F-ADF2-F4911BA9AFA6";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private List<iBeacon> iBeaconList;
    private Task<Void> onCreateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final BeaconConsumer bc = this;
        onCreateTask = iBeacon.getAll().continueWith(new Continuation<List<iBeacon>, Void>() {
            @Override
            public Void then(@NonNull Task<List<iBeacon>> task) throws Exception {
                iBeaconList = task.getResult();
                beaconManager.bind(bc);
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
    public void onBeaconServiceConnect() {
        onCreateTask.continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(@NonNull Task<Void> task) throws Exception {
                beaconManager.setRangeNotifier(new RangeNotifier() {
                    @Override
                    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                        for (Beacon beacon : beacons) {
                            int detectedMajor = beacon.getId2().toInt();
                            int detectedMinor = beacon.getId3().toInt();
                            boolean existsOnFirebase = false;
                            for (iBeacon firebaseBeacon : iBeaconList) {
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
                    beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse(uuid), null, null));
                } catch (RemoteException e) {
                }
                return null;
            }
        });
    }
}