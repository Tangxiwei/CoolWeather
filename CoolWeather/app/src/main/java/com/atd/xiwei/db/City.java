package com.atd.xiwei.db;
import org.litepal.crud.*;

public class City extends DataSupport
{
	private int id;
	private int cityCode;
	private String cityName;
	private int provinceId;
	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setCityCode(int cityCode)
	{
		this.cityCode = cityCode;
	}

	public int getCityCode()
	{
		return cityCode;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setProvinceId(int provinceId)
	{
		this.provinceId = provinceId;
	}

	public int getProvinceId()
	{
		return provinceId;
	}}
