package com.gta.administrator.infraredcontrol.projectors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.fan.MD_FanControlActivity;

import java.util.ArrayList;
import java.util.List;

public class ProjectorsBrandsSelectActivity extends AppCompatActivity {

    private static final String[] fanBrandsName = {
            "投影仪品牌1"
    };
    private ListView my_listview;
    private List<String> brands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_projectors_brands_select);

        my_listview = (ListView) findViewById(R.id.my_listview);

        my_listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getListData()));

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(ProjectorsBrandsSelectActivity.this, ProjectorsBrands1Activity.class));
                        break;
                }
            }
        });
    }

    private List<String> getListData() {
        for (int i = 0; i < fanBrandsName.length; i++) {
            brands.add(fanBrandsName[i]);
        }
        return brands;
    }

}
