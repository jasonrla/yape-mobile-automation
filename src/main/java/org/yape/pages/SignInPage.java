package org.yape.pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.logging.Logger;

public class SignInPage extends BasePage {

    private AppiumDriver<MobileElement> driver;
    private static final Logger logger = Logger.getLogger(SignInPage.class.getName());

    public SignInPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void closeLoginPage() {
        logger.info("Closing login page");
        driver.findElementByAccessibilityId("Navigate up").click();
    }
}