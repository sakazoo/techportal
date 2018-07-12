package techportal.csv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.supercsv.prefs.CsvPreference;

import com.github.mygreen.supercsv.io.CsvAnnotationBeanReader;
import com.github.mygreen.supercsv.io.CsvAnnotationBeanWriter;

/**
 * CSVファイルの入出力ユーティル
 *
 * @author sakazoo
 */
public class CsvUtils {

    /**
     * CSVファイルからすべての行を読み込んでリスト形式として取得する。
     *
     * @param fileName ファイル名
     * @return チケット情報が格納されたリスト
     */
    public static List<TicketCsv> getAllTickets(String fileName) {

        List<TicketCsv> ticketList = new ArrayList<>();
        try (CsvAnnotationBeanReader<TicketCsv> csvReader =
                new CsvAnnotationBeanReader<>(TicketCsv.class, Files.newBufferedReader(new File(fileName).toPath(), Charset.forName("Windows-31j")), CsvPreference.STANDARD_PREFERENCE);) {
            csvReader.getHeader(true);
            TicketCsv record = null;
            while ((record = csvReader.read()) != null) {
                if (StringUtils.isNotEmpty(record.getId()) && StringUtils.isNotEmpty(record.getQuestion())) {
                    // tfidf値があればカンマ区切りでリストに格納
                    if (StringUtils.isNotEmpty(record.getTfidfStr())) {
                        record.setTfidfList(convertTfidfVec(record.getTfidfStr()));
                    }
                    ticketList.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("次のファイルが読み込めません。：" + fileName);
            System.exit(1);
        }
        return ticketList;
    }

    public static void writeTfidfVector(String fileName, List<TicketCsv> ticketList) {

        try (CsvAnnotationBeanWriter<TicketCsv> csvWriter =
                new CsvAnnotationBeanWriter<>(TicketCsv.class, Files.newBufferedWriter(new File(fileName).toPath(), Charset.forName("Windows-31j")), CsvPreference.STANDARD_PREFERENCE);) {
            csvWriter.writeHeader();
            for (TicketCsv ticket : ticketList) {
                ticket.setTfidfStr(makeTfidfStr(ticket));
                csvWriter.write(ticket);
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Double> convertTfidfVec(String tfidfStr) {
        String[] tfidfArray = tfidfStr.split(",", 0);
        List<Double> tfidfList = new ArrayList<>();
        for (String tfidf : tfidfArray) {
            tfidfList.add(Double.parseDouble(tfidf));
        }
        return tfidfList;
    }

    private static String makeTfidfStr(TicketCsv ticket) {
        StringBuffer tfidfStr = new StringBuffer();
        for (Double tfidf : ticket.getTfidfList()) {
            if (0 < tfidfStr.length()) {
                tfidfStr.append(",");
            }
            tfidfStr.append(tfidf);
        }
        return tfidfStr.toString();
    }
}
