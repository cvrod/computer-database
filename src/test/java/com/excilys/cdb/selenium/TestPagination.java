package com.excilys.cdb.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

@Ignore
public class TestPagination {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void testPagination() throws Exception {
    driver.get(baseUrl + "/computer-database/computer?page=0&offset=10");
    driver.findElement(By.cssSelector("button.btn.btn-default")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.linkText("3")).click();
    driver.findElement(By.linkText("4")).click();
    driver.findElement(By.linkText("7")).click();
    driver.findElement(By.linkText("10")).click();
    driver.findElement(By.linkText("13")).click();
    driver.findElement(By.linkText("16")).click();
    driver.findElement(By.linkText("19")).click();
    driver.findElement(By.linkText("22")).click();
    driver.findElement(By.linkText("25")).click();
    driver.findElement(By.linkText("28")).click();
    driver.findElement(By.linkText("31")).click();
    driver.findElement(By.linkText("34")).click();
    driver.findElement(By.linkText("37")).click();
    driver.findElement(By.linkText("40")).click();
    driver.findElement(By.linkText("43")).click();
    driver.findElement(By.linkText("46")).click();
    driver.findElement(By.linkText("49")).click();
    driver.findElement(By.linkText("52")).click();
    driver.findElement(By.linkText("55")).click();
    driver.findElement(By.linkText("58")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}