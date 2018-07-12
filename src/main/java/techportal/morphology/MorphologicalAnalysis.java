package techportal.morphology;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.impl.StandardTagger;
import techportal.csv.TicketCsv;

/**
 * チケットに記載されている文章の形態素解析を行う
 *
 * @author sakazoo
 */
public class MorphologicalAnalysis {

    /**
     * 形態素解析エンジンであるMecabを用いて相談内容を単語リストを取得
     *
     * @param ticketList チケットリスト
     */
    public static void makeTicketTermList(List<TicketCsv> ticketList) {
        for (TicketCsv ticket : ticketList) {
            parseTerms(ticket);
        }
    }

    public static void parseTerms(TicketCsv ticket) {
        // Lattice（形態素解析に必要な実行時情報が格納されるオブジェクト）を構築
        StandardTagger tagger = new StandardTagger("");
        Lattice lattice = tagger.createLattice();

        // 解析対象文字列をセット
        String text = ticket.getQuestion();
        lattice.setSentence(text);

        // tagger.parse()を呼び出して、文字列を形態素解析する。
        tagger.parse(lattice);

        // 形態素解析結果を出力
        // 一つずつ形態素をたどりながら、表層形と素性を出力
        List<String> termList = new ArrayList<>();
        Node node = lattice.bosNode();
        while (node != null) {
            String surface = node.surface();
            String feature = node.feature();
            if (StringUtils.startsWith(feature, "名詞") || StringUtils.startsWith(feature, "形容詞")) {
                termList.add(surface);
            }
            node = node.next();
        }
        ticket.setTermList(termList);

        // lattice, taggerを破壊
        lattice.destroy();
        tagger.destroy();
    }
}
