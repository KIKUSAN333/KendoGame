public class Player {

	private Character character;
	private Enemy enemy;
	
	public Player() {
		super();
		this.character = new Character(1,10);
		this.idle();
		
	}
	
	public void moveLeft(){
		if(character.getX() <= enemy.getX())
			character.moveLeft();
	}

	public void moveRight(){
		if(character.getX() < enemy.getX())
			character.moveRight();
	}
	
	
	public void takeDamage(int damage) {
		character.takeDamage(damage);
	}

	
	public void idle() {
		character.idle("Idle.txt");
	}
	
	public void attack11(int current){
		character.doMotion(Character.ATTACK11_ID,"Attack11.txt", current,Character.ATTACK11_FRAME);
	}
	
	public void attack12(int current){
		character.doMotion(Character.ATTACK12_ID,"Attack12.txt", current,Character.ATTACK12_FRAME);
	}
	
	public void attack13(int current){
		character.doMotion(Character.ATTACK13_ID,"Attack13.txt", current,Character.ATTACK13_FRAME);
		if(this.isHit(Character.ATTACK_REACH_LONG)) {
			//相手がガードしているとき
			if(enemy.getMotionID() == Character.DEFENSE_ID) {
				this.beOpen(current);
				return;
			}//相手が小攻撃を行っている場合
			else if(enemy.getMotionID() == Character.ATTACK31_ID || enemy.getMotionID() == Character.ATTACK32_ID) {
				enemy.takeDamage(Character.DAMAGE_VERY_HIGH);
				enemy.takeDamage91(current);
			}
			else {
				enemy.takeDamage(Character.DAMAGE_HIGH);
				enemy.takeDamage91(current);
			}
		}
	}
	
	public void attack21(int current){
		character.doMotion(Character.ATTACK21_ID,"Attack21.txt", current,Character.ATTACK21_FRAME);
	}
	
	public void attack22(int current){
		character.doMotion(Character.ATTACK22_ID,"Attack22.txt", current,Character.ATTACK22_FRAME);
		if(this.isHit(Character.ATTACK_REACH_MEDIUM)) {
			enemy.takeDamage(Character.DAMAGE_MEDIUM);
			enemy.takeDamage91(current);
		}
	}
	
	public void attack31(int current) {
		character.doMotion(Character.ATTACK31_ID,"Attack21.txt", current,Character.ATTACK31_FRAME);
	}
	
	public void attack32(int current) {
		character.doMotion(Character.ATTACK32_ID,"Attack32.txt", current,Character.ATTACK32_FRAME);
		if(this.isHit(Character.ATTACK_REACH_SHORT)) {
			//相手がガードしているとき
			if(enemy.getMotionID() == Character.DEFENSE_ID) {
				enemy.takeDamage(Character.DAMAGE_VERY_LOW);
				return;
			}//相手が中攻撃を行っている場合
			else if(enemy.getMotionID() == Character.ATTACK21_ID || enemy.getMotionID() == Character.ATTACK22_ID) {
				enemy.takeDamage(Character.DAMAGE_HIGH);
				enemy.takeDamage91(current);
			}
			else {
				enemy.takeDamage(Character.DAMAGE_LOW);
				enemy.takeDamage91(current);
			}
		}
	}
	
	public void defense(int current) {
		character.doMotion(Character.DEFENSE_ID, "Defense.txt", current, Character.DEFENSE_FRAME);
	}
	
	public void takeDamage91(int current) {
		character.doMotion(Character.TAKEDAMAGE91_ID,"Damage.txt", current, Character.TAKEDAMAGE91_FRAME);
		character.moveLeft();
	}
	public void takeDamage92(int current) {
		character.doMotion(Character.TAKEDAMAGE92_ID,"Damage.txt", current, Character.TAKEDAMAGE92_FRAME);
		character.moveLeft();
	}
	public void takeDamage93(int current) {
		character.doMotion(Character.TAKEDAMAGE93_ID,"Damage.txt", current, Character.TAKEDAMAGE93_FRAME);
	}
	
	public void beOpen(int current) {
		character.doMotion(Character.BEOPEN_ID,"BeOpen.txt",current,Character.BEOPEN_FRAME);
	}
	
	
	public void setEnemy(Enemy eenemy) {
		this.enemy = eenemy;
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
		return character.isHit(enemy.getX(), attackDistance);
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
