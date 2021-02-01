package com.webpagesend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//config.textから基本情報を読み込むクラス
public class ConfigReader {
	private String fileName;

	public ConfigReader(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> read() throws IOException, IllegalStateException{
		Map<String, String> map = new HashMap<String, String>();
		//パスを取得
		Path path = Paths.get(fileName);

		//ファイルを行ごとにリスト化
		List<String> list = Files.readAllLines(path);

		//リストが終わるまで
		for(String line : list) {
			//=で分割して文字列配列化
			String[] array = line.split("=");
			//文字列配列をMapに変換
			if(array.length == 2) {
				map.put(array[0], array[1]);
			}else if(array.length == 1) {
				map.put(array[0], "");
			}else {
				//フォーマットにない記述の場合
				throw new IllegalStateException();
			}
		}
		return map;
	}
}
