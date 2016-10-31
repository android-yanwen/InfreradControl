package com.gta.administrator.infraredcontrol.bulb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.adapter.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_DoubleColorFragmentSub2 extends Fragment {

    private BulbActivity mActivity;
    private View view;
/*
    private RecyclerView my_recyclerview;
    private String[] colorBulbs = {
            "开",
            "关",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
    };
*/



    public Bulb_DoubleColorFragmentSub2() {
        // Required empty public constructor
        mActivity = (BulbActivity) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_bulb__double_color_fragment_sub2, container, false);
        /*my_recyclerview = (RecyclerView) view.findViewById(R.id.my_recycle_view);
        my_recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 2));
        my_recyclerview.setAdapter(new MyAdapter());
        my_recyclerview.addItemDecoration(new DividerGridItemDecoration(getActivity()));*/


        return view;
    }


/*
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.bulb_recyclerview_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.button.setText(colorBulbs[position]);
        }

        @Override
        public int getItemCount() {
            return colorBulbs.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private Button button;

            public ViewHolder(View itemView) {
                super(itemView);

                button = (Button) itemView.findViewById(R.id.button);

            }
        }
    }*/



}
