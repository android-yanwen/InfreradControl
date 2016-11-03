package com.gta.administrator.infraredcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceV1Signer;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.BceHttpClient;
import com.baidubce.http.BceHttpResponse;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class Main2ActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_activity_test);



        new Thread(new Runnable() {
            @Override
            public void run() {


                DefaultBceCredentials BceCredentials = new DefaultBceCredentials("e03dff1a5d7049c5ab40650b655885b9", "f299118b5e7d474f84badea389c8d17d");
                BceClientConfiguration config = new BceClientConfiguration();

                config.setCredentials(BceCredentials);

                URI uri = URI.create("http://iot.gz.baidubce.com/v1/endpoint");

                final InternalRequest request = new InternalRequest(HttpMethodName.POST, uri);
                request.addHeader("Content-Type", "application/json; charset=utf-8");
                request.addHeader("Host", "iot.gz.baidubce.com");


                String body = "{\"endpointName\":\"endpoint05\"}";
                request.setContent(RestartableInputStream.wrap(body.getBytes()));

                BceHttpClient client = new BceHttpClient(config, new BceV1Signer());

                HttpResponseHandler hanlder1 = new BceJsonResponseHandler() {
                    @Override
                    public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response) throws Exception {
                        InputStream content = httpResponse.getContent();
                        if (content != null) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                            StringBuffer buffer = new StringBuffer();
//                    while ((buffer.append(reader.readLine())) != null) {
                            buffer.append(reader.readLine());
                            System.out.println(buffer.toString());
//                        Log.d(tag, buffer.toString());
//                    }
                        }
                        return true;
                    }
                };
                HttpResponseHandler hanler2 = new BceErrorResponseHandler();
                client.execute(request, AbstractBceResponse.class, new HttpResponseHandler[]{hanlder1, hanler2});
            }
        }).start();
    }

}
