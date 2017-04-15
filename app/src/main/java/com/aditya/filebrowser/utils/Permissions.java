package com.aditya.filebrowser.utils;

/**
 * Created by Aditya on 4/15/2017.
 */


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aditya.filebrowser.Constants;

import java.util.ArrayList;

/**
 * Created by Aditya on 4/14/2017.
 */
public class Permissions extends AppCompatActivity {

    public static final int APP_PERMISSIONS_REQUEST = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED_OR_NEVER_ASKED = 2;
    public static final int GRANTED = 3;

    public void requestPermissions(String []permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Here, thisActivity is the current activity
            int ungrantedPermCount = 0;
            ArrayList<String> permissionsToBeAsked = new ArrayList<String>();
            for(int i=0;i< permissions.length;i++) {
                if(isPermissionIsGranted(permissions[i],this)!=GRANTED) {
                    ungrantedPermCount++;
                    permissionsToBeAsked.add(permissions[i]);
                }
            }
            if(ungrantedPermCount==0) {
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissionsToBeAsked.toArray(new String[permissionsToBeAsked.size()]),
                        APP_PERMISSIONS_REQUEST);
            }
        }
    }

    public static int isPermissionIsGranted(String permission, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
                    return BLOCKED_OR_NEVER_ASKED;
                }
                return DENIED;
            }
            return GRANTED;

        } else {
            return GRANTED;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=this.getIntent().getExtras();
        String[] permissions = b.getStringArray(Constants.APP_PREMISSION_KEY);
        for(int i=0;i<permissions.length;i++) {
            Log.e("AAA",permissions[i]);
        }
        requestPermissions(permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case APP_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {

                    boolean isAllPermissionsGranted = true;
                    for(int i=0;i<grantResults.length;i++) {
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED) {
                            isAllPermissionsGranted = false;
                            break;
                        }
                    }
                    if(isAllPermissionsGranted) {
                        setResult(Activity.RESULT_OK);
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    setResult(Activity.RESULT_CANCELED);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                finish();
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    String [] getRequestablePermissions(String []permissions) {

    }
}

