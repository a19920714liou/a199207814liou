package newUtil;

public class Const {
	/**鏃嬭浆鎴栫炕杞弬鏁�*/
	public static final int TRANS_NONE = 0;
	public static final int TRANS_ROT90 = 5;
	public static final int TRANS_ROT180 = 3;
	public static final int TRANS_ROT270 = 6;
	public static final int TRANS_MIRROR = 2;
	public static final int TRANS_MIRROR_ROT90 = 7;
	public static final int TRANS_MIRROR_ROT180 = 1;
	public static final int TRANS_MIRROR_ROT270 = 4;
	public static final int TRANS_MIRROR_VERTICAL = TRANS_MIRROR_ROT180;
	
	
	
	
	
	
	public static final int CAKE_NUM=4;
	public static final int CAKE_RED=0;
	public static final int CAKE_BLUE=1;
	public static final int CAKE_ORG=2;
	public static final int CAKE_YL=3;
	
	/** 娓告垙鐘舵� **/
	
	public static final byte STATE_SPLASH = 0;// 闂睆
	public static final byte STATE_SPLASH1 = 1;// 绗簩涓棯灞�	
	public static final byte STATE_MAIN_MENU = 2;// 鐢讳富鑿滃崟
	public static final byte STATE_GAME_RUN = 3;// 鐢绘父鎴�	
	public static final byte STATE_GAME_RECORD = 4;// 娓告垙璁板綍
}
