import java.io.IOException;

public class Menu {

	public final static int	MENUMAX = 4;
	public final static int MENU_BAR1_ID = 0;
	public final static int MENU_BAR2_ID = 1;
	public final static int MENU_BAR3_ID = 2;
	public final static int MENU_BAR4_ID = 3;
	
	private final static int MENU_BAR_X = 34;
	private final static int MENU_BAR1_Y = 15; 
	private final static int MENU_BAR2_Y = 17; 
	private final static int MENU_BAR3_Y = 19; 
	private final static int MENU_BAR4_Y = 21; 
	
	private final static int SELECT_BAR_X = 31;
	private final static int SELECT_BAR_Y = 15;
	
	private final static int HOWTOPLAY_BAR_X = 65;
	private final static int HOWTOPLAY_BAR1_Y = 19;
	private final static int HOWTOPLAY_BAR2_Y = 20;
	private final static int HOWTOPLAY_BAR3_Y = 21;
	private final static int HOWTOPLAY_BAR4_Y = 22;
	
	private final static int TITLE_X = 25;
	private final static int TITLE_Y = 5;
	
	private int chooseID;
	private boolean inMenu;
	private boolean inHome;
	private boolean inLevelSelect;
	private boolean inRanking;
	
	private Ranking ranking;
		
	public Menu(Ranking ranking) {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
		chooseID = 0;
		inMenu = true;
		inHome = true;
		inLevelSelect = false;
		inRanking = false;
		this.ranking = ranking;
	}
	
	public void up() {
		chooseID = (chooseID - 1 + MENUMAX) % MENUMAX;
	}
	
	public void down() {
		chooseID = (chooseID + 1) % MENUMAX;
	}
	
	
	public void exitMenu() {
		inMenu = false;
	}
	
	public void goMenu() {
		inMenu = true;
		inHome = true;
		inLevelSelect = false;
		chooseID = 0;
	}
	
	public void goLevelSelect() {
		inLevelSelect = true;
		inHome = false;
		inRanking = false;
		chooseID = 0;
	}
	
	public void goHome() {
		inLevelSelect = false;
		inHome = true;
		inRanking = false;
		chooseID = 0;
	}
	
	public void goRanking() {
		inLevelSelect = false;
		inHome = false;
		inRanking = true;
		chooseID = 0;
	}
	
	public boolean getInLevelSelect() {
		return inLevelSelect;
	}
	
	public boolean getInMenu() {
		return inMenu;
	}
	
	public boolean getInHome() {
		return inHome;
	}
	
	public int getChooseID() {
		return chooseID;
	}

	public boolean getInRanking() {
		return inRanking;
	}
	
	public void paint(ConsoleView view) {
		//外枠を描画
        for(int j = 0; j < ConsoleView.HEIGHT; j++) {
			for(int i = 0; i < ConsoleView.WIDTH; i++) {
                if(i == 0 ||j == 0 || i == ConsoleView.WIDTH - 1 || j == ConsoleView.HEIGHT - 1)
				    view.put('#',i,j);
			}
        }
        
        

        view.drawString("f:decide", HOWTOPLAY_BAR_X, HOWTOPLAY_BAR1_Y);
        view.drawString("w:Move up", HOWTOPLAY_BAR_X, HOWTOPLAY_BAR2_Y);
        view.drawString("s:Move down", HOWTOPLAY_BAR_X, HOWTOPLAY_BAR3_Y);
        view.drawString("q:Exit", HOWTOPLAY_BAR_X, HOWTOPLAY_BAR4_Y);
       
        //メニュー画面でのそれぞれ描画するもの
        if(inLevelSelect){
           view.drawString("level 1", MENU_BAR_X, MENU_BAR1_Y);
           view.drawString("level 2", MENU_BAR_X, MENU_BAR2_Y);
           view.drawString("level 3", MENU_BAR_X, MENU_BAR3_Y);
           view.drawString("level 4", MENU_BAR_X, MENU_BAR4_Y);
           	
           view.drawString("->", SELECT_BAR_X, SELECT_BAR_Y + chooseID * 2);
         }
        else if(inHome) {
        	view.drawString("PVE", MENU_BAR_X, MENU_BAR1_Y);
        	view.drawString("PVP", MENU_BAR_X, MENU_BAR2_Y);
        	view.drawString("TRAINING", MENU_BAR_X, MENU_BAR3_Y);
        	view.drawString("RANKING", MENU_BAR_X, MENU_BAR4_Y);
        	
            try {
       			view.drawText("title.txt", TITLE_X, TITLE_Y);
       		} catch (IOException e) {
       			e.printStackTrace();
       		}
        	
        	
        	view.drawString("->", SELECT_BAR_X, SELECT_BAR_Y + chooseID * 2);
        }
       else if(inRanking) {
        	ranking.showRanking(view);
        }
           
       
        
 
        
	}
	

	
}
