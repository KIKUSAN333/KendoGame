import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class Ranking {
	private final Path rankingPath;

	private final static int COL1 = 18;
	private final static int COL2 = 19;
	private final static int COL3 = 20;
	private final static int ROW1 = 3;
	
	private final static int ANNOUNCEMENT_BAR_X = 31;
	private final static int ANNOUNCEMENT_BAR_Y = 8;
	private final static int NAME_POS_X = 32;
	private final static int NAME_POS_Y = 10;
	
	private final static int RANKING_NAME_X = 19;
	private final static int RANKING_SCORE_X = 40;
	private final static int RANKING_Y = 6;
	
	private final static int MAX_NAME = 20;
	
	private String names[];
	private int scores[];
	
	private String name;
	
	private boolean isAcceptingInput;
	
	
	public Ranking() {
		this(Path.of("data", "ranking.txt"));
	}

	Ranking(Path rankingPath) {
		this.rankingPath = rankingPath;
		names = new String[5];
		scores = new int[5];
		isAcceptingInput = false;
		name ="";
		
		try {
			readRankingText();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getIsAcceptingInput() {
		return isAcceptingInput;
	}
	
	public void accecptingInput() {
		isAcceptingInput = true;
	}

	
	public void enterName(String s,int time) {
		
		//入力を受け付けないなら
		if(!isAcceptingInput) {
			return;
		}
		
		if(s.equals(" ")){//スペースで決定
			isAcceptingInput = false;
			try {
				writeRanking(name,time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(s.equals("!")) {//!で文字をクリア
			name = "";
		}
		else if(s.equals("")) {
		    if (!name.isEmpty()) 
		    	name = name.substring(0, name.length() - 1); // 最後の1文字を削除
		}
		else if(name.length() >=  MAX_NAME) {//文字数が多いと入力を受け付けない
			
		}
		else if(!s.equals("TIME_ELAPSED")){
			name += s;		
		}
		
	}
	

	
	public void showInput(ConsoleView view) {
		if(isAcceptingInput) {
			view.drawFramedString(" Eneter your name ", '#',ANNOUNCEMENT_BAR_X, ANNOUNCEMENT_BAR_Y);
			view.drawString(":", NAME_POS_X - 1, NAME_POS_Y);
			view.drawString(name, NAME_POS_X, NAME_POS_Y);
			view.drawString("key : character input", ROW1, COL1);
			view.drawString("Space : Finish", ROW1, COL2);
			view.drawString("! : Clear", ROW1, COL3);
		}
	}
	

	public void readRankingText()throws Exception{
		// 保存データは素材とは別のファイルから読み込みます。
		try (BufferedReader reader = Files.newBufferedReader(rankingPath, StandardCharsets.UTF_8)) {
			String line;
			int count = 0;
			while (count < names.length && (line = reader.readLine()) != null) {
				names[count] = line;
				if ((line = reader.readLine()) != null) {
					scores[count] = Integer.parseInt(line);
				}
				count++;
			}
		}
	}
	
	
	public void writeRankingText() throws Exception{
        try (BufferedWriter out = Files.newBufferedWriter(rankingPath, StandardCharsets.UTF_8)) {
            for (int i = 0; i < names.length; i++) {
                out.write(names[i]);
                out.newLine();
                out.write(Integer.toString(scores[i]));
                out.newLine();
            }
        }
	}
	
	public void writeRanking(String newName, int newScore) throws Exception {
	    //配列を拡張して新しいデータを追加
	    int size = names.length + 1;
	    String[] tempNames = new String[size];
	    int[] tempScores = new int[size];

	    //既存のランキングをコピー
	    for (int i = 0; i < names.length; i++) {
	        tempNames[i] = names[i];
	        tempScores[i] = scores[i];
	    }

	    // 新しいデータを追加
	    tempNames[size - 1] = newName;
	    tempScores[size - 1] = newScore;

	    //挿入ソートでスコア降順に並び替え
	    for (int i = 1; i < size; i++) {
	        String keyName = tempNames[i];
	        int keyScore = tempScores[i];
	        int j = i - 1;

	        //スコアが小さいものを後ろにずらす（降順なので keyScore > tempScores[j] で入れ替え）
	        while (j >= 0 && tempScores[j] < keyScore) {
	            tempScores[j + 1] = tempScores[j];
	            tempNames[j + 1] = tempNames[j];
	            j--;
	        }
	        tempScores[j + 1] = keyScore;
	        tempNames[j + 1] = keyName;
	    }

	    //上位5件だけを元の配列にコピー
	    for (int i = 0; i < 5; i++) {
	        names[i] = tempNames[i];
	        scores[i] = tempScores[i];
	    }
	    
	    writeRankingText();

	}
	
	
	public void showRanking(ConsoleView view) {
		
		for(int i = 0;i < 5;i++) {
			view.drawString(names[i], RANKING_NAME_X, RANKING_Y + i * 3 );
			
			String scoreString = Integer.toString(scores[i]);
				
			view.drawString(scoreString, RANKING_SCORE_X, RANKING_Y + i * 3);
			view.drawString("sec", RANKING_SCORE_X + 3, RANKING_Y + i * 3);
		}
		
	}

}
