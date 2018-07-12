package techportal.compare;

import java.util.Comparator;

import techportal.util.ResultBean;

/**
 * 結果オブジェクトの比較クラス
 *
 * @author sakazoo
 */
public class ResultComp implements Comparator<ResultBean> {
    @Override
    public int compare(ResultBean bean1, ResultBean bean2) {
        return Double.compare(bean2.getCosineSimilarity(), bean1.getCosineSimilarity());
    }

}
