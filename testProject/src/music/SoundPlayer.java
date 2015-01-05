package music;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.a.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {

	private static MediaPlayer[] music;
	private static SoundPool soundPool;

	private static boolean musicSt = true; // 音乐开关
	private static boolean soundSt = true; // 音效开关
	private static Context context;

	private static Map<Integer, Integer> soundMap; // 音效资源id与加载过后的音源id的映射关系表

	private static Map<Integer, MediaPlayer> musicMap; //
	private static int[] musicId = { R.raw.menu, R.raw.over, R.raw.black1};

	/**
	 * 初始化方法
	 * 
	 * @param c
	 */
	public static void init(Context c) {
		context = c;
		initAllMusic();
		initSound();
	}

	// 初始化音效播放器
	private static void initSound() {
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		soundMap = new HashMap<Integer, Integer>();
		soundMap.put(R.raw.button, soundPool.load(context, R.raw.button, 1));
		soundMap.put(R.raw.cake, soundPool.load(context, R.raw.cake, 1));
	}

	// 初始化音乐播放器
	private static void initAllMusic() {
		// int r = new Random().nextInt(musicId.length);
		musicMap = new HashMap<Integer, MediaPlayer>();
		int len = musicId.length;
		music = new MediaPlayer[len];
		for (int i = 0; i < len; i++) {
			music[i] = MediaPlayer.create(context, musicId[i]);
			if (i == 1) {
				music[i].setLooping(false);
			} else {
				music[i].setLooping(true);
			}
			try {
				music[i].prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			musicMap.put(musicId[i], music[i]);
		}

	}

	/**
	 * 播放音效
	 * 
	 * @param resId
	 *            音效资源id
	 */
	public static void playSound(int resId) {
		if (soundSt == false)
			return;

		Integer soundId = soundMap.get(resId);
		if (soundId != null)
			soundPool.play(soundId, 1, 1, 1, 0, 1);
	}

	/**
	 * 暂停音乐
	 */
	public static void pauseMusic(int id) {
		
		MediaPlayer curMusic = musicMap.get(id);
		if (curMusic.isPlaying()) {
			curMusic.pause();
		}
	}

	/**
	 * 播放音乐
	 */
	public static void startMusic(int id) {
		MediaPlayer curMusic=null;
//		for(int i=0;i<musicMap.size();i++){
//			curMusic = musicMap.get(musicId[i]);
//			curMusic.pause();
//		}
		curMusic = musicMap.get(id);
		if (musicSt) {
//			try {
//				curMusic.prepare();
////				curMusic.
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			curMusic.start();
		}
	}

	/**
	 * 停止音乐
	 */
	public static void release() {
//		MediaPlayer curMusic = musicMap.get(id);
//		if (musicSt) {
//			curMusic.stop();
//		}
		MediaPlayer curMusic=null;
		for(int i=0;i<musicMap.size();i++){
			curMusic = musicMap.get(musicId[i]);
			curMusic.stop();
			curMusic.release();
		}
	}

	/**
	 * 获得音乐开关状态
	 * 
	 * @return
	 */
	public static boolean isMusicSt() {
		return musicSt;
	}

	/**
	 * 设置音乐开关
	 * 
	 * @param musicSt
	 */
	public static void setMusicSt(boolean musicSt) {
		// SoundPlayer.musicSt = musicSt;
		// if (musicSt)
		// music.start();
		// else
		// music.stop();
	}

	/**
	 * 获得音效开关状态
	 * 
	 * @return
	 */
	public static boolean isSoundSt() {
		return soundSt;
	}

	/**
	 * 设置音效开关
	 * 
	 * @param soundSt
	 */
	public static void setSoundSt(boolean soundSt) {
		SoundPlayer.soundSt = soundSt;
	}

	/**
	 * 发出‘邦’的声音
	 */
	public static void button() {
		playSound(2);
	}
}
