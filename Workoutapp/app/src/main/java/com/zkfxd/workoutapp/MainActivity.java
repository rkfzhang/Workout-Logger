package com.zkfxd.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    DBadapter workoutDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("test", DBadapter.ARMSTABLE );
        Log.i("test", DBadapter.UPPERTABLE );
        Log.i("test", DBadapter.LOWERTABLE );
    }

    public void workoutArms(View view){

        Intent i = new Intent(this, Workout.class);
        i.putExtra("tableName", DBadapter.ARMSTABLE);
        startActivity(i);
    }

    public void workoutUpper(View view){

        Intent i = new Intent(this, Workout.class);
        i.putExtra("tableName", DBadapter.UPPERTABLE);
        startActivity(i);
    }

    public void workoutLower(View view){

        Intent i = new Intent(this, Workout.class);
        i.putExtra("tableName", DBadapter.LOWERTABLE);
        startActivity(i);
    }
}


