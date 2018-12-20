package com.gta.administrator.infraredcontrol_apk.tv;

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

public class TvBrandsListActivity extends AppCompatActivity {

    private ListView tv_brands_list;

    private static final String[] brands = new String[] {
            "创维电视",
    };

    private List<String> tvBrands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_brands_list);

        tv_brands_list = (ListView) findViewById(R.id.tv_brands_list);

        tv_brands_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getListData()));

        tv_brands_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(TvBrandsListActivity.this, CW_TvControlActivity.class));
                        break;
                }
            }
        });

    }

    private List<String> getListData() {

        for (int i = 0; i < brands.length; i++) {
            tvBrands.add(brands[i]);
        }

        return tvBrands;


    }
}
