import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MenuTest {

	@Test
	void menuで正しく選択できるか() {
		Ranking ranking = new Ranking();
		Menu menu = new Menu(ranking);
		
		//初期は一番上を選択している
		assertEquals(0, menu.getChooseID());
		
		//上を入力するので一番下を選択する
		menu.up();
		assertEquals(3,menu.getChooseID());
		
		//下を入力するので一番上を選択する
		menu.down();
		assertEquals(0, menu.getChooseID());
		
		//更に下を入力するので上から２番めを選択する
		menu.down();
		assertEquals(1, menu.getChooseID());
		
	}
	
	@Test
	void menuで決定をすることができるか() {
		Ranking ranking = new Ranking();
		Menu menu = new Menu(ranking);
		
		//初期は一番上を選択している
		assertEquals(0, menu.getChooseID());
		
		//初期はメニュー画面にいる
		assertEquals(true, menu.getInMenu());
		
		//1番めを選択してみる
		menu.goLevelSelect();
		assertEquals(true, menu.getInMenu());
		
		
		
		//STARTを選択する
		menu.up();
		//メニュー画面ではなくなっている
		assertEquals(false, menu.getInMenu());
	}

}
