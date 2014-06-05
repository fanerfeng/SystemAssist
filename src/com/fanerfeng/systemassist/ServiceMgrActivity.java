/**
 * ActivityManager.RunningServiceInfo {
 *     public long activeSince        // 服务第一次被激活的时间 (启动和绑定方式)
 *     public int clientCount        // 连接到该服务的客户端数目
 *     public int clientLabel        // 【系统服务】为客户端程序提供用于访问标签
 *     public String clientPackage    // 【系统服务】绑定到该服务的包名
 *     public int crashCount        // 服务运行期间，出现crash的次数
 *     public int flags                // 服务运行的状态标志
 *     public boolean foreground    // 服务是否被做为前台进程执行
 *     public long lastActivityTime    // 该服务的最后一个活动的时间
 *     public int pid                // 非0值，表示服务所在的进程Id
 *     public String process        // 服务所在的进程名称
 *     public long restarting        // 如果非0，表示服务没有执行，将在参数给定的时间点重启服务
 *     public ComponentName service    // 服务组件信息
 *     public boolean started        // 标识服务是否被显示的启动
 *     public int uid                // 拥有该服务的用户Id
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
		// 更新进程列表
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
