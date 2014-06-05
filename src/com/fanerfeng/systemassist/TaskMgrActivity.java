/**
 * ��ȡϵͳ��������Ϣ����Ҫ�û�Ȩ�ޣ�android.permission.GET_TASKS
 * 
 * ActivityManager.RunningTaskInfo {
 *     public ComponentName baseActivity    // ������Ϊ��һ����������Ϣ
 *     public CharSequence description        // ����ǰ״̬������
 *     public int id                        // �����ID
 *     public int numActivities                // �������������Ļ����Ŀ
 *     public int numRunning                // �����д�������״̬�Ļ��Ŀ
 *     public Bitmap thumbnail                // ����ǰ״̬��λͼ��ʾ��ĿǰΪnull
 *     public ComponentName topActivity        // ��������ջ��ջ���Ļ���
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
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class TaskMgrActivity extends ListActivity {
	private static List<RunningTaskInfo> taskList = null;
	private static final int maxTaskNum = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_list);

		taskList = new ArrayList<RunningTaskInfo>();
		getTaskInfo();
		showTaskInfo();
	}

	public void showTaskInfo() {
		// ���½����б�
	
		List<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		for (Iterator<RunningTaskInfo> iterator = taskList.iterator(); iterator.hasNext();) {
			RunningTaskInfo taskInfo = iterator.next();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", taskInfo.id);
			map.put("numRunning", taskInfo.numRunning);
			map.put("numActivities", taskInfo.numActivities);
			map.put("topActivity", taskInfo.baseActivity.getClassName().toString());
			map.put("baseActivity",taskInfo.topActivity.getClassName().toString());
			infoList.add(map);				
		}

		Collections.sort(infoList, new Comparator<HashMap<String, Object>>(){
			public int compare(HashMap<String, Object> lhs,	HashMap<String, Object> rhs) {
				return (Integer.parseInt(lhs.get("id").toString()) - Integer.parseInt(rhs.get("id").toString()));
				}			
		});

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,	R.layout.task_list_item,
				new String[] { "id", "numRunning","numActivities","topActivity","baseActivity" }, 
				new int[] {R.id.id, R.id.numRunning,R.id.numActivities,R.id.topActivity, R.id.baseActivity});
		setListAdapter(simpleAdapter);
	}

	public int getTaskInfo() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		taskList = activityManager.getRunningTasks(maxTaskNum);
		return taskList.size();
	}
}
