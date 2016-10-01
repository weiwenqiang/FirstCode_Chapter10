package com.example.chapter10.httpurlconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.chapter10.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TestHttpURLConnection extends Activity implements OnClickListener {
	public static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				//���������UI�������������ʾ��������
				responseText.setText(response);
				break;

			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b2_httpurlconnection);
		sendRequest = (Button) findViewById(R.id.b2_send_request);
		responseText = (TextView) findViewById(R.id.b2_response);
		sendRequest.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId() ==R.id.b2_send_request){
			sendRequestWithHttpURLConnection();
		}
	}
	
	private void sendRequestWithHttpURLConnection(){
		//�����߳���������������
		new Thread(new Runnable(){

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					//newһ��URL���󣬴���Ŀ�������ַ
					URL url = new URL("http://www.baidu.com");
					//�õ�HttpURLConnectionʵ��
					connection = (HttpURLConnection) url.openConnection();
					//��������ʽ��GETϣ���ӷ����������ȡ���ݣ���POST���ʾϣ���ύ���ݸ�������
					connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes("username=admin&password=123456");
					
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					//������ȡ�������������ж�ȡ
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine())!=null){
						response.append(line);
					}
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					//�����������صĽ����ŵ�Message��
					message.obj = response.toString();
					handler.sendMessage(message);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					if(connection != null){
						connection.disconnect();
					}
				}
			}
			
		}).start();
	}
	
}
