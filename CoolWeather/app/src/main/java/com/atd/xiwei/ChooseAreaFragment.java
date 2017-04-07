package com.atd.xiwei;
import android.support.v4.app.Fragment;
import android.app.*;
import android.widget.TextView;
import android.widget.*;
import java.util.*;
import com.atd.xiwei.db.*;
import android.os.*;
import android.view.*;
import com.atd.xiwei.util.*;
import okhttp3.*;
import java.io.*;
import org.litepal.crud.*;

public class ChooseAreaFragment extends Fragment
{
	public static final int LEVEL_PROVINCE =0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY =2;
	
	private ProgressDialog progressDialog;
	private TextView titleText;
	private Button backButton;
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<>();
	
	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countyList;
	
	private Province selectedProvince;
	private City selectedCity;
	//当前选中级别
	private int currentLevel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.choose_area,container,false);
		titleText = (TextView)view.findViewById(R.id.tittle_text);
		backButton = (Button)view.findViewById(R.id.back_button);
		listview = (ListView)view.findViewById(R.id.list_view);
		adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
		listview.setAdapter(adapter);
		// TODO: Implement this method
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onActivityCreated(savedInstanceState);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent,View view,int position,long id)
		{
			if(currentLevel == LEVEL_PROVINCE)
			{
				
			}
		}
		});
	}
	//查询省数据，从数据库，没有则从服务器查询
	public void queryProvinces(){
		titleText.setText("中国");
		backButton.setVisibility(View.GONE);
		provinceList = DataSupport.findAll(Province.class);
		if (provinceList.size() > 0)
		{
			dataList.clear();
			for (Province province : provinceList)
			{
				dataList.add(province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listview.setSelection(0);//
			currentLevel = LEVEL_PROVINCE;
		}else{
			String address = "http://guolin.tech/api/china";
			queryFromServer(address,"province");
		}
	}
	//查询选中的省内的市
	private void queryCities(){
		titleText.setText(selectedProvince.getProvinceName());
		backButton.setVisibility(View.VISIBLE);
		cityList = DataSupport.where("province = ?",String.valueOf(selectedProvince.getId())).find(City.class);
		//查询选中的省的市，where条件后用find
		if (cityList.size() >0)
		{
		   dataList.clear();
		   for(City city: cityList) 
		   {
			   dataList.add(city.getCityName());
		   }
		   adapter.notifyDataSetChanged();
		   listview.setSelection(0);
		   currentLevel = LEVEL_CITY;
		}else{
			int provinceCode = selectedProvince.getProvinceCode();
			String address =  "http://guolin.tech/api/china/" + provinceCode;
			queryFromServer(address,"city");
		}
	}
	private void queryCounties(){
		titleText.setText(selectedCity.getCityName());
		backButton.setVisibility(View.VISIBLE);
		countyList = DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
		if (countyList.size() > 0)
		{
			dataList.clear();
			for (County county: countyList)
			{
				dataList.add(county.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listview.setSelection(0);
			currentLevel = LEVEL_COUNTY;
		}else
		{
			int provinceCode = selectedProvince.getProvinceCode();
			int cityCode = selectedCity.getCityCode();
			String address = "http://guolin.tech/api/china/" + provinceCode + cityCode;
			queryFromServer(address,"county");
		}
	}
	/**
	 *根据传入的地址和类型从服务器查询省市县
	 */
	 private void queryFromServer(String address , final String type)
	 {
		 showProgressDialog();
		 HttpUtil.sendokHttpRequest(address, new okhttp3.Callback(){

				 @Override
				 public void onFailure(Call call, IOException e)
				 {
					 getActivity().runOnUiThread(new Runnable(){
						 public void run()
						 {
							 closeProgressDialog();
							 Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
						 }
					 });
				 }

				 @Override
				 public void onResponse(Call call, Response response) throws IOException
				 {
					 String responseText = response.body().string();
					 boolean result = false;
					 if ("province".equals(type))
					 {
						 result = Utility.handleProvinceResponse(responseText);//存入数据库
					 }else if ("city".equals(type))
					 {
						 result = Utility.handleCityResponse(responseText,selectedProvince.getId());
					 }else if( "county".equals(type))
					 {
						 result = Utility.handleCountryResponse(responseText,selectedCity.getId());
					 }
					 if (result)
					 {
						 getActivity().runOnUiThread(new Runnable(){
							 public void run()
							 {
								 closeProgressDialog();
								 if ("province".equals(type))
								 {
									 queryProvinces();//查询完后还要显示
								 }else if("city".equals(type))
								 {
									 queryCities();
								 }else if ("county".equals(type))
								 {
									 queryCounties();
								 }
							 }
						 });
					 }
				 }

			 
		 });
	 }
	private void showProgressDialog(){
		if ( progressDialog == null)
		{
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("正在加载...");
			progressDialog.setCanceledOnTouchOutside(true);
			progressDialog.show();
		}
	}
	private void closeProgressDialog()
	{
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}
	
	
}
