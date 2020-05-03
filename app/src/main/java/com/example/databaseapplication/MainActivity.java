package com.example.databaseapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    //custom function to disable animation
    private void setSystemAnimationScale(float animationScale) {
        try {
            Class windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
            Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);
            Class serviceManagerClazz = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);
            Class windowManagerClazz = Class.forName("android.view.IWindowManager");
            Method setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", float[].class);
            Method getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales");

            IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
            Object windowManagerObj = asInterface.invoke(null, windowManagerBinder);
            float[] currentScales = (float[]) getAnimationScales.invoke(windowManagerObj);
            for (int i = 0; i < currentScales.length; i++) {
                currentScales[i] = animationScale;
            }
            setAnimationScales.invoke(windowManagerObj, new Object[]{currentScales});
            Log.d("CustomTestRunner", "Changed permissions of animations");
        } catch (Exception e) {
            Log.e("CustomTestRunner", "Could not change animation scale to " + animationScale + " :'(");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context ctx = MainActivity.this.getApplicationContext();
        //emulator unlock
        try {
            KeyguardManager mKeyGuardManager = (KeyguardManager)      ctx.getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock mLock = mKeyGuardManager.newKeyguardLock(MainActivity.class.getSimpleName());
            mLock.disableKeyguard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //keeping screen awake
        try{
            PowerManager power = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
            power.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, MainActivity.class.getSimpleName()).acquire();
        }catch (Exception e){
            e.printStackTrace();
        }

        //disabling animations
        try{
            int permStatus = ctx.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE);
            if (permStatus == PackageManager.PERMISSION_GRANTED){
                setSystemAnimationScale(0.0f);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Button RunQuery = findViewById(R.id.btnRunQuery);
        Button RunUpdate = findViewById(R.id.btnRunUpdate);
        final TextView txtOutput=findViewById(R.id.txtOutput);
        final EditText edtInput = findViewById(R.id.edtInput);
        RunQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext(),"DatabaseApp");
                String sql= edtInput.getText().toString().trim();
                Cursor cursor = databaseHelper.doQuery(sql);
                String output="";
                if(cursor==null) output="Table does not exist!";
                else {
                    while (cursor.moveToNext()) {
                        if (output.equals("")) output = cursor.getString(0);
                        else output += "\n" + cursor.getString(0);
                    }
                }
                txtOutput.setText(output);
            }
        });

        RunUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext(),"DatabaseApp");
                String sql= edtInput.getText().toString().trim();
                databaseHelper.doUpdate(sql);
            }
        });
    }
}
