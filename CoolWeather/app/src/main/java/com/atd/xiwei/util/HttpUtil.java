package com.atd.xiwei.util;
import android.view.KeyEvent.*;
import okhttp3.*;

public class HttpUtil
{
	public static void sendokHttpRequest(String address,okhttp3.Callback callback)
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(address).build();
		client.newCall(request).enqueue(callback);
		//enqueue在内部开子线程传入回调接口
	}
}
