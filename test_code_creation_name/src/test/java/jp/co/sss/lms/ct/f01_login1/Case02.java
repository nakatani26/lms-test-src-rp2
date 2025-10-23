package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		// トップページ
		goTo("http://localhost:8080/lms");

		assertEquals("ログイン", webDriver.findElement(By.tagName("h2")).getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() throws Exception {

		WebElement loginId = webDriver.findElement(By.name("loginId"));
		WebElement loginPassword = webDriver.findElement(By.name("password"));

		// ログインID及びパスワードのクリア処理
		loginId.clear();
		loginPassword.clear();

		// ログインID及びパスワード入力処理
		loginId.sendKeys("StudentAA1000");
		loginPassword.sendKeys("StudentAA1000");

		//ログインボタンを押下
		WebElement loginButton = webDriver.findElement(By.className("btn"));
		loginButton.click();

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement errorMsg = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".help-inline.error")));

		getEvidence(new Object() {
		});
		String actualMessage = errorMsg.getText().trim();
		assertEquals("ログインに失敗しました。", actualMessage);

	}

}
