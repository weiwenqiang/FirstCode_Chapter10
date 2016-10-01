package com.example.chapter10;

import com.example.chapter10.gson.TestGson;
import com.example.chapter10.httpclient.TestHttpClient;
import com.example.chapter10.httpurlconnection.TestHttpURLConnection;
import com.example.chapter10.jsonobject.TestJsonObject;
import com.example.chapter10.pull.TestPullXml;
import com.example.chapter10.sax.TestSaxXml;
import com.example.chapter10.webview.TestWebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		findViewById(R.id.c10_b1).setOnClickListener(this);
		findViewById(R.id.c10_b2).setOnClickListener(this);
		findViewById(R.id.c10_b3).setOnClickListener(this);
		findViewById(R.id.c10_b4).setOnClickListener(this);
		findViewById(R.id.c10_b5).setOnClickListener(this);
		findViewById(R.id.c10_b6).setOnClickListener(this);
		findViewById(R.id.c10_b7).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.c10_b1:
			startActivity(new Intent(MainActivity.this, TestWebView.class));
			break;
		case R.id.c10_b2:
			startActivity(new Intent(MainActivity.this, TestHttpURLConnection.class));
			break;
		case R.id.c10_b3:
			startActivity(new Intent(MainActivity.this, TestHttpClient.class));
			break;
		case R.id.c10_b4:
			startActivity(new Intent(MainActivity.this, TestPullXml.class));
			break;
		case R.id.c10_b5:
			startActivity(new Intent(MainActivity.this, TestSaxXml.class));
			break;
		case R.id.c10_b6:
			startActivity(new Intent(MainActivity.this, TestJsonObject.class));
			break;
		case R.id.c10_b7:
			startActivity(new Intent(MainActivity.this, TestGson.class));
			break;
		default:
			break;
		}
	}
}
