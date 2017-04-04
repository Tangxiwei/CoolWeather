package com.atd.xiwei.util;
import android.text.*;
import com.google.gson.*;
import org.json.*;
import com.atd.xiwei.db.*;

public class Utility
{
	public  static boolean handleProvinceResponse(String response)
	{
		if(!TextUtils.isEmpty(response))
		{
	    try
		{
			JSONArray allProvinces = new JSONArray(response);
			for(int i = 0;i < allProvinces.length();i++)
			{
				JSONObject provinceObject = allProvinces.getJSONObject(i);
				Province province = new Province();
				province.setProvinceName(provinceObject.getString("name"));
				province.setProvinceCode(provinceObject.getInt("code"));
				
				
				
			}
			
		}
		catch ( JSONException e)
		{
			e.printStackTrace();
		}
		}
	}
}
