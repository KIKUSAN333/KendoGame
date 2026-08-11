import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TimeTest {

	@Test
	void 時間切れかどうか判定する() {
		Time time = new Time();
		
		//はじめはまだ時間が残っている
		assertEquals(false,time.isTimeEnd());
		
		for(int i = 0;i < 99;i++) {
			//まだ時間が残っている
			assertEquals(false,time.isTimeEnd());
			//１秒経過
			time.oneSecondHasPassed();

		}
		
		//時間切れ
		assertEquals(true,time.isTimeEnd());
	}

}
