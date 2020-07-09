package com.pocbase.step;

import com.pocbase.base.BasePageUtil;
import com.pocbase.base.BaseTest;
import com.pocbase.base.WaitingActions;
import com.thoughtworks.gauge.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class StepImplementation extends BaseTest {

  final static Logger log = Logger.getLogger(StepImplementation.class);
  public static HashMap<String, String> userVariables = new HashMap<>();
  BasePageUtil basePageUtil = new BasePageUtil();

  @Step("<key> elementinin texti <text> değerini içerir")
  public void isElementTextContains(String key, String text) {
    String getElementText = basePageUtil.getElementTextOrValue(key);
    Assert.assertTrue(key + " elementinden gelen text " + text +" içermiyor",
            getElementText.trim().toLowerCase().contains(basePageUtil.isTextAParameter(text).trim().toLowerCase()));
  }

  @Step("<saniye> saniye beklenir")
  public void waitSeconds(int saniye) {
    try {
      TimeUnit.SECONDS.sleep(saniye);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Step({"<key> elementine tıklanır"})
  public void clickElement(String key) {
    if (!key.equals("")) {
      basePageUtil.clickElementKey(key);
    }
  }

  @Step("<key> elementine <text> yazılır")
  public void sendKeys(String key, String text) {
    if (!key.equals("")) {
      basePageUtil.sendKeys(key, text);
    }
  }

  @Step("<url> adresine gidilir")
  public void goToUrl(String url) {
    driver.get(url);
    log.info(url + "url'ine gidildi");
    WaitingActions.WaitForPageLoad(driver);
  }

  @Step("Şuanki URL'in <text> değerini içerdiği kontrol edilir")
  public void isCurrentUrlContainText(String text){
    String currentUrl = "";
    try{
      WaitingActions.WaitForPageLoad(driver);
      currentUrl = driver.getCurrentUrl();
      currentUrl.contains(text);
    }catch (Exception ex){
      StringBuilder nullException = new StringBuilder();
      nullException.append("\""+currentUrl+"\"");
      nullException.append(text+" DEĞERİNİ İÇERMİYOR! \n");
      log.error("HATA! : " + nullException, ex);
      Assert.fail(nullException + ex.getMessage());
    }
  }
}
