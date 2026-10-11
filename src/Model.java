import java.util.LinkedList;
import java.io.*;

public class Model {
	private ConsoleView view;
	private ConsoleController controller;
	
	private int num;
	private int resultNum;
	
	private LinkedList<Bullet> bullets;
	private Player player;
	private Enemy enemy;
	private Stage stage;
	private HpBar hpBar;
	private Time time;
	private int countTimeElapsed;
	private Result result;
	private Menu menu;
	private Ranking ranking;

	public Model(){
		MenuReset();
	}
	
	private void battleReset(int level,boolean isTraining,boolean isPvE) {
		player = new Player();
		enemy = new Enemy(level);
		
		player.setEnemy(enemy);
		enemy.setPlayer(player);
		
		bullets = new LinkedList<Bullet>();
		
		hpBar = new HpBar(player,enemy);
		stage = new Stage(isTraining,isPvE);
		time = new Time();
		countTimeElapsed = 0;
		result = new Result(player,enemy);
	}
	
	private void MenuReset() {
		view = new ConsoleView(this);
		controller = new ConsoleController(this);
		ranking = new Ranking();
		menu = new Menu(ranking);
	}
	
	//排他処理を行う
	public synchronized void process(String event) {
		if (event == null || event.isEmpty()) return;
		
		//メニュー画面にいるときの選択を受け付ける
		if(menu.getInMenu()) {
			
			checkMenuInput(event);
			
		    num++;
		    view.update();
			return;
		}
		
		//リザルト画面にいるとき
		if(result.getIsResult() || time.isTimeEnd()) {
			
			processResult(event);
			
		    num++;
		    view.update();
			return;
		}
		
		//PvPモードのとき
		if(stage.getIsPvP()) {
			checkEnemyInput(event);
			
		    //リザルト結果を更新
		    result.updateResult();
		    //リザルト画面に行くとき
		    if(result.getIsResult()) {
		    	resultNum = num;
		    }
		    
		}
		//トレーニングモードにいるとき
		else if(stage.getIsTraining()) {
			checkEnemyInput(event);
			
			if(event.equals("q")) {
				menu.goMenu();
			}
			
		    if(event.equals("TIME_ELAPSED")) {
		        Bullet bullet = new Bullet(0, 22, 1, 0, '-');
		        bullets.add(bullet);
		    }
		    else if (event != null) {//テスト用 入力を画面上に表示する
		        Bullet bullet = new Bullet(0, 22, 1, 0, event.charAt(0));
		        bullets.add(bullet);
		    }
			
		}//PvEモードのとき
		else{
			
		    if (event.equals("TIME_ELAPSED")) {//一定時間経過プロセス
		    	countTimeElapsed++;
		    	
		    	//1秒経過したとき
		    	if(countTimeElapsed == 20) {
		    		countTimeElapsed = 0;
		    		time.oneSecondHasPassed();
		    	}
		    }
		    
	    	//敵がモーション中でなければ敵の行動を行う
		    if (!enemy.isMotionActive(num)) {
		    	enemy.doAction(num);
		    }

		    //リザルト結果を更新
		    result.updateResult();
		    //リザルト画面に行くとき
		    if(result.getIsResult()) {
		    	resultNum = num;
		    }
		    
		}
		
		//以下に対戦モード時の共通部分
		
	    //次のモーションを再生
	    player.doNextMotions(num);
	    enemy.doNextMotions(num);
	    
	    //ゲーム中のプレイヤーの入力を監視する
	    checkPlayerInput(event);

	    num++;
	    view.update();
	}
	
	private void checkMenuInput(String event) {
		//上を選択
		if(event.equals("w")) {
			menu.up();
		}
		//下を選択
		else if(event.equals("s")) {
			menu.down();
		}
		//一つ前に戻る
		else if(event.equals("q")) {
			if(menu.getInLevelSelect() || menu.getInRanking()) {
				menu.goHome();
			}
		}
		//選択項目を決定
		else if(event.equals("f")) {
			checkUserSelect();
		}
	}
	
	private void checkPlayerInput(String event) {
	    //プレイヤーがモーション中でなければ入力を受け付ける
	    if(!player.isMotionActive(num)) {
		    if (event.equals("a")) {
		        player.moveLeft();
		    }
		    else if (event.equals("d")) {
		        player.moveRight();
		    }
		    else if (event.equals("j")) {
		        player.attack11(num);
		    }
		    else if(event.equals("k")) {
		    	player.attack21(num);
		    }
		    else if(event.equals("l")) {
		    	player.attack31(num);
		    }
		    else if(event.equals("s")) {
		    	player.defense(num);
		    }
	    }
	}
	
	private void checkEnemyInput(String event) {
		//エネミーがモーション中でなければ入力を受け付ける
	    if(!enemy.isMotionActive(num)) {
		    if (event.equals("1")) {
		        enemy.moveLeft();
		    }
		    else if (event.equals("3")) {
		        enemy.moveRight();
		    }
		    else if (event.equals("7")) {
		        enemy.attack11(num);
		    }
		    else if(event.equals("8")) {
		    	enemy.attack21(num);
		    }
		    else if(event.equals("9")) {
		    	enemy.attack31(num);
		    }
		    else if(event.equals("2")) {
		    	enemy.defense(num);
		    }
	    }
	}
	
	private void processResult(String event) {
		
		//数秒間入力を受け付けない
		while(num <= resultNum + 100) {
		    num++;
		    view.update();
		    return;
		}
	
		//PvPモード か プレイヤーが負け ならランキング入力をしない
		if(stage.getIsPvP() || result.isPlayerHpZero() || time.isTimeEnd()) {
			menu.goMenu();
			MenuReset();
			return;
		}
		
		//入力を受け付ける
		ranking.accecptingInput();
		ranking.enterName(event,time.getTime());
		
		//スペースで入力終了
		if(event.equals(" ")) {
			menu.goMenu();
			MenuReset();
		}
	}
	
	private void checkUserSelect() {
	    int chosenID = menu.getChooseID();

	    // レベルセレクト画面のとき
	    if (menu.getInLevelSelect()) {
	        for (int i = 1; i <= 4; i++) {
	            if (chosenID == Menu.MENU_BAR1_ID + (i - 1)) {
	                battleReset(i, false, false);
	                menu.exitMenu();
	                return;
	            }
	        }
	    }

	    // ホーム画面のとき
	    if (menu.getInHome()) {
	        if (chosenID == Menu.MENU_BAR1_ID) {
	            menu.goLevelSelect();
	        } else if (chosenID == Menu.MENU_BAR2_ID) {
	        	battleReset(0, true, true);
	           menu.exitMenu();
	        } else if (chosenID == Menu.MENU_BAR3_ID) {
	           battleReset(0, true, false);
	           menu.exitMenu();
	        } else if (chosenID == Menu.MENU_BAR4_ID) {
	        	menu.goRanking();
	        }
	    }
	}
	
	

	
	public void run() throws IOException {
		controller.run();
	}
	
	public static void main(String[] args) throws IOException{
		Model model = new Model();
		model.run();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getNum() {
		return num;
	}
	
	public LinkedList<Bullet> getBullets(){
		return bullets;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}

	public Stage getStage(){
		return stage;
	}
	
	public HpBar getHpBar() {
		return hpBar;
	}
	
	public Time getTime() {
		return time;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public int getCountTimeElapsed() {
	    return countTimeElapsed;
	}
	
	public Result getResult() {
		return result;
	}
	
	public Ranking getRanking() {
		return ranking;
	}
	

}
