package com.zkfxd.workoutapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Workout extends AppCompatActivity {
    String[] wName;
    String[] wBody;
    String[] wRep;
    String[] wWeight;
    int[] wId;

    static DBadapter workoutDB;

    String tableName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        openDB();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            tableName = bundle.getString("tableName");
        }
        else{
            tableName = DBadapter.UPPERTABLE;
        }

        fillArrays(tableName);

        ListView workoutTable = (ListView) findViewById(R.id.workoutTable);
        workoutTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateWorkout(wId[i]);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    public void updateValues(String tableName){

        //Initial Lists
        List<Integer> idW = new ArrayList<>();
        List<String> nameW = new ArrayList<>();
        List<String> bodyW = new ArrayList<>();
        List<String> repW = new ArrayList<>();
        List<String> weightW = new ArrayList<>();

        Cursor c = workoutDB.getAllRows(tableName);
        if (c.moveToFirst()){

            do{
                int id = c.getInt(DBadapter.COL_ROWID);
                String name = c.getString(DBadapter.COL_NAME);
                String body = c.getString(DBadapter.COL_BODY);
                String rep = c.getString(DBadapter.COL_REPS);
                String weight = c.getString(DBadapter.COL_WEIGHT);

                idW.add(id);
                nameW.add(name);
                bodyW.add(body);
                repW.add(rep);
                weightW.add(weight);

            }while (c.moveToNext());
        }
        c.close();

        wId = ListToArrayInt(idW);
        wName = nameW.toArray(new String[nameW.size()]);
        wBody = bodyW.toArray(new String[bodyW.size()]);
        wRep = repW.toArray(new String[repW.size()]);
        wWeight = weightW.toArray(new String[weightW.size()]);



    }

    public void fillArrays(String tableName){
        updateValues(tableName);
        TableCustomAdapter wAdapter = new TableCustomAdapter(this, wName, wBody, wRep, wWeight);
        ListView workoutTable = (ListView) findViewById(R.id.workoutTable);
        workoutTable.setAdapter(wAdapter);
    }

    public void returnHome(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void addWorkout(View view){
        AlertDialog.Builder wBuilder = new AlertDialog.Builder(Workout.this);
        View wView = getLayoutInflater().inflate(R.layout.add_workout, null);

        final EditText workoutName = (EditText) wView.findViewById(R.id.workoutName);
        final EditText repEnter = (EditText) wView.findViewById(R.id.repEnter);
        final EditText weightEnter = (EditText) wView.findViewById(R.id.weightEnter);
        final Spinner bodyPart = (Spinner) wView.findViewById(R.id.bodyPart);
        Button repUp = (Button) wView.findViewById(R.id.repUp);
        Button repDown = (Button) wView.findViewById(R.id.repDown);
        Button weightUp = (Button) wView.findViewById(R.id.weightUp);
        Button weightDown = (Button) wView.findViewById(R.id.weightDown);

        final int bodyGroup = getArray(tableName);
        ArrayAdapter<String> bodyPartAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,getResources().getStringArray(bodyGroup));

        bodyPartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyPart.setAdapter(bodyPartAdapter);
        bodyPart.setSelection(0);

        repUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incVal2(repEnter);
            }
        });
        repDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decVal2(repEnter);
            }
        });

        weightUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incVal(weightEnter);
            }
        });
        weightDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decVal(weightEnter);
            }
        });

        wBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        wBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = workoutName.getText().toString();
                String body = bodyPart.getSelectedItem().toString();
                String reps = repEnter.getText().toString();
                String weight = weightEnter.getText().toString();
                workoutDB.insertRow(name, reps, weight, body, tableName);
                fillArrays(tableName);
                dialogInterface.dismiss();

            }
        });

        wBuilder.setView(wView);
        AlertDialog dialog = wBuilder.create();
        dialog.show();
    }

    public void updateWorkout(long _id){
        final long id = _id;
        AlertDialog.Builder wBuilder = new AlertDialog.Builder(Workout.this);
        View wView = getLayoutInflater().inflate(R.layout.add_workout, null);

        final EditText workoutName = (EditText) wView.findViewById(R.id.workoutName);
        final EditText repEnter = (EditText) wView.findViewById(R.id.repEnter);
        final EditText weightEnter = (EditText) wView.findViewById(R.id.weightEnter);
        final Spinner bodyPart = (Spinner) wView.findViewById(R.id.bodyPart);
        Button repUp = (Button) wView.findViewById(R.id.repUp);
        Button repDown = (Button) wView.findViewById(R.id.repDown);
        Button weightUp = (Button) wView.findViewById(R.id.weightUp);
        Button weightDown = (Button) wView.findViewById(R.id.weightDown);

        int bodyGroup = getArray(tableName);
        ArrayAdapter<String> bodyPartAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,getResources().getStringArray(bodyGroup));

        bodyPartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyPart.setAdapter(bodyPartAdapter);

        Cursor c = workoutDB.getRow(id, tableName);
        if (c.moveToFirst()){
            String nameI = c.getString(DBadapter.COL_NAME);
            String bodyI = c.getString(DBadapter.COL_BODY);
            String repsI = c.getString(DBadapter.COL_REPS);
            String weightI = c.getString(DBadapter.COL_WEIGHT);

            workoutName.setText(nameI);
            repEnter.setText(repsI);
            weightEnter.setText(weightI);
            bodyPart.setSelection(Arrays.asList(getResources().getStringArray(bodyGroup)).indexOf(bodyI));

        }
        c.close();



        repUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incVal2(repEnter);
            }
        });
        repDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decVal2(repEnter);
            }
        });

        weightUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incVal(weightEnter);
            }
        });
        weightDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decVal(weightEnter);
            }
        });

        wBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        wBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                workoutDB.deleteRow(id, tableName);
                fillArrays(tableName);
                dialogInterface.dismiss();
            }
        });

        wBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialogInterface, int i) {
                String name = workoutName.getText().toString();
                String body = bodyPart.getSelectedItem().toString();
                String reps = repEnter.getText().toString();
                String weight = weightEnter.getText().toString();
                workoutDB.updateRow(id, name,reps, weight,body, tableName);
                fillArrays(tableName);
                dialogInterface.dismiss();
            }
        });

        wBuilder.setView(wView);
        AlertDialog dialog = wBuilder.create();
        dialog.show();
    }

    public void incVal(EditText text){

        double num = Double.valueOf(text.getText().toString());
        DecimalFormat format = new DecimalFormat("0.#");
        num = num + 2.5;
        String output = String.valueOf(format.format(num));

        text.setText(output);

    }

    public void decVal(EditText text){

        double num = Double.valueOf(text.getText().toString());
        DecimalFormat format = new DecimalFormat("0.#");
        num = num - 2.5;
        String output = String.valueOf(format.format(num));

        text.setText(output);

    }

    public void incVal2(EditText text){

        double num = Double.valueOf(text.getText().toString());
        DecimalFormat format = new DecimalFormat("0.#");
        num = num + 1;
        String output = String.valueOf(format.format(num));

        text.setText(output);

    }

    public void decVal2(EditText text){

        double num = Double.valueOf(text.getText().toString());
        DecimalFormat format = new DecimalFormat("0.#");
        num = num - 1;
        String output = String.valueOf(format.format(num));

        text.setText(output);

    }


    public int getArray(String table){
        int bodyGroup;
        Log.i("test", table);

        if (table.equals("Arms")){
            bodyGroup = R.array.armsArray;
        }else if(table.equals("Lower")){
            bodyGroup = R.array.lowerArray;
        }else {
            bodyGroup = R.array.upperArray;
        }

        return bodyGroup;
    }

    public int[] ListToArrayInt(List<Integer> original){

        int size = original.size();
        Integer [] ogList = original.toArray(new Integer[size]);
        int[] changed = new int[size];
        for (int n=0; n < size ; n++){
            changed[n] = ogList[n];
        }

        return changed;

    }

    private void openDB(){

        workoutDB = new DBadapter(this);
        workoutDB.open();

    }

    private void closeDB(){
        workoutDB.close();
    }
}
