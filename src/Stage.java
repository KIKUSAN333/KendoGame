public class Stage{

	private final static int COL1 = 18;
	private final static int COL2 = 19;
	private final static int COL3 = 20;
	private final static int ROW1 = 3;
	private final static int ROW2 = 20;
	private final static int ROW3 = 37;
	private final static int ROW4 = 54;
	private final static int ROW5 = 70;
	
	
	private boolean isTraining;
	private boolean isPvP;

    public Stage(boolean isTraining,boolean isPvP){
        super();
        this.isTraining = isTraining;
        this.isPvP = isPvP;
    }

    public void paint(ConsoleView view){
    	//外枠を描画
        for(int j = 0; j < ConsoleView.HEIGHT; j++) {
			for(int i = 0; i < ConsoleView.WIDTH; i++) {
                if(i == 0 ||j == 0 || i == ConsoleView.WIDTH - 1 || j == ConsoleView.HEIGHT - 1)
				    view.put('#',i,j);
			}
		}
        
        //足場を描画
        for(int i = 1;i < ConsoleView.WIDTH - 1;i++) {
        	view.put('-',i,17);
        }
        
    }
    
    public void showHowToPlayEnemy(ConsoleView view) {
    	view.drawString("1:move left", ROW3, COL1);
    	view.drawString("3:move right", ROW3, COL2);
    	view.drawString("2:defense", ROW3, COL3);
    	
    	view.drawString("7:Men strike", ROW4, COL1);
    	view.drawString("8:Thrust", ROW4, COL2);
    	view.drawString("9:Kote strike", ROW4, COL3);
    	
    	if(!isPvP) {
    	view.drawString("q:Exit",ROW5, COL3);
    	}
    }
    
    public void showHowToPlay(ConsoleView view) {
    	view.drawString("a:move left", ROW1, COL1);
    	view.drawString("d:move right", ROW1, COL2);
    	view.drawString("s:defense", ROW1, COL3);
    	
    	view.drawString("j:Men strike", ROW2, COL1);
    	view.drawString("k:Thrust", ROW2, COL2);
    	view.drawString("l:Kote strike", ROW2, COL3);
    	
    	if(isTraining) {
    		showHowToPlayEnemy(view);
    	}
    }

    public Stage getStage(){
        return this;
    }
    
    public boolean getIsTraining() {
    	return isTraining;
    }
    
    public boolean getIsPvP(){
    	return isPvP;
    }

}