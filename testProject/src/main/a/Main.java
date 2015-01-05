package main.a;


import newUtil.Const;
import main.BaseView;
import main.GameView;
import main.MyResouce;
import music.SoundPlayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class Main extends Activity {

	public static boolean isPause;
	public boolean isOncreate = false;
	FrameLayout frameLayout=null;
	int frameNum=0;
	public static Main ma;// 当前主Activity的引用

	/**
	 * 构造
	 */
	public Main() {
		ma = this;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("power="+"onCreate==="+isOncreate);
		if(frameLayout==null){
			System.out.println("frameLayout is null");
		}
		ma = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置为无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置为全屏显示
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 强制为横屏

		// 取得屏幕的宽度，高度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		BaseView.screenW = dm.widthPixels;// 取得屏幕的宽度
		BaseView.screenH = dm.heightPixels;// 取得屏幕的高度

		if (!isOncreate) {
			// 使用层布局管理器
			addView(new main.GameView(this, frameLayout));
			setContentView(frameLayout);
			isOncreate = true;
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("power="+"onPause");
		isPause = true;
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("power="+"onResume");
//		if (isPause) {
//			isPause = false;
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setCancelable(false);
//			// builder.setMessage("暫停遊戲中...");
//			builder.setTitle("提示");
//			builder.setPositiveButton("继续游戏",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
////							setPause(isPause);
////							SoundManager.OnResume();
////							editTxt.setVisibility(0);
//							dialog.dismiss();
//						}
//					});
//			builder.create().show();
//
////			if (checkGamePlayer()) {
////				Process.killProcess(Process.myPid());
////				// pluginsTips(mContext);
////			}
//
//			// Intent intent=new Intent();
//			// intent.setClass(com.gamebean.GameActivity.app,
//			// PauseActivity.class);
//			// startActivity(intent);
//		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("power="+"onDestroy");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("power="+"onRestart");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("power="+"onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("power="+"onStop");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			runExit();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void addView(View v){
		if(v==null) return;
		if(frameLayout==null){
			frameLayout = new FrameLayout(this);
		}
		frameLayout.addView(v);
		frameNum++;
	}
	public void runExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ma);
		builder.setMessage("确定退出游戏吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				switch (GameView.mainState) {
				case Const.STATE_GAME_RECORD:
				case Const.STATE_GAME_RUN:
					GameView.getInstance().setState(Const.STATE_MAIN_MENU);
					break;

				case Const.STATE_MAIN_MENU:
					dialog.dismiss();
					Process.killProcess(Process.myPid());
					break;
				}
				
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	private long exitTime = 0;

    public void ExitApp()
    {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
            } else
            {
                    this.finish();
            }

    }
}