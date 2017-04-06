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
						 result = Utility.handleProvinceResponse(responseText);
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
