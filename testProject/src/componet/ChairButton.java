package componet;

import game.Chair;
import main.GameView;
import newUtil.XImage;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ChairButton extends VirtureButton {

	public XImage[] images = null;// 图片对象数组
	public boolean isAnim = false;// 是否播放动画
	private int imgIndex = 0;// 图片编号

	Chair parent;
	public ChairButton(String id, float x, float y) {
		super(id, x, y);
		// TODO Auto-generated constructor stub
	}

	public ChairButton(XImage[] imgs, String id, float x, float y) {
		super(id, x, y);
		this.images = imgs;
		if (imgs != null) {
			buttonW = this.images[0].getImg().getWidth();
			buttonH = this.images[0].getImg().getHeight();
		}

	}

	public void setParent(Chair p){
		parent=p;
	}
	public int lip=0;
	@Override
	public void draw(Canvas canvas, Paint paint) {
		if (isAnim && images != null) {
			lip++;
			if(lip%5==0){
				imgIndex++;
				if (imgIndex > images.length - 1) {
					imgIndex = images.length - 1;
					 imgIndex=0;
					isAnim = false;
					parent.clearAllCake();
					GameView.addPoint();
					lip=0;
				}
			}
			
		}
		if (images != null) {
			canvas.drawBitmap(images[imgIndex].getImg(), x, y, paint);
		} else {
			paint.setColor(Color.RED);
			canvas.drawRect(btnRect, paint);
			paint.setTextSize(40);
			paint.setColor(Color.WHITE);
			canvas.drawText(this.id, x+10, y+10, paint);
		}

	}

	public void setAnimIndex(int index) {
		this.imgIndex = index;
	}

	@Override
	public boolean inArea(float x, float y) {
		if (images != null) {
			if (x > this.x && x < this.x + this.buttonW && y > this.y
					&& y < this.y + this.buttonH) {
				return true;
			}
		}
		return false;
	}

}
