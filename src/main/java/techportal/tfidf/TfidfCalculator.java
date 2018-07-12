/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package techportal.tfidf;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import techportal.csv.TicketCsv;

/**
 * tf-idf計算クラス
 *
 * @author sakazoo
 */
public class TfidfCalculator {

    /**
     * tfを算出する
     *
     * @param termList 単語リスト
     * @param targetTerm 対象の単語
     * @return tf値
     */
    public static double calcTf(List<String> termList, String targetTerm) {
        double count = 0;
        for (String term : termList) {
            if (StringUtils.equals(term, targetTerm)) {
                count++;
            }
        }
        return count / termList.size();
    }

    /**
     * idfを算出する
     *
     * @param ticketList 全チケット情報
     * @param targetTerm 対象の単語
     * @return idf値
     */
    public static double calcIdf(List<TicketCsv> ticketList, String targetTerm) {
        double count = 0;
        for (TicketCsv ticket : ticketList) {
            for (String term : ticket.getTermList()) {
                if (StringUtils.equals(term, targetTerm)) {
                    count++;
                    break;
                }
            }
        }
        // 全ドキュメントに存在しない単語の場合は重要度を下げるため0とする
        if(count == 0){
            return 0;
        }else{
            return Math.log(ticketList.size() / count);
        }
    }
}
