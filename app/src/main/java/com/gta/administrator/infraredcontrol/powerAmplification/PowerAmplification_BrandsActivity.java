package com.gta.administrator.infraredcontrol.powerAmplification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol.R;

import java.util.ArrayList;
import java.util.List;

public class PowerAmplification_BrandsActivity extends AppCompatActivity {

    private static final String[] powerAmplificationBrandsName = {
            "天龙功放"
    };
    private ListView my_listview;
    private List<String> powerAmplificationBrands = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_amplification__brands);


        my_listview = (ListView) findViewById(R.id.my_listview);
        my_listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getListData()));


        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(PowerAmplification_BrandsActivity.this, TL_PowerAmplificationControlActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private List<String> getListData() {
        for (int i = 0; i < powerAmplificationBrandsName.length; i++) {
            powerAmplificationBrands.add(powerAmplificationBrandsName[i]);
        }
        return powerAmplificationBrands;
    }
}
