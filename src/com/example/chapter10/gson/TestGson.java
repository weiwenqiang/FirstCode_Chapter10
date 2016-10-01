package com.example.chapter10.gson;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.chapter10.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TestGson extends Activity implements OnClickListener {
	public static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;
	/*private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				// ���������UI�������������ʾ��������
				responseText.setText(response);
				break;
			default:
				break;
			}
		}
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b2_httpurlconnection);
		sendRequest = (Button) findViewById(R.id.b2_send_request);
		sendRequest.setText("GSON����Json");
		responseText = (TextView) findViewById(R.id.b2_response);
		sendRequest.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.b2_send_request) {
			sendRequestWithHttpClient();
		}
	}

	private void sendRequestWithHttpClient() {
		// �����߳���������������
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/test/get_data.json");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						// �������Ӧ���ɹ���
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						
						/*Message message = new Message();
						message.what = SHOW_RESPONSE;
						// �����������صĽ����ŵ�Message��
						message.obj = response.toString();
						handler.sendMessage(message);*/
						
						// ����
						parseJSONWithGSON(response);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}
	private void parseJSONWithGSON(String jsonData){
		Gson gson = new Gson();
		List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
		for (App app : appList) {
			Log.d("TestGson", "id is "+app.getId());
			Log.d("TestGson", "name is "+app.getName());
			Log.d("TestGson", "version is "+app.getVersion());
		}
	}
}
