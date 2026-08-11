import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void motionEndFrameで現在モーションが行われているか判定する() {
		
		Player player = new Player();
		int current = 0;
		//最初は何も攻撃モーションをしていない
		assertEquals(false,player.isMotionActive(0));
		
		//モーション開始
		player.attack11(current);
		assertEquals(true,player.isMotionActive(current));
		
		current += 3;
		assertEquals(true,player.isMotionActive(current));
		
		//モーション終了後
		current += 1;
		assertEquals(false,player.isMotionActive(current));
	}

}
