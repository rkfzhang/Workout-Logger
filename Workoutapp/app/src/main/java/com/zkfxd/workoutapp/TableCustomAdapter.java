package com.zkfxd.workoutapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;

public class TableCustomAdapter extends ArrayAdapter<String> {

    String[] name;
    String[] body;
    String[] reps;
    String[] weight;
    Context gContext;

    public TableCustomAdapter(Context context, String[] nameW, String[] bodyW, String[] repsW, String[] weightW){

        super(context, R.layout.table_list);
        this.name = nameW;
        this.body = bodyW;
        this.reps = repsW;
        this.weight = weightW;
        this.gContext = context;



    }

    @Override
    public int getCount() {
        return name.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder gViewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater gInflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = gInflater.inflate(R.layout.table_list, parent, false);
            gViewHolder.wName = (TextView) convertView.findViewById(R.id.wName);
            gViewHolder.wRep = (TextView) convertView.findViewById(R.id.wRep);
            gViewHolder.wWeight = (TextView) convertView.findViewById(R.id.wWeight);
            convertView.setTag(gViewHolder);
        } else {
            gViewHolder = (ViewHolder) convertView.getTag();
        }

        gViewHolder.wName.setText(name[position]);
        gViewHolder.wRep.setText(reps[position]);
        gViewHolder.wWeight.setText(weight[position]);

        int index = getBodyPic(body[position]);
        gViewHolder.wName.setBackgroundResource(index);



        return convertView;

    }

    public int getBodyPic(String body) {

        switch (body) {

            case "Chest":
                return R.drawable.chest;

            case "Back":
                return R.drawable.back;

            case "Shoulders":
                return R.drawable.shoulders;

            case "Lats":
                return R.drawable.lats;

            case "Traps":
                return R.drawable.traps;

            case "Tricep":
                return R.drawable.triceps;

            case "Bicep":
                return R.drawable.bicep;

            case "Forearms":
                return R.drawable.forearm;

            case "Quads":
                return R.drawable.quads;

            case "Calves":
                return R.drawable.calves;

            case "Glutes":
                return R.drawable.glutes;

            case "Abs":
                return R.drawable.abs;

            case "Lower Back":
                return R.drawable.lowerback;

            default:
                return R.drawable.main;


        }

    }

    static class ViewHolder {
        TextView wName;
        TextView wRep;
        TextView wWeight;
    }
}