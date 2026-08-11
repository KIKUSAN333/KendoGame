import java.util.Random;

public class Enemy {

	private Character character;
	private Player player;
	private int preActionID;
	private int level;
	private int initialMoveCount; 
	
	public Enemy(int level) {
		super();
		this.character = new Character(ConsoleView.WIDTH - Character.WIDTH - 1,10);
		this.idle();
		preActionID = 0;
		initialMoveCount = 0;
		
		this.level = level;
	}
	
	//何かしらの行動を行う
	public void doAction(int current) {
		
	    if (initialMoveCount < 20) {
	        moveLeft();
	        initialMoveCount++;
	        return; // ここで return して以降の処理をスキップ
	    }
		
		Random random = new Random();
		int actionID = 12;
		
		if(level == 1) {
			actionID  = random.nextInt(200);
		}
		else if(level == 2) {
			actionID = random.nextInt(100);// 0〜99の乱数
		}
		else if(level == 3) {
			actionID = random.nextInt(30);
		}
		else if(level == 4) {
			actionID = random.nextInt(10);
			
			if(preActionID == 0) {
				defense(current);
				actionID = 8;
			}
			else if(preActionID == 8 || preActionID == 1) {
				attack11(current);
			}
			else if(actionID  == 0 || actionID == 1|| actionID == 2){
				moveLeft();
			}
			else if(actionID == 8) {
				moveRight();
			}
			else if(actionID == 3 || actionID == 4) {
				attack11(current);
			}
			else if(actionID == 5) {
				attack21(current);
			}
			else if(actionID == 6) {
				attack31(current);
			}
			else if(actionID == 7) {
				defense(current);
			}
			
			preActionID = actionID;
			return;
			
		}
		
		
		if(actionID % 10 == 0 || actionID % 10 == 9){
			moveLeft();
		}
		else if( actionID % 10 == 8) {
			moveRight();
		}
		else if(actionID == 2) {
			attack11(current);
		}
		else if(actionID == 3) {
			attack21(current);
		}
		else if(actionID == 4) {
			attack31(current);
		}
		else if(actionID == 5) {
			defense(current);
		}
		
	}
	
	
	public void moveLeft(){
		if(player.getX() < character.getX())
			character.moveLeft();
	}

	public void moveRight(){
		if(player.getX() <= character.getX())
			character.moveRight();
	}
	
	public void takeDamage(int damage) {
		character.takeDamage(damage);
	}

	
	public void idle() {
		character.idle("EIdle.txt");
	}
	
	public void attack11(int current){
		character.doMotion(Character.ATTACK11_ID,"EAttack11.txt", current,Character.ATTACK11_FRAME);
	}
	
	public void attack12(int current){
		character.doMotion(Character.ATTACK12_ID,"EAttack12.txt", current,Character.ATTACK12_FRAME);
	}
	
	public void attack13(int current){
		character.doMotion(Character.ATTACK13_ID,"EAttack13.txt", current,Character.ATTACK13_FRAME);
		if(this.isHit(Character.ATTACK_REACH_LONG)) {
			//相手がガードしているとき
			if(player.getMotionID() == Character.DEFENSE_ID) {
				this.beOpen(current);
				return;
			}//相手が小攻撃を行っている場合
			else if(player.getMotionID() == Character.ATTACK31_ID || player.getMotionID() == Character.ATTACK32_ID) {
				player.takeDamage(Character.DAMAGE_VERY_HIGH);
				player.takeDamage91(current);
			}
			else {
				player.takeDamage(Character.DAMAGE_HIGH);
				player.takeDamage91(current);
			}
		}
	}
	
	public void attack21(int current){
		character.doMotion(Character.ATTACK21_ID,"EAttack21.txt", current,Character.ATTACK21_FRAME);
	}
	
	public void attack22(int current){
		character.doMotion(Character.ATTACK22_ID,"EAttack22.txt", current,Character.ATTACK22_FRAME);
		if(this.isHit(Character.ATTACK_REACH_MEDIUM)) {
			player.takeDamage(Character.DAMAGE_MEDIUM);
			player.takeDamage91(current);
		}
	}
	
	public void attack31(int current) {
		character.doMotion(Character.ATTACK31_ID,"EAttack21.txt", current,Character.ATTACK31_FRAME);
	}
	
	public void attack32(int current) {
		character.doMotion(Character.ATTACK32_ID,"EAttack32.txt", current,Character.ATTACK32_FRAME);
		if(this.isHit(Character.ATTACK_REACH_SHORT)) {
			//相手がガードしているとき
			if(player.getMotionID() == Character.DEFENSE_ID) {
				player.takeDamage(Character.DAMAGE_VERY_LOW);
				return;
			}//相手が中攻撃を行っている場合
			else if(player.getMotionID() == Character.ATTACK21_ID || player.getMotionID() == Character.ATTACK22_ID) {
				player.takeDamage(Character.DAMAGE_HIGH);
				player.takeDamage91(current);
			}
			else {
				player.takeDamage(Character.DAMAGE_LOW);
				player.takeDamage91(current);
			}
		}
	}
	
	public void defense(int current) {
		character.doMotion(Character.DEFENSE_ID, "EDfense.txt", current, Character.DEFENSE_FRAME);
	}
	
	public void takeDamage91(int current) {
		character.doMotion(Character.TAKEDAMAGE91_ID,"EDamage.txt", current, Character.TAKEDAMAGE91_FRAME);
		character.moveRight();
	}
	public void takeDamage92(int current) {
		character.doMotion(Character.TAKEDAMAGE92_ID,"EDamage.txt", current, Character.TAKEDAMAGE92_FRAME);
		character.moveRight();
	}
	public void takeDamage93(int current) {
		character.doMotion(Character.TAKEDAMAGE93_ID,"EDamage.txt", current, Character.TAKEDAMAGE93_FRAME);
	}
	
	public void beOpen(int current) {
		character.doMotion(Character.BEOPEN_ID,"EBeOpen.txt", current, Character.BEOPEN_FRAME);
	}
	
	public void setPlayer(Player pplayer) {
		this.player = pplayer;
	}
	
	//次のモーションに移行する
	public void doNextMotions(int current) {
		//念のため
	    if (!isMotionActive(current)) {
	        idle();
	    }
	    
	    if(canDoMotion(Character.TAKEDAMAGE91_ID,current)) {
	    	takeDamage92(current);
	    }
	    else if(canDoMotion(Character.TAKEDAMAGE92_ID,current)) {
	    	takeDamage93(current);
	    }
	    else if(canDoMotion(Character.TAKEDAMAGE93_ID,current)) {
	    	idle();
	    }
		
		if(canDoMotion(Character.ATTACK11_ID,current)) {
			attack12(current);
		}
		else if(canDoMotion(Character.ATTACK12_ID,current)) {
			attack13(current);
		}
		else if(canDoMotion(Character.ATTACK13_ID,current)) {
			idle();
		}
		
	    if(canDoMotion(Character.ATTACK21_ID, current)) {
	    	attack22(current);
	    }
		else if(canDoMotion(Character.ATTACK22_ID,current)) {
			idle();
		}
	    
	    if(canDoMotion(Character.ATTACK31_ID,current)) {
	    	attack32(current);
	    }
	    else if(canDoMotion(Character.ATTACK32_ID,current)) {
	    	idle();
	    }
	    

	    
	}
	
	public boolean isHit(int attackDistance) {
		return character.isHit(player.getX(), attackDistance);
	}
	
	
	public void paint(ConsoleView view) {
		character.paint(view);
	}
	
	
	public boolean isMotionActive(int current) {
		return character.isMotionActive(current);
	}
	
	
	public boolean canDoMotion(int currentMotionID,int current) {
		return character.canDoMotion(currentMotionID,current);
	}
	
	public int getX() {
		return character.getX();
	}
	
	public int getHp() {
		return character.getHp();
	}
	 
	public int getMotionID() {
		return character.getMotionID();
	}
	
}