package com.example.helloworldapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;

import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

public class FirstFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 35;

    TextView textView ;

    TelephonyManager telephonyManager;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        textView = container.findViewById(R.id.textview_first);
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    private void getSignalStrength(View view) {
        telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
            CellInfoLte cellinfogsm = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
            CellSignalStrengthLte cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
            if(cellSignalStrengthGsm != null){
                TextView testview =(TextView)view.findViewById((R.id.textview_first));
                if(testview != null){
                    String result = null;
                    StringBuilder stringBuilder= new StringBuilder();
                    LocationServices.getFusedLocationProviderClient(getActivity()).getLastLocation().addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if(task.isSuccessful() && task.getResult() != null){
                                Location location=task.getResult();
                                stringBuilder.append(cellSignalStrengthGsm.toString().concat("Latitude:").concat(String.valueOf(location.getLatitude())).concat("Longitude:").concat(String.valueOf(location.getLongitude())));
                                Log.i("TESTING","saving location");
                                testview.setText(stringBuilder.toString());
                            }else {
                                Log.i("TESTING","location got as null");
                            }
                        }
                    });
                    testview.setText(stringBuilder.toString());
                }else {
                    Log.i("Testing","Null2");
                }
                Log.i("Signal Strength",cellSignalStrengthGsm.toString());
            }else {

                Log.i("Testing","Null");

            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            Log.i("Testing","I have the permissions");
        }
    }

//    private void showSnackbar(final String text) {
//        View container = findViewById(R.id.);
//        if (container != null) {
//            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
//        }
//    }


//    private void showSnackbar(final int mainTextStringId, final int actionStringId,
//                              View.OnClickListener listener) {
//        Snackbar.make(findViewById(android.R.id.content),
//                getString(mainTextStringId),
//                Snackbar.LENGTH_INDEFINITE)
//                .setAction(getString(actionStringId), listener).show();
//    }


    private void requestPermissions() {
        System.out.println("control is here");
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_PHONE_STATE);

        if(shouldProvideRationale){
            shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("TESTNG", "Displaying permission rationale to provide additional context.");

//            showSnackbar(R.string.permission_rationale, android.R.string.ok,
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            startLocationPermissionRequest();
//                        }
//                    });

        } else {
            Log.i("TESTING", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }


    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_PHONE_STATE);
        if(permissionState == PackageManager.PERMISSION_GRANTED){
            permissionState = ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            return permissionState == PackageManager.PERMISSION_GRANTED;
        }else {
            return false;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                System.out.println("READ_SMS PERMISSION"+ActivityCompat.checkSelfPermission(getContext(), READ_SMS));
                System.out.println("READ_PHONE_NUMBERS PERMISSION"+ActivityCompat.checkSelfPermission(getContext(), READ_PHONE_NUMBERS));
                System.out.println("READ_PHONE_STATE PERMISSION"+ActivityCompat.checkSelfPermission(getContext(), READ_PHONE_STATE));
                if (ActivityCompat.checkSelfPermission(getContext(), READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                        //ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    System.out.println("already got permissions");
                    /*CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                    CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
                    textView.setText(cellSignalStrengthGsm.toString());*/
                }
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                System.out.println("this is getting triggered");
                //requestPermissions();
                getSignalStrength(view);
                /*                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
            }
        });
    }
}