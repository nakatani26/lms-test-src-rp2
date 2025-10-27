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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	private static final By By = null;

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
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		WebElement keyword = webDriver.findElement(By.name("keyword"));
		keyword.clear();
		keyword.sendKeys("助成金");

		//検索ボタン押下
		WebElement searchButton = webDriver.findElement(By.xpath("//*[@value='検索']"));
		searchButton.click();

		getEvidence(new Object() {
		}, "01");

		//スクロール、待ち時間
		scrollBy("window.innerHeight");
		pageLoadTimeout(10);

		WebElement result = webDriver.findElement(By.className("mb10"));
		assertEquals("Q.助成金書類の作成方法が分かりません", result.getText());

		getEvidence(new Object() {
		}, "02");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() throws Exception {

		//クリアボタン押下
		WebElement clearButton = webDriver.findElement(By.xpath("//*[@value='クリア']"));
		clearButton.click();

		//キーワード消去されてるか
		WebElement keyword = webDriver.findElement(By.name("keyword"));
		assertEquals("", keyword.getText());

		getEvidence(new Object() {
		});
	}
}
