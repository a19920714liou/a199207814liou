package main;

import game.ChaiManager;
import game.Chair;

import java.util.Random;

import componet.BtnManager;
import componet.ChairButton;
import componet.ClickButton;

import main.a.Main;
import main.a.R;
import music.SoundManager;
import music.SoundPlayer;
import newUtil.Const;
import newUtil.XImage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Process;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * ��Ϸ���ʵ��
 * 
 * @author Administrator
 * 
 */
public class GameView extends BaseView {
	public static GameView gv;
	float x, y;
	float offX = 0, offY = 0;
	float upX, upY;
	public MyResouce resManager = null;
	Random rand = new Random();
	XImage curCake=null;//�������䵰��
	boolean gameOver=false;
	int cakeType,cakeDir;//�������ͣ����ⷽ��
	int cakeX,cakeY;//��ǰ��������
	boolean turnChair=false;
	boolean getCake=false;//�Ƿ�Ե���
	private static int totalRecord;//�ܷ���
	private static int speed=4;//�����ٶ�
	public static int fpLip = 0;
	int time=60;//����ʱģʽ
	int gameMode=0;//��Ϸģʽ������ʱģʽ������ģʽ

	
	private int flashIndex = 0;

	Chair c_bear = null;//��
	Chair c_tiger = null;//�ϻ�
	Chair c_rabbit = null;//����
	Chair c_frog = null;//����
	
	ClickButton btnStart = null;
	ClickButton btnSet = null;
	ClickButton btnHelp = null;
	ClickButton btnSound = null;
//	BtnManager btnManager = null;
	BtnManager menuBtnManager = null;
	ChaiManager chaiManager= null;
	Chair curChair=null;
	int [] records=new int[10];
	public static GameView getInstance() {
		return gv;
	}

	public GameView(Context context, FrameLayout frameLayout) {
		super(context, frameLayout);
		gv = this;
		resManager = new MyResouce(Main.ma);
		loadSysRes();
		setState(Const.STATE_SPLASH);
	}

	public void loadSysRes() {
		System.out.println("begin loadSysRes");
		resManager.initRes();
//		btnManager = new BtnManager();
		menuBtnManager = new BtnManager();
		chaiManager=new ChaiManager();
		
		int curX=screen.lucX+(screenW>>1)-100,curY=screen.lucY+(screenH>>1)+50;
		btnStart = new ClickButton(resManager.img_start, "start", curX, curY);
		btnSet = new ClickButton(resManager.img_set, "set", screenW-200, screenH-150);
		btnSound = new ClickButton(resManager.img_sound, "sound", screenW-200, screenH-250);
		btnHelp = new ClickButton(resManager.img_help, "record", screenW-200, screenH-350);
		
		
		int cbX=40,cbY=(screenH>>1)-140;
		int lipDest=resManager.bear[0].frame_w+40;
		ChairButton cb=new ChairButton(resManager.bear, "bear", cbX, cbY);// resManager����ͼƬ
		c_bear=new Chair(cb);
		cbX+=lipDest;
		cb=new ChairButton(resManager.tiger, "tiger", cbX, cbY);// resManager����ͼƬ
		c_tiger=new Chair(cb);
		cbX+=lipDest;
		cb=new ChairButton(resManager.rabbit, "rabbit", cbX, cbY);// resManager����ͼƬ
		c_rabbit=new Chair(cb);
		cbX+=lipDest;
		cb=new ChairButton(resManager.frog, "frog", cbX, cbY);// resManager����ͼƬ
		c_frog=new Chair(cb);
		chaiManager.put(c_bear);
		chaiManager.put(c_tiger);
		chaiManager.put(c_rabbit);
		chaiManager.put(c_frog);
		menuBtnManager.put(btnStart);
		menuBtnManager.put(btnSet);
		menuBtnManager.put(btnHelp);
		menuBtnManager.put(btnSound);
	}

	@Override
	/**
	 * ��ͼ����
	 * @param canvas
	 * @param paint
	 */
	public void draw(Canvas canvas, Paint paint) {
		paint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, screenW, screenH, paint);// ����
		switch (mainState) {
		case Const.STATE_SPLASH://����
			drawSplash0(canvas, paint);
			break;
		case Const.STATE_GAME_RUN:
			drawGameRun(canvas, paint);
			break;
		case Const.STATE_MAIN_MENU:
			drawMainMenu(canvas, paint);
			break;
		case Const.STATE_GAME_RECORD:
			drawRecord(canvas, paint);
			break;
		}
	}

	/**
	 * ����Ϸ���˵�����
	 * 
	 * @param canvas
	 * @param paint
	 */

	public void drawMainMenu(Canvas canvas, Paint paint) {
		canvas.drawBitmap(resManager.mainMenu.getImg(), screen.lucX, screen.lucY, paint);
		menuBtnManager.draw(canvas, paint);
		time=60;
		totalRecord=0;
		curCake=null;
	}

	public void drawRecord(Canvas canvas, Paint paint){
		canvas.drawBitmap(resManager.img_bg0.getImg(), screen.lucX, screen.lucY, paint);
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		for(int i=0;i<records.length;i++){
			canvas.drawText("��¼��"+records[i], screen.lucX+50, screen.lucY+40*i+50, paint);// �����㴦������
		}
	}
	public int[] getNewRecord(){
		int[] newRecord=new int[10];
		int index=0;
		for(int i=0;i<records.length;i++){
			if(totalRecord>=records[i]){
				index=i;
			}
		}
		for(int i=0;i<index-1;i++){
			newRecord[i]=records[i];
		}
		newRecord[index]=records[index];
		for(int i=index+1;i<records.length;i++){
			newRecord[i]=records[i];
		}
		return newRecord;
	}
	public void saveRecord(){
		
		int[] temp=getNewRecord();
		records=temp;
		SharedPreferences sp = Main.ma.getSharedPreferences("gamerecord", 0);
		//��������
		for(int i=0;i<records.length;i++){
			Editor editor = sp.edit();
			editor.putInt("record"+i, records[i]);
			editor.commit();
		}
		
	}
	public void readRecord(){
		SharedPreferences sp = Main.ma.getSharedPreferences("gamerecord", 0);
		//��������
		for(int i=0;i<records.length;i++){
			records[i]=sp.getInt("record"+i, 0);
		}
	}
	/**
	 * ��LOGO
	 * 
	 * @param canvas
	 * @param paint
	 */
	public void drawSplash0(Canvas canvas, Paint paint) {
		canvas.drawBitmap(resManager.imgSplash[flashIndex].getImg(), screen.lucX, screen.lucY,
				paint);
	}
	public static void addPoint(){
		totalRecord+=100;
		speed=4+totalRecord/500;
	}
	public static void reducePoint(){
		totalRecord-=10;
		speed=4+totalRecord/500;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		String btnId=null;
		switch (mainState) {
		case Const.STATE_GAME_RUN:
			if(event.getAction()==MotionEvent.ACTION_UP){
				if(curCake!=null){
					curChair = chaiManager.getSelectChair(x, y);
					if(curChair!=null&&!turnChair){
						SoundPlayer.playSound(R.raw.cake);
						if(curChair.hasCake(cakeDir)){
							reducePoint();
						}
						getCake=true;
					}
					
				}
			}
			
//			if (btnId != null && btnId.equals("bear")) {
////				brearBtn.isAnim = true;// ��������
//				getCake=true;
//			}
			break;
		case Const.STATE_MAIN_MENU:
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				menuBtnManager.onPressed(x, y);
//				if (btnId != null && btnId.equals("start")) {
//					setState(Const.STATE_GAME_RUN);
//				}
			}
			if(event.getAction()==MotionEvent.ACTION_UP){
				btnId = menuBtnManager.getSelectBtn(x, y);
				if (btnId != null && btnId.equals("start")) {
					SoundPlayer.playSound(R.raw.button);
					setState(Const.STATE_GAME_RUN);
//					SoundPlayer.startMusic(R.raw.black1);
				}else if (btnId != null && btnId.equals("record")){
					readRecord();
					setState(Const.STATE_GAME_RECORD);
				}
				menuBtnManager.onReleased(x, y);
			}
			break;
		}
		return true;
	}

	public void drawLoadRes(Canvas canvas, Paint paint) {
		paint.setColor(Color.GREEN);
		canvas.drawRect(0, 0, screenW, screenH, paint);// ����

		paint.setColor(Color.BLUE);
		paint.setTextSize(40);
		canvas.drawBitmap(resManager.setIn1.getImg(), 0, 0, paint);// ���ý���
		
		// canvas.drawText("x����"+upX+"y����"+upY, this.getWidth()>>1,
		// this.getHeight()>>1,paint);
		canvas.drawText("x����" + upX + "y����" + upY, 100, 150, paint);// �����㴦������
		canvas.drawText("x����" + offX + "y����" + offY, 50, 100, paint);// �ƶ��������
	}

	private Matrix matrix = new Matrix();
	int degrees = 0;

	/**
	 * ������Ϸ
	 * 
	 * @param canvas
	 * @param paint
	 */
	public void drawGameRun(Canvas canvas, Paint paint) {
		canvas.drawBitmap(resManager.img_bg0.getImg(), screen.lucX, screen.lucY, paint);// ����Ϸ����
		//btnManager.draw(canvas, paint);
		chaiManager.draw(canvas, paint);
		paint.setColor(Color.RED);
		paint.setTextSize(30);
		canvas.drawText("�÷֣�"+totalRecord, screen.lucX+40, screen.lucY+40, paint);// �����㴦������
		canvas.drawText("ʣ��ʱ�䣺"+time, screen.lucX+40, screen.lucY+80, paint);// �����㴦������
		if(curCake!=null){
			canvas.drawBitmap(curCake.getImg(), cakeX, cakeY, paint);// ����Ϸ����
		}
		if(gameOver){
			paint.setColor(Color.RED);
			paint.setTextSize(60);
			canvas.drawText("", (screenW>>1)-80, (screenH>>1)-30, paint);// �����㴦������
		}
	}
	public void restartGame() {
		Main.ma.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					AlertDialog.Builder builder = new AlertDialog.Builder(Main.ma);
					builder.setMessage("�Ƿ����¿�ʼ?");
					builder.setTitle("GAME OVER");
					builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							gameOver=false;
							time=60;
							totalRecord=0;
							curCake=null;
							chaiManager.clearAllCake();
						}
					});
					builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							setState(Const.STATE_MAIN_MENU);
							dialog.dismiss();
						}
					});
					builder.create().show();
				} catch (Exception e) {
				}
			}
		});
		
	}
	int lip = 0;// ������
	@Override
	public void gameRun() {
		switch (mainState) {
		case Const.STATE_GAME_RUN:
			if(!gameOver){
				if(curCake!=null){
					cakeY+=speed;
					if(cakeY>screenH){
						curCake=null;
					}
					if(getCake){
						curChair.getCake(cakeDir, curCake);
						getCake=false;
						if(curChair.hasAllCake()){
							turnChair=true;
						}
						curCake=null;
					}
					if(turnChair){
						curChair.turnChair();
						turnChair=false;
					}
				}else{
					cakeType=rand.nextInt(Const.CAKE_NUM);
					cakeDir=rand.nextInt(Const.CAKE_NUM);
					cakeX=rand.nextInt(screenW-180);
					cakeY=0;
					curCake=resManager.img_cake[cakeType][cakeDir];
				}
				lip++;
				if(lip%20==0){
					time--;
					if(time<=0){
						gameOver=true;
						lip=0;
						saveRecord();
						restartGame();
					}
				}
				
			}
			
			break;
		case Const.STATE_SPLASH:// ��һ������
			lip++;
			if (lip % 20 == 0) { // ����������30�����˵�
				flashIndex++;
				if (flashIndex >= 6) {
					this.setState(Const.STATE_MAIN_MENU);
					SoundPlayer.startMusic(R.raw.black1);
					lip = 0;
				}
			}
			break;
		case Const.STATE_MAIN_MENU:
			break;
		}
	}

	AlertDialog.Builder bld = null;

	public void initWinUI(String message) {
		bld = new AlertDialog.Builder(mContext);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
	}

	public void showWinUI() {
		Main.ma.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					bld.show();
				} catch (Exception e) {
				}
			}
		});
	}

}
