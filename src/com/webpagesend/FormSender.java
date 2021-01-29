package com.webpagesend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormSender {
	private static final String urlFormPath = "//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[1]/div/div/div[2]/div/div[1]/div/div[1]/input";
	private static final String RadioButtonPath = "//*[@id=\"i9\"]";
	private static final String CheckBox1Path  = "//*[@id=\"i23\"]";
	private static final String CheckBox2Path  = "//*[@id=\"i26\"]";
	private static final String nameFormPath = "//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[4]/div/div/div[2]/div/div[1]/div/div[1]/input";
	private static final String groupFormPath = "//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[1]/div/div[1]/input";
	private static final String ageFormPath = "//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[6]/div/div/div[2]/div/div[1]/div/div[1]/input";

	LocalDate date = LocalDate.now();
	String datestr = date.format(DateTimeFormatter.ISO_DATE);
	private static final String dateFormPath = "//*[@id=\"mG61Hd\"]/div[2]/div/div[2]/div[7]/div/div/div[2]/div/div/div[2]/div[1]/div/div[1]/input";
	private static final String submitButton = "//*[@id=\"mG61Hd\"]/div[2]/div/div[3]/div[1]/div/div";

	private String url;
	private Map<String, String> infomation;

	public FormSender(String url) {
		this.url = url;
	}

	public void send(List<TweetRecord> records, Map<String, String> infomation) throws Exception{
		this.infomation = infomation;

		System.setProperty("webdriver.chrome.driver", "./lib/chromedriver");

		WebDriver driver = new ChromeDriver();

		//処理を待機する最大時間を設定
		WebDriverWait wait = new WebDriverWait(driver, 5);

		//全てのレコードを送信するまで繰り返し
		for(TweetRecord record:records) {
			driver.get(this.url);
			inputField(record, driver, wait);
		}

		//ページを閉じる
		driver.quit();

	}

	private void inputField(TweetRecord record, WebDriver driver, WebDriverWait wait) throws Exception{

		WebElement urlForm = driver.findElement(By.xpath(urlFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(urlForm));
		urlForm.sendKeys(record.getTweetUrl());
		//ラジオボタン入力
		WebElement RadioButton = driver.findElement(By.xpath(RadioButtonPath));
		wait.until(ExpectedConditions.elementToBeClickable(RadioButton));
		RadioButton.click();

		//入力
		WebElement CheckBox1 = driver.findElement(By.xpath(CheckBox1Path));
		wait.until(ExpectedConditions.elementToBeClickable(CheckBox1));
		CheckBox1.click();

		WebElement CheckBox2 = driver.findElement(By.xpath(CheckBox2Path));
		wait.until(ExpectedConditions.elementToBeClickable(CheckBox2));
		CheckBox2.click();

		WebElement nameForm = driver.findElement(By.xpath(nameFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(nameForm));
		nameForm.sendKeys(infomation.get("name"));

		WebElement groupForm = driver.findElement(By.xpath(groupFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(groupForm));
		groupForm.sendKeys(infomation.get("group"));

		WebElement ageForm = driver.findElement(By.xpath(ageFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(ageForm));
		ageForm.sendKeys(infomation.get("age"));

		WebElement dateForm = driver.findElement(By.xpath(dateFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(dateForm));
		dateForm.sendKeys(datestr);

		//送信
		WebElement sendButton = driver.
				findElement(By.xpath(submitButton));
		wait.until(ExpectedConditions.elementToBeClickable(sendButton));
		sendButton.click();
	}
}
