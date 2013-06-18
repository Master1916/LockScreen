package com.pater.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private DevicePolicyManager policyManager;

	private ComponentName componentName;
	private ImageButton lockscreen;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		lockscreen=(ImageButton)findViewById(R.id.lockscreen);
		//LockScreen(lockscreen);
		 
		lockscreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LockScreen(v);
			}
		});	
	} 
	public void LockScreen(View v) {
		// ����豸������
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		//
		componentName = new ComponentName(this, LockReceiver.class);
		if (policyManager.isAdminActive(componentName)) {// �ж��Ƿ���Ȩ��(�������豸������)
			policyManager.lockNow();// ֱ������
			android.os.Process.killProcess(android.os.Process.myPid());
		} else {
			activeManager();// �����豸��������ȡȨ��
		}
	}

	// �����
	public void Bind(View v) {
		if (componentName != null) {
			policyManager.removeActiveAdmin(componentName);
			activeManager();
		}
	}

	private void activeManager() {
		// ʹ����ʽ��ͼ����ϵͳ����������ָ�����豸������
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "һ������");
		startActivity(intent);
	}
}
