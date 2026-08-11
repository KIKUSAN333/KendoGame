import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EnemyTest {

	@Test
	void motionEndFrameで現在モーションが行われているか判定する() {
		
		Enemy enemy = new Enemy(0);
		int current = 0;
		//最初は何も攻撃モーションをしていない
		assertEquals(false,enemy.isMotionActive(0));
		
		//モーション開始
		enemy.attack11(current);
		assertEquals(true,enemy.isMotionActive(current));
		
		current += 3;
		assertEquals(true,enemy.isMotionActive(current));
		
		//モーション終了後
		current += 1;
		assertEquals(false,enemy.isMotionActive(current));
	}

}
