package com.gta.administrator.infraredcontrol_apk.dvd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol_apk.R;

import java.util.ArrayList;
import java.util.List;

public class DvdBrandsListActivity extends AppCompatActivity {

    private static final String[] dvdBrands = {
            "步步高DVD播放机"
    };
    private ListView my_listview;
    private List<String> brands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd_brands_list);


        my_listview = (ListView) findViewById(R.id.my_listview);

        my_listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getListData()));


        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(DvdBrandsListActivity.this, BBG_DvdControlActivity.class));


                        break;
                    default:break;
                }
            }
        });
    }

    private List<String> getListData() {
        for (int i = 0; i < dvdBrands.length; ++i) {
            brands.add(dvdBrands[i]);
        }
        return brands;
    }
}
