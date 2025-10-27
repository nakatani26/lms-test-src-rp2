package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.time.Duration;
import java.util.Set;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() throws Exception {
		//ドロップダウン
		WebElement dropdown = webDriver.findElement(By.className("dropdown"));
		dropdown.click();

		//ヘルプボタン押下
		WebElement helpButton = webDriver.findElement(By.linkText("ヘルプ"));
		helpButton.click();

		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws Exception {
		//現在のタブを識別
		String originalHandle = webDriver.getWindowHandle();

		WebElement faqLink = webDriver.findElement(By.linkText("よくある質問"));
		faqLink.click();

		//新しいタブに切り替え
		Set<String> handles = webDriver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(originalHandle)) {
				webDriver.switchTo().window(handle);
				break;
			}
		}

		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() throws Exception {

		//研修関係を押下
		WebElement category = webDriver.findElement(By.linkText("【研修関係】"));
		category.click();

		//スクロール、待ち時間
		scrollBy("window.innerHeight");
		pageLoadTimeout(5);

		WebElement result = webDriver.findElement(By.className("sorting_1"));

		assertEquals("Q.キャンセル料・途中退校について", result.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() throws Exception {
		//質問押下
		WebElement q = webDriver.findElement(By.className("sorting_1"));
		q.click();

		//スクロール、待ち時間
		scrollBy("window.innerHeight");
		pageLoadTimeout(5);

		WebElement a = webDriver.findElement(By.id("answer-h[${status.index}]"));

		String expectedAnswer = "A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、"
				+ "事情をお伺いした上で、協議という形を取らせて頂きます。"
				+ " 弊社営業担当までご相談下さい。";

		assertEquals(expectedAnswer, a.getText());

		getEvidence(new Object() {
		});

	}
}
