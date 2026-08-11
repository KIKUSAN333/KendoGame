
public class Result {
	
	private final static int LOSES_BAR_X = 30;
	private final static int WINS_BAR_X = 32;
	public final static int RESULT_BAR_X = 34;
	public final static int RESULT_BAR_Y = 8;

	private Player player;
	private Enemy enemy;
	private boolean isResult;
	
	public Result(Player pplayer,Enemy eenemy) {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
		
		player = pplayer;
		enemy = eenemy;
		isResult = false;
	}
	
	public void showResult(ConsoleView view) {
		if(!isResult) {
			return;
		}
		
		if(isPlayerHpZero()) {
			view.drawFramedString("  Player Loses...  ",'#', LOSES_BAR_X, RESULT_BAR_Y);
		}
		else if(isEnemyHpZero()) {
			view.drawFramedString("  Player  Wins! ",'#', WINS_BAR_X, RESULT_BAR_Y);
		}
	}
	
	
	public void updateResult() {
		if(isPlayerHpZero() || isEnemyHpZero()) {
			isResult = true;
			
		}
	}
	
	public boolean isPlayerHpZero () {
		if(player.getHp() <= 0) {
			return true;
		}
		return false;
	}

	public boolean isEnemyHpZero () {
		if(enemy.getHp() <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean getIsResult() {
		return isResult;
	}
}
