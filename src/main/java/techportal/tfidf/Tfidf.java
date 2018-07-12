package techportal.tfidf;

import java.util.ArrayList;
import java.util.List;

import techportal.csv.TicketCsv;

/**
 * tf-idfを算出するクラス<br>
 * TfidfCalculatorクラスを呼び出す
 *
 * @author sakazoo
 */
public class Tfidf {

    /**
     * 各チケットに対してtfidfを求める
     *
     * @param allTermLIst 全てのチケットに含まれる単語リスト
     * @param ticketList チケットリスト
     */
    public static void tfIdfCalculator(List<String> allTermLIst, List<TicketCsv> ticketList) {

        for (TicketCsv ticket : ticketList) {
            System.out.println("　チケット" + ticket.getId() + " - 【計算開始】");
            List<Double> tfidfList = new ArrayList<>();
            for (String term : allTermLIst) {
                double tf = TfidfCalculator.calcTf(ticket.getTermList(), term);
                double idf = TfidfCalculator.calcIdf(ticketList, term);
                double tfidf = tf * idf;
                tfidfList.add(tfidf);
            }
            ticket.setTfidfList(tfidfList);
            System.out.println("　チケット" + ticket.getId() + " - 【計算終了】");
        }
    }

    /**
     * 入力チケットに対してtfidfを求める
     *
     * @param allTermLIst 全てのチケットに含まれる単語リスト
     * @param ticketList チケットリスト
     * @param inputTicket 入力チケット
     */
    public static void tfIdfSingleCalculator(List<String> allTermLIst, List<TicketCsv> ticketList, TicketCsv inputTicket) {

            List<Double> tfidfList = new ArrayList<>();
            for (String term : allTermLIst) {
                double tf = TfidfCalculator.calcTf(inputTicket.getTermList(), term);
                double idf = TfidfCalculator.calcIdf(ticketList, term);
                double tfidf = tf * idf;
                tfidfList.add(tfidf);
            }
            inputTicket.setTfidfList(tfidfList);
    }

}
