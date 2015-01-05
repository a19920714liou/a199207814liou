package game;

import java.util.ArrayList;

import componet.VirtureButton;

import android.graphics.Canvas;
import android.graphics.Paint;


public class ChaiManager {
	ArrayList<Chair> chairs = new ArrayList<Chair>();
	public ChaiManager() {
		
	}

	public void put(Chair c) {
		chairs.add(c);
	}
	public void draw(Canvas canvas, Paint paint) {
		for (int i = 0, size = chairs.size(); i < size; i++) {
			Chair c = chairs.get(i);
			c.draw(canvas, paint);
		}
	}
	public Chair getSelectChair(float x, float y) {
		for (int i = 0, size = chairs.size(); i < size; i++) {
			Chair bt = chairs.get(i);
			if(bt.cb.inArea(x,y)){
				return bt;
			}
		}
		return null;
	}
	public void clearAllCake(){
		for (int i = 0, size = chairs.size(); i < size; i++) {
			Chair c = chairs.get(i);
			c.clearAllCake();
		}
	}
}
