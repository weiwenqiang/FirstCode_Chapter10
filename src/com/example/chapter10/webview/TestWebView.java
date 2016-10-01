package com.example.chapter10.webview;

import com.example.chapter10.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TestWebView extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.b1_webview);
		
		webView = (WebView) findViewById(R.id.b1_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//���ݴ���Ĳ�����ȥ�����µ���ҳ
				view.loadUrl(url);
				//��ʾ��ǰWebView���Դ��������ҳ�����󣬲��ý���ϵͳ�����
				return true;
			}
			
		});
		webView.loadUrl("http://www.baidu.com");
	}
}
