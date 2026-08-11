import java.io.InputStreamReader;
import java.net.URL;

public class Character {
	public final static int WIDTH = 14;
	public final static int HEIGHT = 7;
	
	public final static int ATTACK11_FRAME = 3;
	public final static int ATTACK12_FRAME = 3;
	public final static int ATTACK13_FRAME = 8;
	public final static int ATTACK21_FRAME = 10;
	public final static int ATTACK22_FRAME = 5;
	public final static int ATTACK31_FRAME = 3;
	public final static int ATTACK32_FRAME = 5;
	public final static int DEFENSE_FRAME = 5;
	public final static int TAKEDAMAGE91_FRAME = 2;
	public final static int TAKEDAMAGE92_FRAME = 2;
	public final static int TAKEDAMAGE93_FRAME = 5;
	public final static int BEOPEN_FRAME = 20;
	
	public final static int ATTACK11_ID = 11;
	public final static int ATTACK12_ID = 12;
	public final static int ATTACK13_ID = 13;
	public final static int ATTACK21_ID = 21;
	public final static int ATTACK22_ID = 22;
	public final static int ATTACK31_ID = 31;
	public final static int ATTACK32_ID = 32;
	public final static int DEFENSE_ID = 80;
	public final static int TAKEDAMAGE91_ID = 91;
	public final static int TAKEDAMAGE92_ID = 92;
	public final static int TAKEDAMAGE93_ID = 93;
	public final static int BEOPEN_ID = 100;
	public final static int IDLE_ID = 00;
	
	public static final int DAMAGE_VERY_HIGH = 200;
	public static final int DAMAGE_HIGH = 150;
	public static final int DAMAGE_MEDIUM = 100;
	public static final int DAMAGE_LOW = 50;
	public static final int DAMAGE_VERY_LOW = 20;
	
	private static final int DISTANCE = 4;
	public static final int ATTACK_REACH_LONG = 9;
	public static final int ATTACK_REACH_MEDIUM = 7;
	public static final int ATTACK_REACH_SHORT = 6;
	
	
	private int x;
	private int y;
	private char image[][];
	private int MotionEndFrame;
	private int motionID;
	
	private int hp;
	
	public Character(int xx,int yy) {
		super();
		x = xx;
		y = yy;
		MotionEndFrame = -1;
		motionID = 00;
		hp = 1000;
		image = new char[HEIGHT][WIDTH];
		try {
			this.setImage("Idle.txt");
		}catch(Exception e) {
			System.out.println("setImage()エラー");
		}
		
	}
	
	private void setImage(String text) throws Exception{
		//ファイルを開く
		//text = "../src/" + text;
		URL textURL = getClass().getResource(text);
		//System.out.println(text);
		//System.out.println(textURL);
		InputStreamReader fr = new InputStreamReader(textURL.openStream());
		
		int input = fr.read();
		int i = 0;
		int j = 0;
		
		while(input != -1) {
			char ch = (char) input;
			
			if(ch == '#') {//テキストには改行のタイミングで#を入れる
				j++;
				i = 0;	
			}
		    else if(ch == '\n' || ch == '\r') {//改行の際は何もしない
		    	
		    }
			else {
				image[j][i] = ch;
				i++;
			}
			
			input = fr.read();
			
		}
		
	}
	
	public void takeDamage(int damage) {
		hp -= damage;
	}
	
	public void moveLeft(){
		if(canMoveLeft())
			x--;
	}

	public void moveRight(){
		if(canMoveRight())
			x++;
	}
	
	public boolean canMoveLeft() {
		if(x <= 1) {
			return false;
		}
		
		return true;
	}
	
	public boolean canMoveRight() {
		if(ConsoleView.WIDTH - WIDTH - 1 <= x) {
			return false;
		}
		return true;
	}
	
	public void doMotion(int id,String motion,int current,int EndFrameOffset) {
		try {
			motionID = id;
			this.setImage(motion);
			this.MotionEndFrame = current + EndFrameOffset;
		}catch(Exception e) {
			System.out.println("doMotion():erroe id = " + id);
		}
	}
	
	public void idle(String motion) {
		try {
			motionID = IDLE_ID;
			this.setImage(motion);
			MotionEndFrame = -1;
		}catch(Exception e) {
			System.out.println("idle():error");
		}
	}
	

	public void paint(ConsoleView view) {
		for(int j = 0;j < HEIGHT;j++) {
			for(int i = 0;i < WIDTH;i++) {
				if(image[j][i] == '-' ||image[j][i] == 'O' || image[j][i] == '|'
					|| image[j][i] =='/' || image[j][i] == 'ｷ'	) {
					view.put(image[j][i],x + i, y + j);
				}
			}
		}
	}
	
	
	 
	
	public boolean isOutOfScreen() {
		
		return(x < 0 || ConsoleView.WIDTH <= x || 
				y < 0 || ConsoleView.HEIGHT <= y);

	}
	
	public boolean isMotionActive(int current) {
		
		if(this.motionID == IDLE_ID) {
			return false;
		}
		
		if(this.MotionEndFrame >= current) {
			return true;
		}
		
		return false;
	}
	
	public void setMotionEndFrame(int n) {
		this.MotionEndFrame = n;
	}
	
	public int getMotionEndFrame() {
		return this.MotionEndFrame;
	}
	
	public int getMotionID() {
		return this.motionID;
	}
	
	public boolean canDoMotion(int id,int current) {
		if(this.motionID == id && this.MotionEndFrame == current) {
			return true;
		}
		return false;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getHp() {
		return this.hp;
	}
	
	
	public boolean isHit(int otherCharacterX,int attackDistance) {
		if(Math.abs(this.getX() - otherCharacterX) + DISTANCE < attackDistance) {
			return true;
		}
		return false;
	}
	
	
	
}
