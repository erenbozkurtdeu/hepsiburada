package com.pocbase.step;

import com.pocbase.base.BaseTest;
import com.thoughtworks.gauge.Step;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class StatusController extends BaseTest {
    final static Logger log = Logger.getLogger(StatusController.class);

    @Step("<url> sayfasının status kodu <code> değerine eşittir")
    public void isStatusCodeOk(String url, int code) {
        int counter = 0;
        driver.get(url);
            if (!url.startsWith("javascript") && !url.isEmpty() && !url.startsWith("#") && !url.contains("linkedin") && !url.contains("itunes")) {
                int statusCode = httpResponseCodeViaGet(url);
                if (code != statusCode) {
                    log.warn(counter +". URL: " + url + " " + statusCode + " response kodunu döndü! " + code + "  DEĞİL");
                    Assert.assertEquals(counter + ". URL: " + url + " response kodu " + code + " DEĞİL", code, statusCode);
                }
                else{
                    log.info(counter +". URL: " + url + " linkine erişim sağlandı. OK!");
                }
            }
            else{
                log.warn(counter + ". URL: " + url + " geçerli bir link değil!");
            }
        }

    public int httpResponseCodeViaGet(String url) {
        return RestAssured.get(url).statusCode();
    }
}
