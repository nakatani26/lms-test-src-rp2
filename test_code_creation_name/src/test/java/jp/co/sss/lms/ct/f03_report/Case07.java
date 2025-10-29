package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() throws Exception {

		goTo("http://localhost:8080/lms");

		assertEquals("ログイン", webDriver.findElement(By.tagName("h2")).getText());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {

		WebElement loginId = webDriver.findElement(By.name("loginId"));
		WebElement loginPassword = webDriver.findElement(By.name("password"));

		// ログインID及びパスワードのクリア処理
		loginId.clear();
		loginPassword.clear();

		// ログインID及びパスワード入力処理
		loginId.sendKeys("StudentAA01");
		loginPassword.sendKeys("StudentAA02");

		//ログインボタン押下
		WebElement loginButton = webDriver.findElement(By.className("btn"));
		loginButton.click();

		new WebDriverWait(webDriver, Duration.ofSeconds(10))
				.until(ExpectedConditions.urlContains("/course/detail"));

		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() throws Exception {
		//waitを定義(10秒待つ)
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 未提出の研修日を取得
		WebElement targetRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//table[contains(@class,'sctionList')]//tr[.//span[text()='未提出']]")));

		//詳細ボタン押下
		WebElement detailButton = targetRow.findElement(By.xpath(".//input[@value='詳細']"));
		detailButton.click();

		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/section/detail"));
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() throws Exception {

		//詳細ボタンを押下
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement report = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//input[@value='日報【デモ】を提出する']")));
		report.click();

		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() throws Exception {
		WebElement textarea = webDriver.findElement(By.id("content_0"));
		textarea.sendKeys("あいうえお");

		getEvidence(new Object() {
		}, "01");

		//提出するボタン押下
		WebElement submitButton = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submitButton.click();

		WebElement report = webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"));
		assertEquals("提出済み日報【デモ】を確認する", report.getAttribute("value"));

		getEvidence(new Object() {
		}, "02");

	}

}
