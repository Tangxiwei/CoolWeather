package com.atd.xiwei.db;
import org.litepal.crud.DataSupport;

public class Province extends DataSupport
{
	private int id;
	private int provinceCode;
	private String provinceName;


	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setProvinceCode(int provinceCode)
	{
		this.provinceCode = provinceCode;
	}

	public int getProvinceCode()
	{
		return provinceCode;
	}

	public void setProvinceName(String provinceName)
	{
		this.provinceName = provinceName;
	}

	public String getProvinceName()
	{
		return provinceName;
	}}
