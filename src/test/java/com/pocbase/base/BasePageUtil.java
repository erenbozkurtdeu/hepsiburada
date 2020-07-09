package com.pocbase.base;

import com.pocbase.helper.ElementHelper;
import com.pocbase.helper.StoreHelper;
import com.pocbase.model.ElementInfo;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.*;

import static com.pocbase.step.StepImplementation.userVariables;

public class BasePageUtil extends BaseTest {

  final static Logger log = Logger.getLogger(BasePageUtil.class);

  public By getElementFromJson(String key){
    try {
      ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
      By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
      return infoParam;
    }catch (Exception ex){
      throw new NotFoundException("("+key+") JSON DOSYASINDA BULUNAMADI! "+ ex.getMessage());
    }
  }

  public WebElement findElement(String key){
    WebElement webElement = null;
    By byElement = getElementFromJson(key);
    try {
      WaitingActions.WaitForPageLoad(driver);
      webElement = wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
//      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})", webElement);
    }catch (Exception e){
      StringBuilder nullException = new StringBuilder();
      nullException.append("("+key+")");
      nullException.append(" ELEMENTI SAYFADA BULUNAMADI! ");
      log.error("HATA ! :" + nullException, e);
      Assert.fail(nullException + e.getMessage());
    }
    return webElement;
  }

  public void clickElementKey(String key){
    WebElement element = null;
    try {
      WaitingActions.WaitForPageLoad(driver);
      element = findElement(key);
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'})", element);
      element.click();
      WaitingActions.WaitForPageLoad(driver);
    }catch (Exception e){
      StringBuilder nullException = new StringBuilder();
      nullException.append("("+key+")");
      nullException.append(" ELEMENTINE TIKLANAMADI! \n");
      log.error("HATA! : " + nullException, e);
      Assert.fail(nullException + e.getMessage());
    }
  }

  public String getElementText(String key){
    return findElement(key).getText();
  }

  public void waitUntilElementLocated(String key) {
     wait.until(ExpectedConditions.presenceOfElementLocated(getElementFromJson(key)));
  }

  public String getElementTextOrValue(String key){
    String elementText = "";
    waitUntilElementLocated(key);
    if (!getElementText(key).isEmpty())
    {
      elementText = getElementText(key);
      log.info("elementText -->" + elementText);
    }
    else if (!getElementAttributeValue(key,"innerText").isEmpty())
    {
      elementText = getElementAttributeValue(key,"innerText");
      log.info("elementInnerText -->" + elementText);
    }
    else if (!getElementAttributeValue(key, "value").isEmpty())
    {
      elementText = getElementAttributeValue(key, "value");
      log.info("elementValue -->" + elementText);
    }
    Assert.assertFalse("("+key+") ELEMENTİNİN YAZISI BOŞ GELDİ!", elementText.trim().isEmpty());
    return elementText;
  }

  public String getElementAttributeValue(String key, String attribute){
    return findElement(key).getAttribute(attribute);
  }

  public void sendKeys(String key, String text){
    try {
      WaitingActions.WaitForPageLoad(driver);
      findElement(key).sendKeys(text);
    }catch (Exception e){
      StringBuilder nullException = new StringBuilder();
      nullException.append("("+key+")");
      nullException.append(" INPUT ALANINA YAZILAMADI!");
      log.error("HATA ! :" + nullException, e);
      Assert.fail(nullException + e.getMessage());
    }
  }

  public String isTextAParameter(String text)
  {
    String newText = text;
    if(text.startsWith("@")){

      newText = replaceVariableText(text);
      log.info("Değişken değeri: " + newText);
    }

    return newText;
  }

  public String replaceVariableText(String text){
    String value = text;
    for (Map.Entry me : userVariables.entrySet()) {
      if(me.getKey().equals(text)){
        value = (String) me.getValue();
      }
    }
    Assert.assertFalse(text + "Değişken adı bulunamadı!",value.isEmpty());
    return value;
  }
}