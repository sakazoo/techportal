package techportal.util;

/**
 * 出力情報Bean
 *
 * @author sakazoo
 */
public class ResultBean {

    /** 関連するチケットID */
    private String ticketId1;
    private String ticketId2;

    /** Cos類似度 */
    private double cosineSimilarity;

    public String getTicketId1() {
        return ticketId1;
    }

    public void setTicketId1(String ticket1) {
        this.ticketId1 = ticket1;
    }

    public String getTicketId2() {
        return ticketId2;
    }

    public void setTicketId2(String ticket2) {
        this.ticketId2 = ticket2;
    }

    public double getCosineSimilarity() {
        return cosineSimilarity;
    }

    public void setCosineSimilarity(double cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }

}
