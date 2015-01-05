package main;

import main.a.ScreenScaleResult;
import newUtil.Const;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.FrameLayout;

/**
 * 游戏主框架
 * 
 * @author Administrator
 * 
 */
public abstract class BaseView extends SurfaceView implements Callback,
		Runnable {

	public static ScreenScaleResult screen;
	Thread thread;// 游戏主线程
	private SurfaceHolder holder = null; // 控制对象

	public Canvas canvas;// 画布

	public Paint paint;// 画笔

	public static final int TIME_IN_FRAME = 50;// 每帧的最大时长

	public static int screenW = 800;// 屏幕宽度

	public static int screenH = 480;// 屏幕高度

	public static int mainCount;// 主线程里的计数器

	public static byte mainState;// 菜单中的主状态

	public static FrameLayout frameLayout;// 层布局管理器

	public Context mContext = null;

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public BaseView(Context context, FrameLayout frameLayout) {
		super(context);
		this.mContext = context;
		this.frameLayout = frameLayout;
		holder = getHolder();
		holder.addCallback(this);
		setFocusable(true);//允许获得焦点

		initPaint();
		
	}

	/**
	 * 设置游戏状态
	 * 
	 * @param state
	 */
	public void setState(byte state) {
		mainState = state;
	}

	/**
	 * 初始化画笔
	 */
	public void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);	//AntiAlias抗锯齿 
		paint.setTextSize(18);
	}

	/**
	 * 启动线程
	 */
	public void begin() {
		if (thread == null) {
			thread = new Thread(this);//thread是多线程？！~
		}
		thread.start();
	}

	/**
	 * 结束线程
	 */
	public void stopThread() {
		thread = null;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {// 在surface的大小发生改变时激发

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {// 在surface创建时激发
		begin();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {// 在surface销毁时激发
		stopThread();
	}

	@Override
	public void run() {
		Thread curThread = Thread.currentThread();
		while (curThread == thread) {
			long startTime = System.currentTimeMillis();

			/** 在这里加上线程安全锁 **/
			synchronized (holder) {
				canvas = holder.lockCanvas();// 拿到当前画布 然后锁定
				gameRun();
				if (canvas == null) {
					System.out.println("canvas is null");
				}
				if (paint == null) {
					System.out.println("paint is null");
				}
				
				if (canvas != null) {
					draw(canvas, paint);
					holder.unlockCanvasAndPost(canvas);
				}
			}
			long endTime = System.currentTimeMillis();
			int diffTime = (int) (endTime - startTime);
			while (diffTime <= TIME_IN_FRAME) {
				diffTime = (int) (System.currentTimeMillis() - startTime);
				Thread.yield();// 线程等待
			}
		}
	}

	/**
	 * 绘图方法
	 * 
	 * @param canvas
	 * @param paint
	 */
	abstract public void draw(Canvas canvas, Paint paint);

	abstract public void gameRun();
}
