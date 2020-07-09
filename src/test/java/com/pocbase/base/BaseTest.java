package com.pocbase.base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import static java.lang.System.getenv;

public class BaseTest {


  final static Logger log = Logger.getLogger(BaseTest.class);
  protected static WebDriver driver;
  protected static WebDriverWait wait;
  public static String baseUrl = "https://www.hepsiburada.com/cozummerkezi";

  //Choose webdriver
  public static String WebDriverOption = "chrome";
  //public static String WebDriverOption = "firefox";

  @BeforeScenario
  public void setUp() throws Exception {
    if(WebDriverOption == "chrome") {
        // WebDriver version 83.0.4103.116
        System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("test-type");
        options.addArguments("disable-popup-blocking");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("disable-translate");
        driver = new ChromeDriver(options);
    }
    else
    {
        System.setProperty("webdriver.gecko.driver", "web_driver/geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("test-type");
        options.addArguments("disable-translate");
        driver = new FirefoxDriver(options);
    }
      wait = new WebDriverWait(driver, 15, 200);
      driver.manage().window().maximize();



    log.info("********************************* TEST IS STARTED *********************************");
    if (StringUtils.isNotEmpty(getenv("environment"))){
        baseUrl = getenv("environment");
    }
    log.info("Tarayıcı başlangıç URL'i: "+ baseUrl);
    driver.get(baseUrl);
  }

  @AfterScenario
  public void tearDown(){
    driver.quit();
    log.info("********************************* TEST IS FINISHED *********************************");
  }
}
