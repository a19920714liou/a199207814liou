package componet;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BtnManager {

	ArrayList<VirtureButton> btnManager = new ArrayList<VirtureButton>();

	public void onPressed(float x, float y) {
		for (int i = 0, size = btnManager.size(); i < size; i++) {
			VirtureButton bt = btnManager.get(i);
			if(bt.inArea(x,y)){
				bt.state=1;
			}
		}
	}
	
	public String getSelectBtn(float x, float y) {
		for (int i = 0, size = btnManager.size(); i < size; i++) {
			VirtureButton bt = btnManager.get(i);
			if(bt.inArea(x,y)){
				bt.state=1;
				return bt.id;
			}
		}
		return null;
	}
	public void onReleased(float x, float y) {
		for (int i = 0, size = btnManager.size(); i < size; i++) {
			VirtureButton bt = btnManager.get(i);
			if(bt.inArea(x,y)){
				bt.state=0;
			}
			
		}
	}
	public void put(VirtureButton btn) {
		btnManager.add(btn);
	}

	public void draw(Canvas canvas, Paint paint) {
		for (int i = 0, size = btnManager.size(); i < size; i++) {
			VirtureButton bt = btnManager.get(i);
			bt.draw(canvas, paint);
		}
	}
	
	
	
}
