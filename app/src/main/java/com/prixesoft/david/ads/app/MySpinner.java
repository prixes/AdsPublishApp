package com.prixesoft.david.ads.app;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 10-Mar-17.
 */


//Spinner for subCategory reaction when Category is selected
public class MySpinner implements AdapterView.OnItemSelectedListener {
;
    Spinner s1;
    Spinner s2;
    MainActivity activity;

    MySpinner(MainActivity activity, Spinner s1 , Spinner s2){
        this.s1=s1;
        this.s2=s2;
        this.activity=activity;
    }



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


        // TODO Auto-generated method stub
        String sp1= String.valueOf(s1.getSelectedItem());
        List<String> list = new ArrayList<String>();

        if(sp1.contentEquals("For Sale")) {
            list.add("Fashion & Beauty");
            list.add("Home and Garden");
            list.add("Handmade");
            list.add("Furniture");
        } else if(sp1.contentEquals("Services")){
                list.add("Catering");
                list.add("Babysitting");
                list.add("Purchase");
        } else if(sp1.contentEquals("Vehicles")){
                list.add("Cars");
                list.add("Motorcycles");
                list.add("Bicycles");
                list.add("Boats");
        } else if(sp1.contentEquals("Property")){
                list.add("Flat sharing");
                list.add("Houses for sale");
                list.add("Rooms to rent");
        }







        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        //s2.setAdapter(dataAdapter);



        s2.setPrompt("Subcategory!");
        s2.setAdapter(
                new HintSpinner(
                        dataAdapter,
                        R.layout.hint_spinner_sub,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        activity));

    }







    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
