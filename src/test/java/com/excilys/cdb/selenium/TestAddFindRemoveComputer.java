package com.excilys.cdb.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@Ignore
public class TestAddFindRemoveComputer {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testAddFindRemove() throws Exception {

        //Adding a computer named "SeleniumTest" to DB
        driver.get(baseUrl + "/computer-database/computer");
        driver.findElement(By.id("addComputer")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("SeleniumTest");
        driver.findElement(By.id("introduced")).clear();
        driver.findElement(By.id("introduced")).sendKeys("2014-03-02");
        new Select(driver.findElement(By.id("companyId")))
                .selectByVisibleText("IBM");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

        //Trying to find "SeleniumTest" computer
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("SeleniumTest");
        driver.findElement(By.id("searchsubmit")).click();

        //Verify if we have 1 computer
        final String foundAdd = this.driver.findElement(By.id("countComputer")).getText();
        assertTrue(foundAdd.equals("1"));

        //Editing SeleniumTest computer -> changing name to BonjourSelenium
        driver.findElement(By.linkText("SeleniumTest")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("BonjourSelenium");
        new Select(driver.findElement(By.id("companyId")))
                .selectByVisibleText("Lincoln Laboratory");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();

        //Trying to find "BonjourSelenium" computer
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("BonjourSelenium");
        driver.findElement(By.id("searchsubmit")).click();

        //Verify if we have 1 computer
        final String foundEdit = this.driver.findElement(By.id("countComputer")).getText();
        assertTrue(foundEdit.equals("1"));

        //Deleting Computer BonjourSelenium
        driver.findElement(By.id("editComputer")).click();
        driver.findElement(By.name("cb")).click();
        driver.findElement(By.xpath("//a[@id='deleteSelected']/i")).click();
        assertTrue(closeAlertAndGetItsText().matches(
                "^Are you sure you want to delete the selected computers[\\s\\S]$"));

        //Trying to find BonjourSelenium computer
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("BonjourSelenium");
        driver.findElement(By.id("searchsubmit")).click();

        //Verify if we have 0 computer
        final String foundDelete = this.driver.findElement(By.id("countComputer")).getText();
        assertTrue(foundDelete.equals("0"));

        driver.findElement(By.linkText("Application - Computer Database"))
                .click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
