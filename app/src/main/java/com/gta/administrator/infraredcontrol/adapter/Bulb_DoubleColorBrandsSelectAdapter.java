package com.gta.administrator.infraredcontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.bulb.Bulb_DoubleColorFragmentSub3;

import java.util.List;

/**
 * Created by yanwen on 16/10/16.
 */
public class Bulb_DoubleColorBrandsSelectAdapter extends RecyclerView.Adapter<Bulb_DoubleColorBrandsSelectAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> brandsList;
    private boolean isChecked = false;

    public Bulb_DoubleColorBrandsSelectAdapter(Context mContext, List<String> brandsList, boolean isChecked) {
        this.mContext = mContext;
        this.brandsList = brandsList;
        this.isChecked = isChecked;
    }

    public interface onCheckedChangedListener {
        void onCheckedChanged(int position, boolean isChecked);
    }

    private onCheckedChangedListener checkedChangedListener;

    public void setOnCheckedChangedListener(Bulb_DoubleColorBrandsSelectAdapter.onCheckedChangedListener onCheckedChangedListener) {
        this.checkedChangedListener = onCheckedChangedListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bulb_brands_recyclerview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.brands.setText(brandsList.get(position));
        holder.checked.setChecked(isChecked);
        holder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChangedListener.onCheckedChanged(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandsList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView brands;
        public CheckBox checked;


        public MyViewHolder(View itemView) {
            super(itemView);

            brands = (TextView) itemView.findViewById(R.id.brands_text);
            checked = (CheckBox) itemView.findViewById(R.id.checked);

        }
    }



}
