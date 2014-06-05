/**
 * ActivityManager.RunningServiceInfo {
 *     public long activeSince        // �����һ�α������ʱ�� (�����Ͱ󶨷�ʽ)
 *     public int clientCount        // ���ӵ��÷���Ŀͻ�����Ŀ
 *     public int clientLabel        // ��ϵͳ����Ϊ�ͻ��˳����ṩ���ڷ��ʱ�ǩ
 *     public String clientPackage    // ��ϵͳ���񡿰󶨵��÷���İ���
 *     public int crashCount        // ���������ڼ䣬����crash�Ĵ���
 *     public int flags                // �������е�״̬��־
 *     public boolean foreground    // �����Ƿ���Ϊǰ̨����ִ��
 *     public long lastActivityTime    // �÷�������һ�����ʱ��
 *     public int pid                // ��0ֵ����ʾ�������ڵĽ���Id
 *     public String process        // �������ڵĽ�������
 *     public long restarting        // �����0����ʾ����û��ִ�У����ڲ���������ʱ�����������
 *     public ComponentName service    // ���������Ϣ
 *     public boolean started        // ��ʶ�����Ƿ���ʾ������
 *     public int uid                // ӵ�и÷�����û�Id
 * }
 */
package com.fanerfeng.systemassist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ServiceMgrActivity extends ListActivity {
	private static List<RunningServiceInfo> serviceList = null;
	private static final int maxServiceNum = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.service_list);
		setContentView(R.layout.task_list);
		serviceList = new ArrayList<RunningServiceInfo>();
		getServiceInfo();
		showServiceInfo();
	}

	public void showServiceInfo() {
		// ���½����б�
		List<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		
		for (Iterator<RunningServiceInfo> iterator = serviceList.iterator(); iterator.hasNext();) {
			RunningServiceInfo serviceInfo = iterator.next();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("pid", serviceInfo.pid);
			map.put("uid", serviceInfo.uid);
			map.put("process", serviceInfo.process);
			map.put("service", serviceInfo.service.getClassName().toString());

			infoList.add(map);
		}
		
		Collections.sort(infoList, new Comparator<HashMap<String, Object>>(){
			public int compare(HashMap<String, Object> lhs,	HashMap<String, Object> rhs) {
				return (Integer.parseInt(lhs.get("pid").toString()) - Integer.parseInt(rhs.get("pid").toString()));
				}			
		});

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,	R.layout.service_list_item,
				new String[] { "pid", "uid","process","service"}, 
				new int[] {R.id.service_pid, R.id.service_uid,R.id.process,R.id.service});
		setListAdapter(simpleAdapter);
	}

	public int getServiceInfo() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		serviceList = activityManager.getRunningServices(maxServiceNum);
		return serviceList.size();
	}
}
