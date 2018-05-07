package com.example.dell.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Collection;
import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.myapplication.views.CustomView;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class MainActivity extends Activity implements BeaconConsumer {

    protected static final String TAG = "MainActivity";
    private final String  beaconID1 = "046225e2-1010-4ff7-8ddc-3adef8072912";
    private final String beaconID2 = "ae75ce32-38ae-4fda-b4ce-8cdaa5a04bf6";
    private final String beaconID3 = "16207353-a170-43ec-a0fc-1f34e3bd367d";
    private double distance1;
    private double distance2;
    private double distance3;
    private double x, y;
    private CustomView mCustomView;
    private BeaconManager beaconManager;
    private static EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.rangingText);
        mCustomView = (CustomView) findViewById(R.id.customView);

        beaconManager =  BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setForegroundBetweenScanPeriod(1500l);
        beaconManager.setForegroundBetweenScanPeriod(3100l);
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() < 0){
                    logToDisplay("Please go to level 6 building D!");
                }
                else {
                    //EditText editText = (EditText)RangingActivity.this.findViewById(R.id.rangingText);
                    for (Beacon beacon:beacons){

                        String temp = beacon.getId1().toString();
                        //Log.i(TAG,beacon.getId1().toString() + "\n");
                        //logToDisplay(beacon.getId1() + " at " + beacon.getDistance() + " meters away.");
                        if (temp.equals(beaconID1)){
                            distance1 = beacon.getDistance();
                        }
                        if (temp.equals(beaconID2)){
                            distance2 = beacon.getDistance();
                        }
                        if (temp.equals(beaconID3)){
                            distance3 = beacon.getDistance();
                        }
                    }
                    getPoint(distance1, distance2, distance3);
                    mCustomView.getXY((int)x, (int)y);
                    logToDisplay("Beacon 1 (10-580): " + String.format("%.2f", distance1) + " m | " +
                                "Beacon 2 (10-240): " + String.format("%.2f", distance2) + " m | " +
                                "Beacon 3 (120-240): " + String.format("%.2f", distance3) + " m \n" +
                                "Location (x-y): " + x + " - "+ y);
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private void logToDisplay(final String line) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText(line);
            }
        });
    }
    private void getPoint(double r1, double r2, double r3){
        double x1, y1, x2, y2, x3, y3;
        x1 = 10;
        y1 = 580;
        x2 = 10;
        y2 = 240;
        x3 = 120;
        y3 = 240;
        double A = x1 - x2;
        double B = y1 - y2;
        double D = x1 - x3;
        double E = y1 - y3;

        double T = (r1*r1 - x1*x1 - y1*y1);
        double C = (r2*r2 - x2*x2 - y2*y2) - T;
        double F = (r3*r3 - x3*x3 - y3*y3) - T;

        // Cramer's Rule

        double Mx = (C*E  - B*F) /2;
        double My = (A*F  - D*C) /2;
        double M  = A*E - D*B;

        x = Mx/M;
        y = My/M;

    }
}
