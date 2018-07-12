package techportal.util;

import java.util.Collections;
import java.util.List;

import techportal.compare.ResultComp;

/**
 * 画面出力ユーティル
 *
 * @author sakazoo
 */
public class Print {

    /**
     * 類似度の高いチケットをすべて表示する
     *
     * @param resultList 類似度の高いチケット情報リスト
     */
    public static void printResultAll(List<ResultBean> resultList) {

        for (ResultBean result : resultList) {
            System.out.println("Ticket" + result.getTicketId1() + " and " + result.getTicketId2() + " : " + result.getCosineSimilarity());
        }
    }

    /**
     * 類似度の高いチケットをすべて表示する
     *
     * @param resultList 類似度の高いチケット情報リスト
     */
    public static void printRelationalTicket(List<ResultBean> resultList) {
        System.out.println("\n関連するチケットは以下の通り。");
        if (resultList.isEmpty()) {
            System.out.println("該当する関連Ticketが存在しません。");
        } else {
            Collections.sort(resultList, new ResultComp());
            for (ResultBean result : resultList) {
                System.out.println("  Ticket" + result.getTicketId2() + " : " + result.getCosineSimilarity());
            }
        }
        System.out.println();
    }

}
