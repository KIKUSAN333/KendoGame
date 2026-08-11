import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResultTest {

	@Test
	void リザルト画面の遷移できるか判定する() {
		int enemyLevel = 1;
		Player player = new Player();
		Enemy enemy = new Enemy(enemyLevel);
		player.setEnemy(enemy);
		enemy.setPlayer(player);
		
		Result result = new Result(player,enemy);
		
		//最初はリザルト画面ではない
		assertEquals(false,result.getIsResult());
		
		//プレイヤーとエネミーがダメージを受ける
		player.takeDamage(500);
		enemy.takeDamage(250);
		result.updateResult();
		
		//体力が残っているのでリザルト画面ではない
		assertEquals(false,result.getIsResult());
		
		//プレイヤーとエネミーそれぞれまだ体力が０以下になっていない
		assertEquals(false,result.isPlayerHpZero());
		assertEquals(false,result.isEnemyHpZero());
		
		//プレイヤーのHPが０になる
		player.takeDamage(1000);
		result.updateResult();
		
		//リザルト画面にいる
		assertEquals(true,result.getIsResult());
		
		//プレイヤーのHPは０以下になっている
		assertEquals(true,result.isPlayerHpZero());
		//エネミーのHPは０以下ではない
		assertEquals(false,result.isEnemyHpZero());
		
		//エネミーのHPも0以下になる
		enemy.takeDamage(1000);
		result.updateResult();
		
		//リザルト画面にいる
		assertEquals(true,result.getIsResult());
		
		//プレイヤーのHPは０以下になっている
		assertEquals(true,result.isPlayerHpZero());
		//エネミーのHPは０以下になっている
		assertEquals(true,result.isEnemyHpZero());
		
		
		
	}

}
