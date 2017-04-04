package com.atd.xiwei.db;
import org.litepal.crud.*;

public class County extends DataSupport
{
	private int id;
	private String countryName;
	private String weatherId;
	private int cityId;


	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setWeatherId(String weatherId)
	{
		this.weatherId = weatherId;
	}

	public String getWeatherId()
	{
		return weatherId;
	}

	public void setCityId(int cityId)
	{
		this.cityId = cityId;
	}

	public int getCityId()
	{
		return cityId;
	}}
