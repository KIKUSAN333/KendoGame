import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;

public class ConsoleView {

	char screen[][];
	
	public final static int WIDTH = 80;
	public final static int	HEIGHT = 24;
	
	private Model model;	
	
	public ConsoleView(Model model) {
		super();
		this.model = model;
		screen = new char[HEIGHT][WIDTH];
		
		clear();
	}
	
	public void update() {
		clear();
		String s = "" + model.getNum();
		
		Menu menu = model.getMenu();
		
		
		//画面を描画

		//メニュー画面にいるなら
		if(menu.getInMenu()) {
			menu.paint(this);

			paint();
			return;
		}

	
		//ゲームを描画
		Stage stage = model.getStage();
		HpBar hpBar = model.getHpBar();
		Enemy enemy = model.getEnemy();
		Player player = model.getPlayer();
		Time time = model.getTime();
		Result result = model.getResult();
		Ranking ranking = model.getRanking();
		
		//トレーニングモード かつ PvEモードである場合
		if(stage.getIsTraining() && !stage.getIsPvP()) {
			//入力を簡易的に表示
			LinkedList<Bullet> bullets = model.getBullets();
			LinkedList<Bullet> newBullets = new LinkedList<Bullet>();
			for(Bullet bullet: bullets) {
				if(!bullet.isOutOfScreen()) {
					newBullets.add(bullet);
				}
				bullet.paint(this);
			}
			bullets = newBullets;
			drawString(s,10,5);
			
			hpBar.showHp(this);
			
		}
		//ステージを描画
		stage.paint(this);
		
		//敵を描画
		enemy.paint(this);
		
		//プレイヤーを描画
		player.paint(this);
		
		//HPバーを描画
		hpBar.showHpBar(this);

		//残り時間を描画
		time.showTime(this);
		
		//リザルトを表示
		result.showResult(this);
		
		//リザルト画面でないなら
		if(!result.getIsResult()) {
			stage.showHowToPlay(this);
		}
		
		//ランキング表示
		ranking.showInput(this);
		
		
		paint();
	}
	
	public void clear() {
		for(int j = 0; j < screen.length; j++) {
			for(int i = 0; i < screen[j].length; i++) {
				screen[j][i] = ' ';
			}
		}
	}

	public void paint() {
		StringBuffer s = new StringBuffer();
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				s.append(screen[y][x]);
			}
			s.append("\n");
		}
		System.out.println(s);
	}
	
	public void put(char c,int x,int y) {
		if(0 <= x && x < WIDTH && 0 <= y && y <HEIGHT) {
			screen[y][x] = c;
		}
	}
	
	public void drawString(String s, int x, int y) {
		for(int i = 0;i < s.length(); i++) {
			put(s.charAt(i),x + i,y);
		}
	}
	
	public void drawRect(char c, int x, int y, int w ,int h) {
		
		for(int j = 0;j < h; j ++) {
			for(int i = 0; i < w; i++) {
				if(i == 0 || i == w - 1 || j == 0|| j == h - 1) {
					put(c,x+i,y+j);
				}
			}
		}
		
	}
	
	public void drawFramedString(String s, char c, int x, int y) {
		drawString(s,x,y);
		drawRect(c,x - 1,y - 1,s.length() + 2,3);
	}
	
	public void drawOval(char c, int x, int y, int w, int h) {
		
	    double a = w / 2.0;
	    double b = h / 2.0;

	    int angle = 360;
	    for (int i = 0; i < angle; i++) {
	        double theta = 2 * Math.PI * i / angle;
	        int X = (int) Math.round(x + a * Math.cos(theta));
	        int Y = (int) Math.round(y + b * Math.sin(theta));
	        put(c, X, Y); 
	    }
			
	}
	
	public void drawText(String text,int x,int y) throws IOException {
		URL textURL = getClass().getResource(text);
		BufferedReader reader = new BufferedReader(
		new InputStreamReader(textURL.openStream()));
		String line;
		

		int count = 0;
		while ((line = reader.readLine()) != null) {
			drawString(line,x,y+count);
			count++;
		}
			
			
	}
	
	

}
