package techportal.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import techportal.csv.TicketCsv;

/**
 * チケット情報の編集を行う
 *
 * @author sakazoo
 */
public class TicketConverter {

    /**
     * 全てのチケットに登場する単語リストを取得する
     *
     * @param ticketList チケットリスト
     * @return 全チケットに登場する単語リスト
     */
    public static List<String> getAllTermList(List<TicketCsv> ticketList) {

        List<String> allTermList = new ArrayList<>();
        for (TicketCsv ticket : ticketList) {
            for (String term : ticket.getTermList()) {
                // appleとAppleのように同じ意味の英単語は大文字APPLEにそろえる
                String upperTerm = StringUtils.upperCase(term);
                if (allTermList.contains(upperTerm) == false && isValidWord(term)) {
                    allTermList.add(upperTerm);
                }
            }
        }
        return allTermList;
    }

    /**
     * 重要な単語としてリスト追加するかを判定する
     *
     * @param term
     * @return
     */
    private static boolean isValidWord(String term) {
        boolean flag = false;
        if (StringUtils.length(term) == 1) {
            return flag;
        } else if ((term.matches("^[a-zA-Z]+") || term.matches("^[一-龥ぁ-ん]+")) == false) {
            return flag;
        } else {
            return true;
        }
    }

}
