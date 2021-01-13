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
	private String url;
	private Map<String, String> infomation;

	public FormSender(String url) {
		this.url = url;
	}

	public void send(List<TweetRecord> records, Map<String, String> infomation){
		this.infomation = infomation;

		System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		//処理を待機する最大時間を設定
		WebDriverWait wait = new WebDriverWait(driver, 5);

		//全てのレコードを送信するまで繰り返し
		for(TweetRecord record:records) {
			try {
				System.out.println("回答開始");
				driver.get(this.url);
				inputField(record, driver, wait);
				System.out.println("送信完了");
			}catch(Exception e) {
				System.out.println("送信失敗");
			}
		}

		//ページを閉じる
		driver.quit();

	}

	private void inputField(TweetRecord record, WebDriver driver, WebDriverWait wait) throws Exception{

		//ラジオボタン入力
		List<WebElement> radioButtons = driver.findElements(By.className("freebirdThemedRadio"));
		wait.until(ExpectedConditions.elementToBeClickable(radioButtons.get(record.getType())));
		radioButtons.get(record.getType()).click();

		List<WebElement> inputList = driver.findElements(By.className("exportInput"));

		//入力
		wait.until(ExpectedConditions.elementToBeClickable(inputList.get(0)));
		inputList.get(1).sendKeys(record.getTweetUrl());
		wait.until(ExpectedConditions.elementToBeClickable(inputList.get(1)));
		inputList.get(2).sendKeys(infomation.get("mail_address"));
		wait.until(ExpectedConditions.elementToBeClickable(inputList.get(2)));
		inputList.get(3).sendKeys(infomation.get("name"));
		wait.until(ExpectedConditions.elementToBeClickable(inputList.get(3)));
		inputList.get(4).sendKeys(infomation.get("group"));

		//送信
		WebElement sendButton = driver.
				findElement(By.className("freebirdFormviewerViewNavigationSubmitButton"));
		wait.until(ExpectedConditions.elementToBeClickable(sendButton));
		sendButton.click();
	}
}
