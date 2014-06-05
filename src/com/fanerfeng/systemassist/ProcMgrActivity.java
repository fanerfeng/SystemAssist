/**
 * ActivityManager.RunningAppProcessInfo {
 *     public int importance                // 进程在系统中的重要级别
 *     public int importanceReasonCode        // 进程的重要原因代码
 *     public ComponentName importanceReasonComponent    // 进程中组件的描述信息
 *     public int importanceReasonPid        // 当前进程的子进程Id
 *     public int lru                        // 在同一个重要级别内的附加排序值
 *     public int pid                        // 当前进程Id
 *     public String[] pkgList                // 被载入当前进程的所有包名
 *     public String processName            // 当前进程的名称
 *     public int uid                        // 当前进程的用户Id
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
		// 更新进程列表
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
