package com.gta.administrator.infraredcontrol;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidubce.http.HttpMethodName;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Endpoint;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Thing;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.debug.DebugMsg;
import com.gta.administrator.infraredcontrol.socket.SocketUlitity;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanwen on 16/10/4.
 */
public class FragmentHome extends Fragment {

    private static final String TAG = "FragmentHome";

    private Button get_endpoint_list;
    private Button get_endpoint;
    private Button add_endpoint;
    private Button delete_endpoint;
    private Button get_thing_list;
    private Button get_thing;
    private Button add_thing;
    private Button delete_thing;
    private TextView info_text;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();

        return view;
    }


    void initView() {
        info_text = (TextView) view.findViewById(R.id.info_text);
        get_endpoint_list = (Button) view.findViewById(R.id.get_endpoint_list);
        get_endpoint_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGet_endpoint_list();
            }
        });

        get_endpoint = (Button) view.findViewById(R.id.get_endpoint);
        get_endpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder
                        (getActivity());
                builder.setMessage("输入endpoint名称");
                final EditText editText = new EditText(getActivity());
                builder.setView(editText);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setGet_endpoint(editText.getText().toString());
                    }
                });
                builder.show();
            }
        });

        add_endpoint = (Button) view.findViewById(R.id.add_endpoint);
        add_endpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder
                        (getActivity());
                builder.setMessage("创建endpoint名称");
                final EditText editText = new EditText(getActivity());
                builder.setView(editText);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setAdd_endpoint(editText.getText().toString());
                    }
                });
                builder.show();
            }
        });

        delete_endpoint = (Button) view.findViewById(R.id.delete_endpoint);
        delete_endpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder
                        (getActivity());
                builder.setMessage("删除的endpoint名称");
                final EditText editText = new EditText(getActivity());
                builder.setView(editText);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDelete_endpoint(editText.getText().toString());
                    }
                });
                builder.show();
            }
        });


    }





    void setGet_endpoint_list() {
        Endpoint module = new Endpoint();
        module.requestEndpoint(HttpMethodName.GET, "", new Endpoint.RequestEndpointListener() {
            @Override
            public void onResponse(final StringBuffer result) {
                Log.d(TAG, "onResponse: " + result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        info_text.setText(result);
                    }
                });
            }
        });
    }

    void setGet_endpoint(String endpoint) {
        Endpoint module = new Endpoint();
        module.requestEndpoint(HttpMethodName.GET, endpoint, new Endpoint.RequestEndpointListener() {
            @Override
            public void onResponse(final StringBuffer result) {
                Log.d(TAG, "onResponse: " + result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        info_text.setText(result);
                    }
                });
            }
        });
    }

    void setAdd_endpoint(String endpoint) {
        Endpoint module = new Endpoint();
        module.requestEndpoint(HttpMethodName.POST, endpoint, new Endpoint
                .RequestEndpointListener() {
            @Override
            public void onResponse(final StringBuffer result) {
                Log.d(TAG, "onResponse: " + result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        info_text.setText(result);
                    }
                });
            }
        });
    }

    void setDelete_endpoint(String endpoint) {
        Endpoint module = new Endpoint();
        module.requestEndpoint(HttpMethodName.DELETE, endpoint, new Endpoint
                .RequestEndpointListener() {
            @Override
            public void onResponse(final StringBuffer result) {
                Log.d(TAG, "onResponse: " + result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        info_text.setText(result);
                    }
                });
            }
        });
    }




}
