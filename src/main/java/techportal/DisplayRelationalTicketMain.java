package techportal;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import techportal.cosine.CosineSimilarity;
import techportal.csv.CsvUtils;
import techportal.csv.TicketCsv;
import techportal.morphology.MorphologicalAnalysis;
import techportal.tfidf.Tfidf;
import techportal.util.Print;
import techportal.util.ResultBean;
import techportal.util.TicketConverter;

/**
 * TechPortal内の内容が類似しているチケットを表示する<br>
 * 以下、用いた手法<br>
 * CSV入出力：supercsv<br>
 * 形態素解析：MeCab<br>
 * 特徴量：tf-idf<br>
 * 比較方法：Cos類似度
 *
 * @author sakazoo
 */
public class DisplayRelationalTicketMain {

    /** 文章を格納しているフォルダの場所 */
    private static final String CSV_FILENAME = System.getProperty("user.dir") + "\\doc" + "\\issues_full3.csv";

    public static void main(String[] args) {
        long totalStartTime = System.currentTimeMillis();
        // CSV読み込み
        System.out.println("CSV読み込み - 【開始】");
        List<TicketCsv> ticketList = CsvUtils.getAllTickets(CSV_FILENAME);
        if (ticketList.isEmpty()) {
            System.out.println("CSVファイルに対象データが含まれていません。");
            System.exit(0);
        }
        System.out.println("CSV読み込み - 【終了】");

        // 形態素解析(チケット内の相談内容から単語リストを作成)
        System.out.println("形態素解析 - 【開始】");
        MorphologicalAnalysis.makeTicketTermList(ticketList);
        System.out.println("形態素解析 - 【終了】");

        // tfidfの計算処理が重いため、既に算出している場合は処理させない
        if (hasTfidf(ticketList) == false) {
            // 全てのチケットから単語(名詞、形容詞)リスト作成
            List<String> allTermLIst = TicketConverter.getAllTermList(ticketList);
            // TF-IDFの計算(チケットに記載されている重要な単語を算出)
            calcTfidf(allTermLIst, ticketList);
        }

        // Cos類似度の計算(値が1に近いほど類似したチケット)
        System.out.println("Cos類似度の計算 - 【開始】");
        List<ResultBean> resultList = CosineSimilarity.getResultList(ticketList);
        System.out.println("Cos類似度の計算 - 【終了】");

        // 結果出力
        System.out.println("結果出力 - 【開始】");
        Print.printResultAll(resultList);
        System.out.println("結果出力 - 【終了】");
        long totalEndTime = System.currentTimeMillis();
        System.out.println("処理時間：" + (totalEndTime - totalStartTime) / 1000 + "秒");
    }

    private static boolean hasTfidf(List<TicketCsv> ticketList) {
        for (TicketCsv ticket : ticketList) {
            if (StringUtils.isEmpty(ticket.getTfidfStr())) {
                return false;
            }
        }
        return true;
    }

    private static void calcTfidf(List<String> allTermLIst, List<TicketCsv> ticketList) {

        long tfidfStartTime = System.currentTimeMillis();
        System.out.println("TF-IDFの計算 - 【開始】");
        Tfidf.tfIdfCalculator(allTermLIst, ticketList);
        System.out.println("TF-IDFの計算 - 【終了】");
        long tfidfEndTime = System.currentTimeMillis();
        System.out.println("TF-IDFの計算 - 処理時間：" + (tfidfEndTime - tfidfStartTime) / 1000 + "秒");
    }

}
