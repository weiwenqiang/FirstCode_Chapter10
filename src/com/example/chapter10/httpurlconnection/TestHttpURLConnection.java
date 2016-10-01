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
				//在这里进行UI操作，将结果显示到界面上
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
		//开启线程来发起网络请求
		new Thread(new Runnable(){

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					//new一个URL对象，传入目标网络地址
					URL url = new URL("http://www.baidu.com");
					//得到HttpURLConnection实例
					connection = (HttpURLConnection) url.openConnection();
					//设置请求方式，GET希望从服务器那里获取数据，而POST则表示希望提交数据给服务器
					connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes("username=admin&password=123456");
					
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					//下面会获取到的数据流进行读取
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine())!=null){
						response.append(line);
					}
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					//将服务器返回的结果存放到Message中
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
