import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RankingTest {

	@Test
	void ランキングに入力時に入力を受け付けるかどうか() {
		Ranking ranking = new Ranking();
		int score = 0;
		
		//最初は入力を受け付けないかどうか
		assertEquals(false,ranking.getIsAcceptingInput());
		
		//入力を受け付けるようにする
		ranking.accecptingInput();
		
		//入力を受け付けるかどうか
		assertEquals(true,ranking.getIsAcceptingInput());
		
		//名前を入力している
		ranking.enterName("JunitTestMan", score);
		
		//入力を受け付けるかどうか
		assertEquals(true,ranking.getIsAcceptingInput());
		
		//入力終了
		ranking.enterName(" ", score);
		
		//入力を受けつけないかどうか
		assertEquals(false,ranking.getIsAcceptingInput());
		
		
	}

}
