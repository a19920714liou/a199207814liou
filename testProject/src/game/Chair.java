package game;

import android.graphics.Canvas;
import android.graphics.Paint;
import main.GameView;
import newUtil.XImage;
import componet.ChairButton;

public class Chair {

	boolean turnChair=false;
	public ChairButton cb=null;//妞呭瓙鎸夐挳
	public XImage[] cakes=new XImage[4];//宸﹀彸涓婁笅铔嬬硶鍥剧墖
	float pX,pY;//铔嬬硶鏀惧湪鐩樺瓙涓婄殑浣嶅瓙
	
	public Chair(ChairButton c) {
		cb=c;
		pX=cb.x;
		pY=cb.y+(cb.images[0].frame_h>>1)-40;
		c.setParent(this);
	}

	public boolean hasCake(int dir){
		if(cakes[dir]!=null) return true;
		return false;
	}
	public boolean hasAllCake(){//铔嬬硶鏄惁鎷兼弧浜�	
		boolean all=true;
		for(int i=0;i<cakes.length;i++){
			if(cakes[i]==null){
				all=false;
				break;
			}
		}
		return all;
	}
	public void clearAllCake(){//娓呴櫎鎵�湁铔嬬硶
		for(int i=0;i<cakes.length;i++){
			cakes[i]=null;
		}
		GameView.addPoint();
	}
	public void getCake(int dir,XImage img){
		cakes[dir]=img;
		
	}
	public void turnChair(){
		cb.isAnim=true;
	}
	public void draw(Canvas canvas, Paint paint){
		cb.draw(canvas, paint);
		if(cakes[2]!=null){//涓�			
			canvas.drawBitmap(cakes[2].getImg(), pX, pY, paint);
		}
		if(cakes[0]!=null){//宸�			
			canvas.drawBitmap(cakes[0].getImg(), pX+3, pY, paint);
		}
		if(cakes[1]!=null){//鍙�			
			canvas.drawBitmap(cakes[1].getImg(), pX-3, pY, paint);
		}
		if(cakes[3]!=null){//涓�			
			canvas.drawBitmap(cakes[3].getImg(), pX-3, pY, paint);
		}
	}
}
