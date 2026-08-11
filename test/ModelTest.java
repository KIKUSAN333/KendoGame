import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void process_menu操作でメニュー選択が変わるか() {
        Model model = new Model();
        Menu menu = model.getMenu();

        //初期選択は0
        assertEquals(0, menu.getChooseID());
        //メニュー画面にいる
        assertTrue(menu.getInMenu());

        //下の選択肢に移動する
        model.process("s");
        //下の選択を選んでいる
        assertEquals(1, menu.getChooseID());

        //上の選択肢に移動する
        model.process("w");
        //上の選択肢を選んでいるか
        assertEquals(0, menu.getChooseID());

        //今選んでいる選択肢を決定する
        model.process("f");
        //レベル選択画面に遷移しているかどうか
        assertEquals(true,menu.getInLevelSelect());
        
        //１つ前に戻る
        model.process("q");
        assertEquals(true,menu.getInHome());
        
        //上の選択肢に移動する
        model.process("w");
        model.process("w");
        
        //今選んでいる選択肢を決定する
        model.process("f");
        assertEquals(true,menu.getInRanking());
    }

    @Test
    void process_TIME_ELAPSEDでcountTimeElapsedが正しく動くか() {
        Model model = new Model();
        Menu menu = model.getMenu();

        //メニュー終了させる
        while(menu.getInMenu()) {
            model.process("f"); //強制的にSTARTを選択してメニュー終了
        }

        //countTimeElapsedは0スタート
        assertEquals(0, model.getCountTimeElapsed());

        for(int i = 1; i <= 19; i++) {
            model.process("TIME_ELAPSED");
            assertEquals(i, model.getCountTimeElapsed());
        }

        //20回目でリセットされる
        model.process("TIME_ELAPSED");
        assertEquals(0, model.getCountTimeElapsed());
    }


    @Test
    void process_playerEnemyのモーションや行動が動くか() {
        Model model = new Model();
        Menu menu = model.getMenu();

        //メニュー終了させる
        while(menu.getInMenu()) {
            model.process("f");
        }

        //最初のフレーム
        int iniFrame = model.getNum();

        //TIME_ELAPSED でモーションや行動が処理される
        model.process("TIME_ELAPSED");

        assertTrue(model.getNum() > iniFrame);

        //プレイヤーにモーション中でなければ移動入力で動くか
        model.process("a");  // 左に移動指示

        //フレームが増えているか
        assertTrue(model.getNum() > iniFrame);
    }
}