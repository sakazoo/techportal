package techportal.csv;

import java.util.ArrayList;
import java.util.List;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;

/**
 * CSVから取得した情報を設定するPOJO
 *
 * @author sakazoo
 */
@CsvBean(header = true)
public class TicketCsv {

    /** チケットID */
    @CsvColumn(number = 1)
    private String id;

    /** 相談内容 */
    @CsvColumn(number = 2, label = "相談内容・環境等")
    private String question;

    /** tf-idf値のカンマ区切り文字列 */
    @CsvColumn(number = 3, label = "tfidf")
    private String tfidfStr;

    /** チケットに含まれる全単語リスト(名詞、形容詞) */
    private List<String> termList = new ArrayList<>();

    /** tfidfリスト(サイズは全文章に含まれる単語数) */
    private List<Double> tfidfList = new ArrayList<>();

    public TicketCsv() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getTermList() {
        return termList;
    }

    public void setTermList(List<String> termList) {
        this.termList = termList;
    }

    public List<Double> getTfidfList() {
        return tfidfList;
    }

    public void setTfidfList(List<Double> tfidfList) {
        this.tfidfList = tfidfList;
    }

    public String getTfidfStr() {
        return tfidfStr;
    }

    public void setTfidfStr(String tfidfVec) {
        this.tfidfStr = tfidfVec;
    }
}
