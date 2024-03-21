package org.yape.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.logging.Logger;

public class PropertyDetailsPage extends BasePage{

    private static final Logger logger = Logger.getLogger(PropertyDetailsPage.class.getName());

    @FindBy(id = "com.booking:id/fragment_container")
    private WebElement roomModalElement;

    @FindBy(id = "com.booking:id/price_view_taxes_and_charges")
    private WebElement taxesAmountElement;

    @FindBy(id = "com.booking:id/price_view_price")
    private WebElement totalAmountElement;

    @FindBy(id = "com.booking:id/price_view_rack_rate")
    private WebElement totalOriginalAmountElement;

    @FindBy(id = "com.booking:id/select_room_cta")
    private WebElement roomButtonElement;

    public PropertyDetailsPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isSelectRoomModalOpened() {
        logger.info("Is select room modal opened?");
        return roomModalElement.isDisplayed();
    }

    public boolean isTotalOriginalAmountDisplayed(){
        logger.info("Is total original amount displayed?");
        return totalOriginalAmountElement.isDisplayed();
    }

    public boolean isTotalOriginalAmountCorrect(String totalOriginalAmount){
        logger.info("Is total original amount correct?");
        return totalOriginalAmountElement.getText().equals(totalOriginalAmount);   
    }

    public boolean isTotalAmountCorrect(String totalAmount){
        logger.info("Is total amount correct?");
        return totalAmountElement.getText().equals(totalAmount);
    }

    public boolean isTaxesAmountCorrect(String taxes){
        logger.info("Is taxes amount correct?");
        return taxesAmountElement.getText().equals(taxes);
    }

    public void clickOnSelectRoomsButton(){
        logger.info("Click on select rooms button");
        roomButtonElement.click();
    }

}
