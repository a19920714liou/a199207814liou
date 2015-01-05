package newUtil;

import java.io.IOException;
import java.io.InputStream;

import main.a.Main;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

public class ResManager {
	// 用于加载图片模式的优化有利于内存
	private BitmapFactory.Options ARGB4444options = new BitmapFactory.Options();
	private BitmapFactory.Options ARGB8888options = new BitmapFactory.Options();
	private BitmapFactory.Options RGB565options = new BitmapFactory.Options();

	private Context con;
	private static AssetManager assetManager;
	
	public ResManager(Context c) {
		con=c;
		assetManager=con.getAssets();
		ARGB8888options.inDither = false;
		ARGB8888options.inJustDecodeBounds = false;
		ARGB8888options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		ARGB4444options.inDither = false;
		ARGB4444options.inJustDecodeBounds = false;
		ARGB4444options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		RGB565options.inDither = false;
		RGB565options.inJustDecodeBounds = false;
		RGB565options.inPreferredConfig = Bitmap.Config.RGB_565;
//		RGB565options.inSampleSize=2;
		try {
			BitmapFactory.Options.class.getField("inPurgeable").set(ARGB8888options, true);
			BitmapFactory.Options.class.getField("inPurgeable").set(ARGB4444options, true);
			BitmapFactory.Options.class.getField("inPurgeable").set(RGB565options, true);

			BitmapFactory.Options.class.getField("inInputShareable").set(ARGB8888options, true);
			BitmapFactory.Options.class.getField("inInputShareable").set(ARGB4444options, true);
			BitmapFactory.Options.class.getField("inInputShareable").set(RGB565options, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 从assets文件夹下获取Bitmap图片对象
	 * 
	 * @param assetManager
	 * @param fileName
	 *            文件名字 文件夹名字，当前assets文件夹下传入""即可。
	 *            如果在assets目录下还有个文件夹mm，则访问mm下的0.jpg则folderName传入的
	 *            是"mm"。完整访问路径是:"mm/0.jpg" 要获得mm目录下的所有文件是assetManager.list("mm")
	 *            要获得assets目录下的所有文件是assetManager.list("")
	 * @return
	 */
	public static InputStream getAssetsData(String fileName) {
		InputStream in = null;
		try {
			in = assetManager.open(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
	/**
	 * 以路径方式加载
	 * @param path
	 * @param transparency
	 * @return
	 */
	public Bitmap loadImageFromAst(String path, boolean transparency) {
		return loadImageFromAst(getAssetsData(path), transparency);
	}
	/**
	 * 以流的方式加载
	 * @param in
	 * @param transparency
	 * @return
	 */
	public Bitmap loadImageFromAst(InputStream in, boolean transparency) {
		Bitmap bitmap = BitmapFactory.decodeStream(in, null, transparency ? ARGB4444options : RGB565options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		if ((w < 16 || w > 128) && (h < 16 || h > 128)) {
			return bitmap;
		}
		Bitmap img = filterBitmapTo565(bitmap, w, h);
		if (img != null) {
			return img;
		}
		return bitmap;
	}

	/**
	 * 当非透明图像不等于RGB565模式时，将它过滤为此彩色模式
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static final Bitmap filterBitmapTo565(Bitmap bitmap, int w, int h) {
		Config config = bitmap.getConfig();
		if (config != Config.RGB_565 && config != Config.ARGB_4444 && config != Config.ALPHA_8) {
			boolean isOpaque = true;
			int pixel;
			int size = w * h;
			int[] pixels = new int[size];
			bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
			for (int i = 0; i < size; i++) {
				pixel = premultiply(pixels[i]);
				if (isOpaque && (pixel >>> 24) < 255) {
					isOpaque = false;
					break;
				}
			}
			pixels = null;
			if (isOpaque) {
				Bitmap newBitmap = bitmap.copy(Config.RGB_565, false);
				bitmap.recycle();
				bitmap = null;
				return newBitmap;
			}
		}
		return null;
	}
	/**
	 * 像素前乘
	 * 
	 * @param argbColor
	 * @return
	 */
	public static int premultiply(int argbColor) {
		int a = argbColor >>> 24;
		if (a == 0) {
			return 0;
		} else if (a == 255) {
			return argbColor;
		} else {
			int r = (argbColor >> 16) & 0xff;
			int g = (argbColor >> 8) & 0xff;
			int b = argbColor & 0xff;
			r = (a * r + 127) / 255;
			g = (a * g + 127) / 255;
			b = (a * b + 127) / 255;
			return (a << 24) | (r << 16) | (g << 8) | b;
		}
	}
	
}
