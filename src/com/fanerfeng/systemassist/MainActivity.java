package com.fanerfeng.systemassist;

import com.fanerfeng.systemassist.R;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	private GestureDetector gestureDetector;
	final int RIGHT = 0;
	final int LEFT = 1;
	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取该Activity里面的TabHost组件
		tabHost = getTabHost();
		// TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		// tabHost.setup();

		// 创建第一个Tab页
		Intent intent1 = new Intent(this, TaskMgrActivity.class);
		Intent intent2 = new Intent(this, ProcMgrActivity.class);
		Intent intent3 = new Intent(this, ServiceMgrActivity.class);

		TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("Task") // 设置标题
				.setContent(intent1); // 设置内容
		// 添加第一个标签页
		tabHost.addTab(tab1);

		TabSpec tab2 = tabHost
				.newTabSpec("tab2")
				// 在标签标题上放置图标
				.setIndicator("Process",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(intent2);
		tabHost.addTab(tab2);

		TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator("Service").setContent(intent3);
		tabHost.addTab(tab3);

		gestureDetector = new GestureDetector(MainActivity.this, new MyGestureDetector());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gestureDetector.onTouchEvent(event); 
		return super.onTouchEvent(event);
	}
	  
    @Override  
    public boolean dispatchTouchEvent(MotionEvent event) {  
        if (gestureDetector.onTouchEvent(event)) {  
            event.setAction(MotionEvent.ACTION_CANCEL);  
        }  
        return super.dispatchTouchEvent(event);  
    }  
  
	class MyGestureDetector extends SimpleOnGestureListener{
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,	float velocityY) {
			float x = e2.getX() - e1.getX();
			float y = e2.getY() - e1.getY();
			if (y > -100 && y<100){				
				if (x > 80) {
					doResult(RIGHT);
					return true;
				} else if (x < -80) {
					doResult(LEFT);
					return true;
				}				
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	};

	public void doResult(int action) {
		int i = tabHost.getCurrentTab();		
		switch (action) {
		case RIGHT:
			tabHost.setCurrentTab(i = i == 2 ? i = 0 : ++i);
			break;
		case LEFT:			
			tabHost.setCurrentTab(i = i == 0 ? i = 2 : --i);
			break;
		}
	}
};
