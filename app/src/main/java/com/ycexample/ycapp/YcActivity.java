package com.ycexample.ycapp;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;



import com.ychmi.sdk.YcApi;

import android.util.Log;



public class YcActivity extends Activity {
	YcApi ycapi;
	
	public boolean mBeepStatus=false;
	public boolean mLedStatus=false;
	public boolean mShowbarStatus=true;
	
	int max;
	int current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yc);
		
		ycapi = new YcApi(); 
		
		//beep
		BeepInit();
		
		//hide or show status bar
		HideShowBarInit();	
		
		//״̬�Ƴ�ʼ��
		LedInit();	
		
		UniqueIDInit();
		

		//(7)���ڳ�ʼ��
		SerialInit();
		
		StartAndroidLauncher(this);
		
		UpdateLogo();
		
	
        addShortCut(YcActivity.this);
	}
	
	
	
	public void onBackPressed()//��ⷵ�ؼ��ĺ���
	{
		ycapi.ReturnLauncher(YcActivity.this);
	}
	

	//(1)��������ʼ��
	private void BeepInit()
	{
		Button btnBeep = (Button)findViewById(R.id.beep);
	    btnBeep.setOnTouchListener(new View.OnTouchListener(){   
	    	@Override
	        public boolean onTouch(View v, MotionEvent event) {               
	                if(event.getAction() == MotionEvent.ACTION_DOWN){   
	                	
	                   //�������ð���ʱ�ı���ͼƬ  
	                	if(mBeepStatus)
	                	{
	                		mBeepStatus=false;
	                		ycapi.SetBeep(false);	                		
	                	}
	                	else
	                	{
	                		mBeepStatus=true;
	                		ycapi.SetBeep(true);
	                	}
	                	
	                }	        
	                return false;
	        }      
	    });  
	}
	//(1)��������ʼ��
	private void HideShowBarInit()
	{
		Button btnBeep = (Button)findViewById(R.id.hideshowbar);          
	    btnBeep.setOnTouchListener(new View.OnTouchListener(){   
	    	@Override
	        public boolean onTouch(View v, MotionEvent event) {               
	                if(event.getAction() == MotionEvent.ACTION_DOWN){   
	                	
	                   //�������ð���ʱ�ı���ͼƬ  
	                	if(mShowbarStatus)
	                	{
	                		mShowbarStatus=false;
	                		ycapi.HideNviBarFull();
	                	}
	                	else
	                	{
	                		mShowbarStatus=true;
	                		ycapi.ShowNviBarFull();
	                	}
	                	
	                }	        
	                return false;
	        }      
	    });  
	}
	//(2)״̬�Ƴ�ʼ��
	private void LedInit()
	{
		Button btnLed = (Button)findViewById(R.id.statusled);          
		btnLed.setOnTouchListener(new View.OnTouchListener(){   
	    	@Override
	        public boolean onTouch(View v, MotionEvent event) {               
	                if(event.getAction() == MotionEvent.ACTION_DOWN){       
	                   //�������ð���ʱ�ı���ͼƬ  
	                	if(mLedStatus)
	                	{	 
	                		mLedStatus=false;
	                		ycapi.SetLed(false);
	                		
	                	}
	                	else
	                	{
	                		mLedStatus=true;
	                		ycapi.SetLed(true);	                		
	                	}
	                }	        
	                return false;       
	        }      
	    });  
	}
	
	private void UniqueIDInit() 
	{
		final Button UniqueID = (Button)findViewById(R.id.uniqueid);          
		UniqueID.setOnTouchListener(new View.OnTouchListener(){   
	    	@Override
	        public boolean onTouch(View v, MotionEvent event) {               
	                if(event.getAction() == MotionEvent.ACTION_DOWN){       
	                	String str=new String();
	            		str=ycapi.GetUniqueID();
	            		UniqueID.setText("Unique ID : "+str);
	            		Log.d("","### Get Unique ID =" +str+"\n");
	                }	        
	                return false;       
	        }      
	    });  
		
	}
			
	private void StartAndroidLauncher(final Context context)
	{
		final Button button= (Button)findViewById(R.id.returnlauncher);
    	button.setOnClickListener(new View.OnClickListener()
    	{
			public void onClick(View v) {
				ycapi.ReturnLauncher(context);
			}
		});		
	}

	//(7) ���ڳ�ʼ��   
	public void SerialInit() 
	{		
		final Button button = (Button)findViewById(R.id.serial);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(YcActivity.this, SerialActivity.class));
			}
		});
	}
	
	public void UpdateLogo()
	{		
		final Button button = (Button)findViewById(R.id.updatelogo);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(YcActivity.this, LogoProgActivity.class));
			}
		});
	}
	


	
	public void addShortCut(Context context) 
	{
		// ���������ͼ���һ����ͼ����,��Ӧ����Receiver��intent-filter�е�action
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// ���ÿ�ݷ�ʽ������
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
		// �������ظ�����
		shortcut.putExtra("duplicate", false);
		// Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
		Intent intent = new Intent(context, context.getClass());
		intent.setAction(Intent.ACTION_MAIN);
		// ָ����ݷ�ʽָ��������������Ϊintent
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// ָ����ݷ�ʽͼ��
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
		Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher));
		context.sendBroadcast(shortcut);
	}

}
