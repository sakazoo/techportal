## 概要
Excellに記載されている文章を読み取り、類似文章を提示することを目的として作成。

自然言語処置の一般的な手法である「tf-idf」と「Cos類似度」の組み合わせを用いて検証を行っている。

詳細はこちら→https://dev.classmethod.jp/machine-learning/yoshim_2017ad_tfidf_1-2/

## アプリ説明
`DisplayRelationalTicketMain.java`

CSVファイルからチケット情報を読み込み類似度が高い組み合わせの一覧を表示する。

`MakeTfidfVectorCsv.java`

TF-IDFの結果をCSVファイルに書き込む。

`RelationalTicketSearchMain.java`

入力された内容に類似するチケットを提示する。第一引数に相談内容を入力する必要がある。

## 注意点
MeCabをJNIで実行しているため、`NLPSample/techportal/dll`内の`libmecab.dll`ファイルを
`C:\Windows\System32`配下に置くか、環境変数にパスを通す必要あり。
