package com.webpagesend;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//エクセルをリスト化するクラス
public class ExcelToRecordParser {
	private String filePath;

	public ExcelToRecordParser(String path) {
		this.filePath = path;
	}

	//リスト作成メソッド
	public List<TweetRecord> createRecordList() throws Exception{
		List<TweetRecord> records = new ArrayList<TweetRecord>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath));){
			//シートの取得
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();
			Row row = null;
			//行がある間ループ
			while(rowIterator.hasNext()) {
				row = rowIterator.next();
				//フラッグの取得
				Cell flagCell = row.getCell(0);
				//フラグの判定
				if(isEnable(flagCell)) {
					//読み込んでレコードを生成
					records.add(new TweetRecord(
							row.getCell(3).getStringCellValue()
							));
				}

			}
		}

		return records;
	}

	//フラグ判定メソッド :フラグが１の時のみtrue
	private static boolean isEnable(Cell cell) {
		if(Objects.isNull(cell)) {
			return false;
		}

		if(cell.getCellType().equals(CellType.NUMERIC)) {
			if(cell.getNumericCellValue() == 1) {
				return true;
			}
		}

		return false;
	}
}
