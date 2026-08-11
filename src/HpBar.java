
public class HpBar {

	private Player player;
	private Enemy enemy;
	
	private final static int WIDTH = 32;
	private final static int HEIGHT = 3;
	private final static int BARLENGTH = 30;
	
	private final static int PLAYER_X = 2;
	private final static int ENEMY_X = 73;
	private final static int CHARACTER_Y = 1;
	
	private final static int PLAYER_HPBAR_X = 3;
	private final static int ENEMY_HPBAR_X = 47;
	private final static int HPBAR_Y = 3;
	
	
	
	private char hpChar = '█';
	
	public HpBar(Player player, Enemy enemy) {
		super();
		this.player = player;
		this.enemy = enemy;
	}
	
	public void showHpBar(ConsoleView view) {
		
		int playerHp = player.getHp();
		int enemyHp = enemy.getHp();
		int maxHp = 1000;
		

		
		view.drawRect('-', PLAYER_HPBAR_X - 1, HPBAR_Y - 1, WIDTH, HEIGHT);
		view.drawRect('-', ENEMY_HPBAR_X - 1, HPBAR_Y - 1, WIDTH, HEIGHT);
		
		
		view.drawString("Player", PLAYER_X, CHARACTER_Y);
		for(int i = 0;i < BARLENGTH;i++) {
			if(i < playerHp * BARLENGTH/maxHp)
				view.put(hpChar, PLAYER_HPBAR_X + i, HPBAR_Y);
		}
		
		view.drawString("Enemy", ENEMY_X, CHARACTER_Y);
		for(int i = 0;i < BARLENGTH;i++) {
			if(i < enemyHp * BARLENGTH/maxHp)
				view.put(hpChar, ENEMY_HPBAR_X + i, HPBAR_Y);
		}
		
	}
	
	public void showHp(ConsoleView view) {
		int playerHp = player.getHp();
		int enemyHp = enemy.getHp();
		
		//それぞれの体力の中身を見るため
		view.drawString(""+playerHp, 2, 7);
		view.drawString(""+enemyHp, 74, 7);
	}
	
}
