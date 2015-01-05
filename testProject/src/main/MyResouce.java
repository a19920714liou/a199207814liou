package main;


import android.content.Context;
import android.graphics.Bitmap;
import main.a.Main;
import main.a.R;
import main.a.ScreenScaleUtil;
import music.SoundManager;
import music.SoundPlayer;
import newUtil.Const;
import newUtil.ResManager;
import newUtil.Util;
import newUtil.XImage;

public class MyResouce extends ResManager {
	
	public static boolean finishLoaded=false;
	public XImage imgSplash[]=null;//闂睆鐨勫浘鐗�	
	public XImage mainMenu=null;//涓昏彍鍗曡儗鏅浘鐗�	
	public XImage img_start[]=null;//寮�鎸夐挳
	public XImage img_set[]=null;//璁剧疆鎸夐挳
	public XImage img_help[]=null;//甯姪鎸夐挳
	public XImage img_sound[]=null;//澹伴煶
	
	public XImage img_bg0=null;//娓告垙鑳屾櫙鍥剧墖
	
	public XImage img_cake[][]=null;//铔嬬硶
	
	
	
	public XImage exitGame=null;
	public XImage help=null;
	public XImage set=null;
	public XImage setIn1=null;
	
	public XImage game=null;
	public XImage bear[]=null;//鐔�	
	public XImage tiger[]=null;//鑰佽檸
	public XImage rabbit[]=null;//鍏斿瓙
	public XImage frog[]=null;//闈掕洐
	public XImage left=null;
	public XImage right=null;
	public XImage up=null;
	public XImage down=null;
	
	public MyResouce(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public XImage getImage(String path,boolean scal){
		Bitmap b=loadImageFromAst(path, false);
		int w = 0;
		int h = 0;
		w = b.getWidth();
		h = b.getHeight();
		if(scal){
//			float scaleWidth = ((float) BaseView.screenW) / w;
//			float scaleHeight = ((float) BaseView.screenH) / h;
			b = Util.getScalBitmap(b, BaseView.screen.ratio, BaseView.screen.ratio, 0);
			w = b.getWidth();
			h = b.getHeight();
		}
		XImage img=new XImage(b, w, h);
		b=null;
		return img;
	}
	
	public void initRes() {
		BaseView.screen=ScreenScaleUtil.calScale(BaseView.screenW, BaseView.screenH);
		imgSplash=new XImage[6];
		for(int i=0,size=imgSplash.length;i<size;i++){
			imgSplash[i]=getImage("window/b"+(i+1)+".png",true);
		}
		mainMenu = getImage("window/mainMenu.png",true);
		img_start=new XImage[2];
		for(int i=0,size=img_start.length;i<size;i++){
			img_start[i]=getImage("button/start"+i+".png",true);
		}
		img_set=new XImage[2];
		for(int i=0,size=img_set.length;i<size;i++){
			img_set[i]=getImage("button/set"+i+".png",true);
		}
		img_help=new XImage[2];
		for(int i=0,size=img_help.length;i<size;i++){
			img_help[i]=getImage("button/help"+i+".png",true);
		}
		img_sound=new XImage[2];
		for(int i=0,size=img_sound.length;i<size;i++){
			img_sound[i]=getImage("button/sound"+i+".png",true);
		}
		
		img_bg0=getImage("window/bg0.jpg",true);
		
		img_cake=new XImage[Const.CAKE_NUM][4];
		for(int i=0;i<Const.CAKE_NUM;i++){
			for(int j=0;j<img_cake[i].length;j++){
				img_cake[i][j]=getImage("cake/"+i+"/"+(j+1)+".png",true);
			}
		}
		
		
		
		exitGame=getImage("user/exitGame.png",false);
		help=getImage("user/help.png",false);
		set=getImage("user/set.png",false);
		game=getImage("user/game.png",false);
		bear=new XImage[4];// 鐔�		
		for(int i=0,size=bear.length;i<size;i++){
			bear[i] = getImage("desk/bear/d"+(i+1)+".png", true);
		}
		tiger=new XImage[4];// 鑰佽檸
		for(int i=0,size=tiger.length;i<size;i++){
			tiger[i] = getImage("desk/tiger/d"+(i+1)+".png", true);
		}
//		
		rabbit=new XImage[4];// 鍏斿瓙
		for(int i=0,size=rabbit.length;i<size;i++){
			rabbit[i] = getImage("desk/rabbit/d"+(i+1)+".png", true);
		}
		frog=new XImage[4];// 闈掕洐
		for(int i=0,size=frog.length;i<size;i++){
			frog[i] = getImage("desk/frog/d"+(i+1)+".png", true);
		}
		
		SoundPlayer.init(Main.ma);
		finishLoaded=true;
	}
}
