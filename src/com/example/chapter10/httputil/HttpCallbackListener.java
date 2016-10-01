package com.example.chapter10.httputil;

public interface HttpCallbackListener {
	void onFinish(String response);
	
	void onError(Exception e);
}
