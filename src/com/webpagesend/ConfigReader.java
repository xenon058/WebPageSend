package com.webpagesend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {
	private String fileName;

	public ConfigReader(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> read() throws IOException, IllegalStateException{
		Map<String, String> map = new HashMap<String, String>();
		Path path = Paths.get(fileName);
		List<String> list = Files.readAllLines(path);
		for(String line : list) {
			String[] array = line.split("=");
			if(array.length == 2) {
				map.put(array[0], array[1]);
			}else {
				throw new IllegalStateException();
			}
		}


		return map;
	}
}
