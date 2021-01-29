package com.webpagesend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

		final String excelDir = config.get("excel_directory");

		//指定ディレクトリからエクセルファイルのリストを取得
		List<String> excelFiles = null;
		try {
			excelFiles = Files.list(Paths.get(excelDir))
					.map(f -> f.toAbsolutePath())
					.map(f -> f.toString())
					.filter(f -> f.endsWith(".xlsx"))
					.collect(Collectors.toList());
		} catch (IOException e1) {
			System.out.println("エクセルの読み込みに失敗しました");
			return;
		}

		//エクセル読み込み
		List<TweetRecord> records = new ArrayList<TweetRecord>();
		try {
			for(String path:excelFiles) {
				ExcelToRecordParser parser = new ExcelToRecordParser(path);
				records.addAll(parser.createRecordList());
			}
		}catch(Exception e) {
			System.out.println("エクセルの読み込みに失敗しました");
			return;
		}

		//フォームに送信
		final String formURL = config.get("form_url");
		FormSender sender = new FormSender(formURL);
		try {
			sender.send(records, config);
		}catch(Exception e) {
			System.out.println("送信に失敗しました");
			//e.printStackTrace();
			return;
		}

		//エクセルファイルを別の場所にコピー
		final String copyDir = config.get("backup_directory");
		Path targetDirPath = Paths.get(copyDir);
		try {
			for(String path:excelFiles) {
				Path sourcePath = Paths.get(path);
				Path targetPath = targetDirPath.resolve(sourcePath.getFileName());
				Files.move(sourcePath, targetPath);
			}
		}catch (IOException e) {
			System.out.println("エクセルファイルのバックアップ中にエラーが発生しました");
			e.printStackTrace();
		}
	}
}