package com.gta.administrator.infraredcontrol.bulb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_ColorFragment extends Fragment {

    private static final String TAG = "Bulb_ColorFragment";
    private TextView txtColor;
    private ColorPickView myView;
    private View view;

    public Bulb_ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bulb__color, container, false);
        // Inflate the layout for this fragment
        initView();
        return view;
    }

    private void initView() {

        myView = (ColorPickView) view.findViewById(R.id.color_picker_view);
        txtColor = (TextView) view.findViewById(R.id.txt_color);
        myView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {

            @Override
            public void onColorChange(int color) {

//                Log.d(TAG, "color=" + color + ":" + Integer.toHexString(color));
//                Toast.makeText(getActivity(), "color:" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
                txtColor.setTextColor(color);
                txtColor.setText(Integer.toHexString(color));
            }

        });
    }

}
