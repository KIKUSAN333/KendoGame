import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CharacterTest {

	@Test
	void isMotionActiveでモーション中か判定する() {
		
		Character character = new Character(0,10);
		int current = 0;
		assertEquals(false,character.isMotionActive(current));

		//何もせずに10フレーム後
		current += 10;
		assertEquals(false,character.isMotionActive(current));

		//モーションを動作させる
		//Attack11は３フレームのモーション
		character.doMotion(11, "Attack11.txt",current,3);

		assertEquals(true,character.isMotionActive(current));

		//更に３フレーム後
		current += 3;
		assertEquals(true,character.isMotionActive(current));

		//更に1フレーム後 Attack11は３フレーム
		current += 1;
		assertEquals(false,character.isMotionActive(current));
	}
	
	@Test
	void canDoMotionでモーション終わりか判定する() {
		Character character = new Character(0,10);
		int current = 0;
		
		//モーションを動作させる
		//Attack11は３フレームのモーション
		character.doMotion(11, "Attack11.txt",current,3);
		
		//Attack11のモーション中なので次のモーションに移動できない
		assertEquals(false,character.canDoMotion(11, current));
		
		//Attack11の最後のフレーム
		current += 3;
		
		//Attack11の最後のフレームなので次のモーションに移行できる
		assertEquals(true,character.canDoMotion(11, current));
		
		//Attack11の次のモーションではないので次のモーションに移行できない
		assertEquals(false,character.canDoMotion(12, current));
	}
	
	@Test
	void canMoveLeftとcanMoveRightで移動できるか判定する() {
		Character player = new Character(1,10);
		Character enemy = new Character(79 - 14,10);
		
		//初期位置で画面外に出ないようにする
		assertEquals(false,player.canMoveLeft());
		assertEquals(true,player.canMoveRight());
		
		assertEquals(false,enemy.canMoveRight());
		assertEquals(true,enemy.canMoveLeft());

	}
	
	@Test
	void isHitで攻撃が当たるか判定する() {
		Character player = new Character(1,10);
		Character enemy = new Character(79 - 14,10);
		int attackDistance = 10;
		
		//キャラクターはそれぞれ離れているので攻撃が当たらない
		assertEquals(false,player.isHit(enemy.getX(), attackDistance));
		assertEquals(false,enemy.isHit(player.getX(), attackDistance));
		
		//キャラクターが近づく
		for(int i = 0;i < 80;i++) {
			player.moveRight();
		}
		
		//キャラクターはそれぞれ近づいたので攻撃が当たる
		assertEquals(true,player.isHit(enemy.getX(), attackDistance));
		assertEquals(true,enemy.isHit(player.getX(), attackDistance));
		
		attackDistance = 0;
		
		//攻撃距離が0の場合は当たらない
		assertEquals(false,player.isHit(enemy.getX(), attackDistance));
		assertEquals(false,enemy.isHit(player.getX(), attackDistance));
		
	}

}
