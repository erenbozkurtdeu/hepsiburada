package com.pocbase.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitingActions {
    final static Logger log = Logger.getLogger(WaitingActions.class);
    public WebDriver driver;
    public WebDriverWait wait;
    public JavascriptExecutor javascriptExecutor;


    public WaitingActions(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.javascriptExecutor = (JavascriptExecutor) driver;
    }

    private static void waitForDocumentLoad(WebDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return  document.readyState").equals("complete");}});
    }

    private static void waitForAjaxLoad(WebDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElements(By.cssSelector(".waiting, .tb-loading")).size() == 0;}});
    }

    public static void WaitForPageLoad(WebDriver driver)
    {
        try {
            waitForDocumentLoad(driver);
            waitForAjaxLoad(driver);
            waitForDocumentLoad(driver);
        }catch (Exception e){
            log.error("SAYFA YüKLENEMEDİ! PAGE WAIT EXCEPTION! "+ e.getMessage());
        }
    }

}