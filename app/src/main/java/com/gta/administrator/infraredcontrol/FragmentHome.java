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
import com.baidubce.services.iothub.model.BaseResponse;
import com.baidubce.services.iothub.model.ListResponse;
import com.baidubce.services.iothub.model.QueryEndpointResponse;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Endpoint;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Principal;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Thing;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.debug.DebugMsg;
import com.gta.administrator.infraredcontrol.socket.SocketUlitity;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
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
    private TextView info_text;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();

        return view;
    }

    Endpoint endpoint;
    void initView() {
        info_text = (TextView) view.findViewById(R.id.info_text);
        get_endpoint_list = (Button) view.findViewById(R.id.get_endpoint_list);
        get_endpoint_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setGet_endpoint_list();
                endpoint = new Endpoint();
                endpoint.requestEndpoint(Endpoint.Method.LIST_ENDPOINT, null, new EndpointCallback());
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
//                        setGet_endpoint(editText.getText().toString());
                        new Endpoint().requestEndpoint(Endpoint.Method.QUERY_ENDPOINT, editText
                                .getText().toString(), new EndpointCallback());
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
//                        setAdd_endpoint(editText.getText().toString());
                        new Endpoint().requestEndpoint(Endpoint.Method.CREATE_ENDPOINT, editText
                                .getText().toString(), new EndpointCallback());
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
//                        setDelete_endpoint(editText.getText().toString());
                        new Endpoint().requestEndpoint(Endpoint.Method.DELETE_ENDPOINT, editText
                                .getText().toString(), new EndpointCallback());
                    }
                });
                builder.show();
            }
        });


    }






    private class EndpointCallback implements Endpoint.RequestEndpointCallbakcListener {

        @Override
        public void createEndpointCallback(QueryEndpointResponse response) {

        }

        @Override
        public void listEndpointCallback(ListResponse response) {
            Log.d(TAG, "listEndpointCallback: " + response.getTotalCount());
            List<HashMap<String, String>> list = response.getResult();
            clearText();
            for (int i = 0; i < response.getTotalCount(); ++i) {
                final String name = list.get(i).get("endpointName");
                displayText(name);
            }

        }

        @Override
        public void queryEndpointCallback(QueryEndpointResponse response) {
            clearText();
            displayText(response.getEndpointName());
            displayText(response.getWebsocketHostname());

        }

        @Override
        public void deleteEndpointCallback(BaseResponse request) {

        }
    }


    private void displayText(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_text.append(text + ";");
            }
        });
    }

    private void clearText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info_text.setText("");
            }
        });
    }

}
