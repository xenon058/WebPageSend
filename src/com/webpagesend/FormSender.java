package com.webpagesend;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormSender {
	private static final String radioButtonPath = "//*[@id=\"formReport\"]/div[2]/div[2]/div/div[1]/div[2]/ul/li[1]/label";
	private static final String urlFormPath  = "//*[@id=\"inputWebsite\"]/input";
	private static final String mailAddressFormPath = "//*[@id=\"formReport\"]/div[2]/div[2]/div/div[3]/div/dl[1]/dd/input";
	private static final String nameFormPath = "//*[@id=\"formReport\"]/div[2]/div[2]/div/div[3]/div/dl[2]/dd/input";
	private static final String groupFormPath = "//*[@id=\"formReport\"]/div[2]/div[2]/div/div[3]/div/dl[3]/dd/input";
	private static final String submitButton = "//*[@id=\"btnReport\"]";

	private String url;
	private Map<String, String> infomation;

	public FormSender(String url) {
		this.url = url;
	}

	public void send(List<TweetRecord> records, Map<String, String> infomation) throws Exception{
		this.infomation = infomation;

		System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");

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

		//ラジオボタン入力
		WebElement radioButton = driver.findElement(By.xpath(radioButtonPath));
		wait.until(ExpectedConditions.elementToBeClickable(radioButton));
		radioButton.click();

		//入力
		WebElement urlForm = driver.findElement(By.xpath(urlFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(urlForm));
		urlForm.sendKeys(record.getTweetUrl());

		WebElement mailAddressForm = driver.findElement(By.xpath(mailAddressFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(mailAddressForm));
		mailAddressForm.sendKeys(infomation.get("mail_address"));

		WebElement nameForm = driver.findElement(By.xpath(nameFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(nameForm));
		nameForm.sendKeys(infomation.get("name"));

		WebElement groupForm = driver.findElement(By.xpath(groupFormPath));
		wait.until(ExpectedConditions.elementToBeClickable(groupForm));
		groupForm.sendKeys(infomation.get("group"));

		//送信
		WebElement sendButton = driver.
				findElement(By.xpath(submitButton));
		wait.until(ExpectedConditions.elementToBeClickable(sendButton));
		sendButton.click();
	}
}
