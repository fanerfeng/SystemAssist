/**
 * ActivityManager.RunningAppProcessInfo {
 *     public int importance                // ������ϵͳ�е���Ҫ����
 *     public int importanceReasonCode        // ���̵���Ҫԭ�����
 *     public ComponentName importanceReasonComponent    // �����������������Ϣ
 *     public int importanceReasonPid        // ��ǰ���̵��ӽ���Id
 *     public int lru                        // ��ͬһ����Ҫ�����ڵĸ�������ֵ
 *     public int pid                        // ��ǰ����Id
 *     public String[] pkgList                // �����뵱ǰ���̵����а���
 *     public String processName            // ��ǰ���̵�����
 *     public int uid                        // ��ǰ���̵��û�Id
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
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class ProcMgrActivity extends ListActivity {
	private static List<RunningAppProcessInfo> procList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.proc_list);
		setContentView(R.layout.task_list);
		procList = new ArrayList<RunningAppProcessInfo>();
		getProcessInfo();

		showProcessInfo();
	}

	public void showProcessInfo() {
		// ���½����б�
		List<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		for (Iterator<RunningAppProcessInfo> iterator = procList.iterator(); iterator.hasNext();) {
			RunningAppProcessInfo procInfo = iterator.next();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("pid", procInfo.pid);
			map.put("uid", procInfo.uid);
			map.put("processName", procInfo.processName);
			infoList.add(map);
		}
		
		Collections.sort(infoList, new Comparator<HashMap<String, Object>>(){
			public int compare(HashMap<String, Object> lhs,	HashMap<String, Object> rhs) {
				return (Integer.parseInt(lhs.get("pid").toString()) - Integer.parseInt(rhs.get("pid").toString()));
				}			
		});
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,	R.layout.proc_list_item,
				new String[] { "pid", "uid","processName"}, 
				new int[] {R.id.pid, R.id.uid,R.id.processName});
		setListAdapter(simpleAdapter);
	}

	public int getProcessInfo() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		procList = activityManager.getRunningAppProcesses();
		return procList.size();
	}
}
