package music;


import newUtil.Const;
import main.a.Main;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

class SoundData {
	private MediaPlayer player;
	private int hashcode;
	private boolean bPause;
	public boolean isPause() {
		return bPause;
	}
	public void setPause(boolean bPause) {
		this.bPause = bPause;
	}
	public SoundData(String path) {
		hashcode=path.hashCode();
		try {			
				AssetFileDescriptor assetFileDescritor = Main.ma
						.getApplicationContext().getAssets().openFd(path);

				player = new MediaPlayer();
				player.setDataSource(assetFileDescritor.getFileDescriptor(),
						assetFileDescritor.getStartOffset(),
						assetFileDescritor.getLength());
				player.prepare();
//			path = GameActivity.app.GetUpdateDir() + "/" + path;
//			player = new MediaPlayer();
//			player.setDataSource(path);
//			player.prepare();
		} catch (Exception e) {
			player = null;
		}
	}
	public SoundData(int id) {
		hashcode=id;
		try {			
			player=MediaPlayer.create(Main.ma, id);
			player.prepare();
		} catch (Exception e) {
			player = null;
		}
	}
	public boolean IsValid()
	{
		return player!=null;
	}
	public int getHashcode() {
		return hashcode;
	}

	public void play(boolean bLoop) {
		if (player != null) {
			player.stop();
			player.setLooping(bLoop);
			try {
				player.prepare();
				player.seekTo(0);
				player.start();
			} catch (Exception e) {
			}
		}
	}

	public void pause() {
		if (player != null) {
			player.pause();
		}
	}

	public void resume() {
		if (player != null) {
			player.start();
		}
	}

	public void stop() {
		if (player != null) {
			player.stop();
		}
	}

	public void unload() {
		if (player != null) {
			player.release();
		}
	}
	public boolean isPlaying()
	{
		if (player != null) {
			return player.isPlaying();
		}
		return false;
	}
}