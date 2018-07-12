package techportal;

import java.util.List;

import techportal.cosine.CosineSimilarity;
import techportal.csv.CsvUtils;
import techportal.csv.TicketCsv;
import techportal.morphology.MorphologicalAnalysis;
import techportal.tfidf.Tfidf;
import techportal.util.Print;
import techportal.util.ResultBean;
import techportal.util.TicketConverter;

/**
 * 入力された内容に類似するチケットを提示する。<br>
 * 処理高速化のため入力値を含まない状態のtd-idfを利用する。
 *
 * @author sakazoo
 */
public class RelationalTicketSearchMain {

    /** 文章を格納しているフォルダの場所 */
    private static final String CSV_FILENAME = System.getProperty("user.dir") + "\\doc" + "\\issues_full2.csv";

    /**
     * 主処理
     *
     * @param args 相談内容を第1引数として渡す
     */
    public static void main(String[] args) {

        if(args.length != 1){
            System.err.println("第一引数に相談内容を入力して実行してください。");
            System.exit(1);
        }
        String input = args[0];
        long totalStartTime = System.currentTimeMillis();

        // 第1引数の情報をもとにチケットオブジェクトを作成する
        TicketCsv inputTicket = new TicketCsv();
        inputTicket.setQuestion(input);

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
        MorphologicalAnalysis.parseTerms(inputTicket);
        System.out.println("形態素解析 - 【終了】");

        // 入力されたチケットを除く全てのチケットから単語(名詞、形容詞)リスト作成
        List<String> allTermLIst = TicketConverter.getAllTermList(ticketList);

        // TF-IDFの計算(チケットに記載されている重要な単語を算出)
        calcTfidf(allTermLIst, ticketList, inputTicket);

        // Cos類似度の計算(値が1に近いほど類似したチケット)
        System.out.println("Cos類似度の計算 - 【開始】");
        List<ResultBean> resultList = CosineSimilarity.getSingleResultList(ticketList, inputTicket);
        System.out.println("Cos類似度の計算 - 【終了】");

        // 結果出力
        System.out.println("結果出力 - 【開始】\n");
        System.out.println("相談内容：\n  " + input);
        Print.printRelationalTicket(resultList);
        System.out.println("結果出力 - 【終了】");
        long totalEndTime = System.currentTimeMillis();
        System.out.println("処理時間：" + (totalEndTime - totalStartTime) / 1000 + "秒");
    }

    private static void calcTfidf(List<String> allTermLIst, List<TicketCsv> ticketList, TicketCsv inputTicket) {

        long tfidfStartTime = System.currentTimeMillis();
        System.out.println("TF-IDFの計算 - 【開始】");
        Tfidf.tfIdfSingleCalculator(allTermLIst, ticketList, inputTicket);
        System.out.println("TF-IDFの計算 - 【終了】");
        long tfidfEndTime = System.currentTimeMillis();
        System.out.println("TF-IDFの計算 - 処理時間：" + (tfidfEndTime - tfidfStartTime) / 1000 + "秒");
    }
}
