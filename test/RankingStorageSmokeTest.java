import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class RankingStorageSmokeTest {
    public static void main(String[] args) throws Exception {
        // 実際の記録を変更せず、一時ファイルで保存と再読み込みを確認します。
        Path directory = Files.createTempDirectory("kendo ranking ");
        Path file = directory.resolve("ranking.txt");
        try {
            Files.copy(Path.of("data", "ranking.txt"), file);
            Ranking ranking = new Ranking(file);
            ranking.writeRanking("剣道テスト", 999);
            Ranking reloaded = new Ranking(file);
            reloaded.writeRankingText();
            var lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            if (lines.size() != 10 || !lines.get(0).equals("剣道テスト")
                    || !lines.get(1).equals("999")) {
                throw new AssertionError("Ranking save/reload failed");
            }
            System.out.println("PASS: ranking save/reload with UTF-8 and spaces in path");
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(directory);
        }
    }
}
