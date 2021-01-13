package com.webpagesend;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
	public static final String confilFile = "config.txt";

	public static void main(String[] args) {
		//リソース読み込み
		Map<String, String> config = null;
		try {
			config = new ConfigReader(confilFile).read();
		}catch(IOException e) {
			System.out.println("設定ファイルの読み込みに失敗しました");
			return;
		}catch(IllegalStateException e) {
			System.out.println("設定ファイルの内容が正しくありません");
			return;
		}

		String excelPath = config.get("excel_path");
		String formURL = config.get("form_url");

		//エクセル読み込み
		List<TweetRecord> records = null;
		try {
			ExcelToRecordParser parser = new ExcelToRecordParser(excelPath);
			records = parser.createRecordList();
		}catch(Exception e) {
			System.out.println("エクセルの読み込みに失敗しました");
			e.printStackTrace();
			return;
		}

		//フォームに送信
		FormSender sender = new FormSender(formURL);
		sender.send(records, config);

	}
}