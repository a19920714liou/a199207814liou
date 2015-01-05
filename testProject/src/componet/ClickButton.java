package componet;

import newUtil.XImage;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ClickButton extends VirtureButton {
	private XImage[] stateImg = new XImage[2];

	public ClickButton(String id, float x, float y) {
		super(id, x, y);
		// TODO Auto-generated constructor stub
	}

	public ClickButton(XImage[] res, String id, float x, float y) {
		super(id, x, y);
		this.stateImg = res;
		if (stateImg != null) {
			buttonW = this.stateImg[0].getImg().getWidth();
			buttonH = this.stateImg[0].getImg().getHeight();
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		if(hide) return;
		if (this.state == 0) {
			canvas.drawBitmap(stateImg[0].getImg(), x, y, paint);
		} else {
			canvas.drawBitmap(stateImg[1].getImg(), x, y, paint);
		}
	}

	@Override
	public boolean inArea(float x, float y) {
		// TODO Auto-generated method stub
		if (x > this.x && x < this.x + this.buttonW && y > this.y && y < this.y + this.buttonH) {
			return true;
		}
		return false;
	}

}
