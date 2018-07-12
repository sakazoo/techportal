package techportal;

import java.util.List;

import techportal.csv.CsvUtils;
import techportal.csv.TicketCsv;
import techportal.morphology.MorphologicalAnalysis;
import techportal.tfidf.Tfidf;
import techportal.util.TicketConverter;

/**
 * TF-IDFの結果をCSVファイルに書き込む.
 *
 * @author sakazoo
 */
public class MakeTfidfVectorCsv {

    /** 文章を格納しているフォルダの場所 */
    private static final String CSV_FILENAME = System.getProperty("user.dir") + "\\doc" + "\\issues_full.csv";
    private static final String CSV_FILENAME2 = System.getProperty("user.dir") + "\\doc" + "\\issues_full3.csv";

    public static void main(String[] args) {

        // CSV読み込み
        List<TicketCsv> ticketList = CsvUtils.getAllTickets(CSV_FILENAME);
        if (ticketList.isEmpty()) {
            System.out.println("CSVファイルに対象データが含まれていません。");
            System.exit(0);
        }

        // 形態素解析(チケット内の相談内容から単語リストを作成)
        MorphologicalAnalysis.makeTicketTermList(ticketList);

        // 全てのチケットから単語(名詞、形容詞)リスト作成
        List<String> allTermLIst = TicketConverter.getAllTermList(ticketList);

        // TF-IDFの計算(チケットに記載されている重要な単語を算出)
        Tfidf.tfIdfCalculator(allTermLIst, ticketList);

        CsvUtils.writeTfidfVector(CSV_FILENAME2, ticketList);
    }

}
