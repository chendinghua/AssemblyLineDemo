package com.ycexample.ycapp;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.ychmi.sdk.YcApi;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LogoProgActivity extends Activity{
	
	private Button chooseFile;
	private TextView fileDire;
	private String curPath = new String("/mnt");
	YcApi   ycapi;
	//private CheckBox mCheckBoxProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_logoprog);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		

		//DrawFrame();

		ycapi  = new YcApi();
		
		StaticLogoSet();
		AnimationLogoSet();
		
	}

	private void StaticLogoSet()
	{
		final RadioGroup m_RadioGroup = (RadioGroup) findViewById(R.id.radioGroupUdiskSd);
		final RadioButton m_RadioUdisk = (RadioButton) findViewById(R.id.radioUdisk);
		final RadioButton m_RadioSdcard = (RadioButton) findViewById(R.id.radioSdcard);
		final EditText mEditTextLogoPath = (EditText)findViewById(R.id.editTextLogoPath);
		mEditTextLogoPath.setText("/mnt/usb_storage3/logo.bmp");
		m_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				if (checkedId == m_RadioUdisk.getId())
				{
					mEditTextLogoPath.setText("/mnt/usb_storage3/logo.bmp");
				}
				else if(checkedId == m_RadioSdcard.getId())
				{
					mEditTextLogoPath.setText("/mnt/external_sd/logo.bmp");
				}
			}
		}); 
		final Button mButtonOpenLogo = (Button)findViewById(R.id.buttonLogoOpen);		

		mButtonOpenLogo.setOnClickListener(new View.OnClickListener() {
			final EditText mEditTextLogoPath = (EditText)findViewById(R.id.editTextLogoPath);
			@SuppressWarnings("null")
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub				
				openCurDir(curPath,mEditTextLogoPath);					
			}
		});		
		final Button mButtonLogoProgram = (Button)findViewById(R.id.buttonLogoWrite);		

		mButtonLogoProgram.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("null")
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				
				File f=new File(mEditTextLogoPath.getText().toString());
				if(f.exists())
				{
					if(ycapi.UpdateBootStaticLogo(mEditTextLogoPath.getText().toString()))
						Toast.makeText(getApplicationContext(), "Upadating finish in udisk!",Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Upadating failed in udisk!",Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Logo is not exist in udisk!",Toast.LENGTH_SHORT).show();
				}	
						
			}
		});		
	}

	
	private void AnimationLogoSet()
	{
		final RadioGroup m_RadioGroup = (RadioGroup) findViewById(R.id.radioGroupUdiskSdAnimation);
		final RadioButton m_RadioUdisk = (RadioButton) findViewById(R.id.radioUdiskAnimation);
		final RadioButton m_RadioSdcard = (RadioButton) findViewById(R.id.radioSdcardAnimation);
		final EditText mEditTextLogoPath = (EditText)findViewById(R.id.editTextAnimationLogoPath);
		mEditTextLogoPath.setText("/mnt/usb_storage3/bootanimation.zip");
		m_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				if (checkedId == m_RadioUdisk.getId())
				{
					mEditTextLogoPath.setText("/mnt/usb_storage3/bootanimation.zip");
				}
				else if(checkedId == m_RadioSdcard.getId())
				{
					mEditTextLogoPath.setText("/mnt/external_sd/bootanimation.zip");
				}
			}
		}); 
		final Button mButtonOpenAnimationLogo = (Button)findViewById(R.id.buttonAnimationLogoOpen);	
		mButtonOpenAnimationLogo.setOnClickListener(new View.OnClickListener() {
			final EditText mEditTextLogoPath = (EditText)findViewById(R.id.editTextAnimationLogoPath);
			@SuppressWarnings("null")
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub				
				openCurDir(curPath,mEditTextLogoPath);					
			}
		});	
		final Button mButtonLogoProgram = (Button)findViewById(R.id.buttonAnimationLogoWrite);

		mButtonLogoProgram.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("null")
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				
				File f=new File(mEditTextLogoPath.getText().toString());
				if(f.exists())
				{
					if(ycapi.UpdateBootAnimationLogo(mEditTextLogoPath.getText().toString()))
						Toast.makeText(getApplicationContext(), "Upadating finish in udisk!",Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Upadating failed in udisk!",Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Logo is not exist in udisk!",Toast.LENGTH_SHORT).show();
				}	
						
			}
		});		
	}
	private void openCurDir(String curPath,final EditText mEditTextLogoPath){
		File f = new File(curPath);
		File[] file = f.listFiles();
		final List<Map<String,Object>> listItem = new ArrayList<Map<String,Object>>();
		

		
		if(!curPath.equals("/")){
			Map<String,Object> map1=new HashMap<String,Object>();
			map1.put("name", "sssssssssssssss");//"杩斿洖涓婁竴绾х洰褰�);
			//map1.put("image", R.drawable.back02);
			map1.put("path",f.getParent());
			map1.put("isDire", true);
			listItem.add(map1);
		}
		
		if(file != null){
			for(int i = 0; i < file.length; i++){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", file[i].getName());
				map.put("image", (file[i].isDirectory()) ? R.drawable.folder : R.drawable.doc);
				map.put("path",file[i].getPath());
				map.put("isDire", file[i].isDirectory());
				listItem.add(map);
			}
		}
		
		SimpleAdapter adapter = new SimpleAdapter(LogoProgActivity.this,listItem,R.layout.adapter,
				new String[]{"name","image"},new int[]{R.id.adapter_filename,R.id.adapter_image});
		
		final AlertDialog.Builder b =new Builder(LogoProgActivity.this);
		b.setAdapter(adapter, new DialogInterface.OnClickListener() {			
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if((Boolean) listItem.get(arg1).get("isDire")){
					
//					Toast.makeText(MainActivity.this, (String)listItem.get(arg1).get("path"), 
//							Toast.LENGTH_LONG).show();
					System.out.println((String)listItem.get(arg1).get("path"));
					System.out.println(((String)listItem.get(arg1).get("path").toString().trim()).equals("/"));
					
					openCurDir((String)listItem.get(arg1).get("path"),mEditTextLogoPath);
				}else{
					mEditTextLogoPath.setText((String)listItem.get(arg1).get("path"));
				}
//				
//				Toast.makeText(MainActivity.this, ""+arg1, Toast.LENGTH_LONG).show();

				
			}
		});
		b.show();
		
	}
	
}
