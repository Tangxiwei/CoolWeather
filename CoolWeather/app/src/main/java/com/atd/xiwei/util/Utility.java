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
				province.save();
			}
			return true;
		}
		catch ( JSONException e)
		{
			e.printStackTrace();
		}
		}
		return false;
	}
	public static boolean handleCityResponse(String Response,int pronvinceId)
	{
		if(!TextUtils.isEmpty(Response))
		{
			try{
			JSONArray allCitys = new JSONArray(Response);
			for(int i = 0;i<allCitys.length();i++)
			{
				JSONObject cityObject = allCitys.getJSONObject(i);
				City city = new City();
				city.setCityCode(cityObject.getInt("id"));
				city.setCityName(cityObject.getString("name"));
				city.setProvinceId(pronvinceId);
				city.save();
			}
			return true;
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	public static boolean handleCountryResponse(String response,int cityId)
	{
		if(!TextUtils.isEmpty(response))
		{
			try{
				JSONArray allCounties = new JSONArray(response);
				for(int i = 0;i<allCounties.length();i++)
				{
					JSONObject countyObject = allCounties.getJSONObject(i);
				    County county = new County();
					county.setWeatherId(countyObject.getString("weather_id"));
					county.setCountyName(countyObject.getString("name"));
					county.setCityId(cityId);
					county.save();
				}
				return true;
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
}
