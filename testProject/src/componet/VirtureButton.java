package componet;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import newUtil.XImage;

public abstract class VirtureButton {
	public String id;//按钮ID
	public float x;
	public float y;
	public int state;//按下状态
	public float buttonW=100;
	public float buttonH=50;
	public RectF btnRect=null;
	public boolean hide=false;
	public VirtureButton(String id,float x,float y){
		this.id=id;
		this.x=x;
		this.y=y;
		btnRect=new RectF(x,y,x+buttonW,y+buttonH);
	}
	public abstract void draw(Canvas canvas, Paint paint);
	public abstract boolean inArea(float x,float y);
}
