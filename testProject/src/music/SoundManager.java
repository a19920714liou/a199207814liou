package music;

import java.util.Vector;

public class SoundManager {
	public static Vector<SoundData> soundData = new Vector<SoundData>();
	static SoundData background;
	static float effectVolume = 1;
	static float backgroundVolume = 1;
	static boolean backgroundLoop = false;

	public static void PlayBackgroundMusic(String musicName, boolean bLoop) {
		if (background != null) {
			background.stop();
		}
		backgroundLoop = bLoop;

		if (musicName.endsWith("mp3")) {
			musicName = musicName.replaceAll("mp3", "ogg");
		}

		background = new SoundData(musicName);
		if (backgroundVolume != 0) {
			background.play(bLoop);
		}
	}

	public static void initBackgroundMusic(int id,boolean bLoop){
		if (background != null) {
			background.stop();
		}
		backgroundLoop = bLoop;

//		if (musicName.endsWith("mp3")) {
//			musicName = musicName.replaceAll("mp3", "ogg");
//		}

		background = new SoundData(id);
	}
	public static void PlayBackgroundMusic(int id, boolean bLoop) {
		//if (backgroundVolume != 0) {
			background.play(bLoop);
		//}
	}
	public static void Vibrate() {
	}

	public static void StopBackgroundMusic() {
		if (background != null) {
			background.stop();
		}
	}

	public static void PauseBackgroundMusic() {
		if (background != null) {
			background.pause();
		}
	}

	public static void ResumeBackgroundMusic() {
		if (background != null) {
			background.resume();
		}
	}

	public static void SetBackgoundMusicVolume(float v) {
		if (v < 0)
			v = 0;
		if (v > 1)
			v = 1;
		backgroundVolume = v;
		if (background != null) {
			if (v == 0) {
				background.stop();
			} else {
				background.play(backgroundLoop);
			}
		}
	}

	public static void SetEffectVolume(float v) {
		if (v < 0)
			v = 0;
		if (v > 1)
			v = 1;
		effectVolume = v;
	}

	public static float GetBackgoundMusicVolume() {
		return backgroundVolume;
	}

	public static float GetEffectVolume() {
		return effectVolume;
	}

	public static int LoadSoundEffect(String effectName) {
		int hashcode = effectName.hashCode();
		if (FindSoundEffect(hashcode) != null) {
			return hashcode;
		}
		SoundData data = new SoundData(effectName);
		if (data.IsValid()) {
			soundData.add(data);
			return hashcode;
		}
		return -1;
	}

	public static void UnloadSoundEffect(int sid) {
		SoundData data = FindSoundEffect(sid);
		if (data != null) {
			data.unload();
			soundData.remove(data);
		}
	}

	public static void UnloadAllSoundEffect() {
		for (int i = 0; i < soundData.size(); i++) {
			SoundData data = soundData.elementAt(i);
			data.unload();
		}
		soundData.clear();
	}

	private static SoundData FindSoundEffect(int sid) {
		for (int i = 0; i < soundData.size(); i++) {
			SoundData data = soundData.elementAt(i);
			if (sid == data.getHashcode()) {
				return data;
			}
		}
		return null;
	}

	public static void PlayEffect(int sid, boolean bLoop) {
		if (effectVolume == 0)
			return;

		SoundData data = FindSoundEffect(sid);
		if (data != null) {
			data.play(bLoop);
		}
	}

	public static void StopEffect(int sid) {
		SoundData data = FindSoundEffect(sid);
		if (data != null) {
			data.stop();
		}
	}

	public static void StopEffects(int[] sids) {
		for (int i = 0; i < sids.length; i++) {
			StopEffect(sids[i]);
		}
	}

	public static void StopAllEffect() {
		for (int i = 0; i < soundData.size(); i++) {
			SoundData data = soundData.elementAt(i);
			if (data.isPlaying()) {
				data.stop();
			}
		}
	}

	public static void OnResume() {
		for (int i = 0; i < soundData.size(); i++) {
			SoundData data = soundData.elementAt(i);
			if (data.isPause()) {
				data.resume();
				data.setPause(false);
			}
		}
		if (background != null) {
			if (background.isPause()) {
				background.resume();
				background.setPause(false);
			}
		}
	}

	public static void OnPause() {
		for (int i = 0; i < soundData.size(); i++) {
			SoundData data = soundData.elementAt(i);
			if (data.isPlaying()) {
				data.pause();
				data.setPause(true);
			}
		}
		if (background != null) {
			if (background.isPlaying()) {
				background.pause();
				background.setPause(true);
			}
		}
	}
}
