package com.ggcoke.weatherdemo.util;

public interface MyAsynTaskCallback {
	void success(String data, int flag);
	void failed(String error, int flag);
}
