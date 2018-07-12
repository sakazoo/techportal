package techportal.cosine;

import java.util.ArrayList;
import java.util.List;

import techportal.csv.TicketCsv;
import techportal.util.ResultBean;

/**
 * Cos類似度算出のためのクラス
 *
 * @author sakazoo
 */
public class CosineSimilarity {

    /** ある程度高いCos類似度のデータのみ取得するために設けた閾値 */
    private static final double THRESHOLD_CosINE_SIMILARITY = 0.3;

    /**
     * 全てのチケット情報が持つtf-idfリストをもとに、Cos類似度を算出する<br>
     * 結果をもとに、関連するチケットID及びCos類似度を持った出力用リストを取得する
     *
     * @param ticketList 全てのチケット情報リスト
     * @return 出力情報リスト
     */
    public static List<ResultBean> getResultList(List<TicketCsv> ticketList) {
        List<ResultBean> resultList = new ArrayList<>();
        int size = ticketList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                TicketCsv ticket1 = ticketList.get(i);
                TicketCsv ticket2 = ticketList.get(j);
                double cosineSimilarity = calcCosineSimilarity(ticket1.getTfidfList(), ticket2.getTfidfList());
                if (THRESHOLD_CosINE_SIMILARITY < cosineSimilarity) {
                    ResultBean result = new ResultBean();
                    result.setTicketId1(ticket1.getId());
                    result.setTicketId2(ticket2.getId());
                    result.setCosineSimilarity(cosineSimilarity);
                    resultList.add(result);
                }
            }
        }
        return resultList;
    }

    /**
     * 入力チケット情報が持つtf-idfリストをもとに、Cos類似度を算出する<br>
     * 結果をもとに、関連するチケットID及びCos類似度を持った出力用リストを取得する
     *
     * @param ticketList 全てのチケット情報リスト
     * @param inputTicket 入力チケット情報
     * @return 出力情報リスト
     */
    public static List<ResultBean> getSingleResultList(List<TicketCsv> ticketList, TicketCsv inputTicket) {
        List<ResultBean> resultList = new ArrayList<>();
        for (TicketCsv ticket : ticketList) {
            double cosineSimilarity = calcCosineSimilarity(inputTicket.getTfidfList(), ticket.getTfidfList());
            if (THRESHOLD_CosINE_SIMILARITY < cosineSimilarity) {
                ResultBean result = new ResultBean();
                result.setTicketId1(inputTicket.getId());
                result.setTicketId2(ticket.getId());
                result.setCosineSimilarity(cosineSimilarity);
                resultList.add(result);
            }
        }

        return resultList;
    }

    private static double calcCosineSimilarity(List<Double> tfidfList1, List<Double> tfidfList2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        // tfidfList1とtfidfList2は同じ要素数であることが前提
        for (int i = 0; i < tfidfList1.size(); i++) {
            dotProduct += tfidfList1.get(i) * tfidfList2.get(i); // a.b
            magnitude1 += Math.pow(tfidfList1.get(i), 2); // (a^2)
            magnitude2 += Math.pow(tfidfList2.get(i), 2); // (b^2)
        }

        magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0;
        } else {
            return dotProduct / (magnitude1 * magnitude2);
        }
    }
}
