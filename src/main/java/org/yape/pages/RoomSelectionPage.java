package org.yape.pages;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class RoomSelectionPage extends BasePage {

    private static final Logger logger = Logger.getLogger(RoomSelectionPage.class.getName());

    @FindBy(id = "com.booking:id/recommended_block_subtotal_price_view")
    private WebElement totalAmountElement;

    @FindBy(id = "com.booking:id/recommended_block_subtotal_taxes_charges_view")
    private WebElement taxesAmountElement;

    @FindBy(id = "com.booking:id/recommended_block_reserve_button")
    private WebElement reserveButton;

    @FindBy(id = "com.booking:id/select_room_cta")
    private WebElement selectRoomsButton;

    @FindBy(id = "com.booking:id/rooms_item_select_text_view")
    private WebElement selectAndCustomizeButton;

    @FindBy(id = "com.booking:id/rooms_item_select_text_view")
    private WebElement selectButton;

    @FindBy(id = "com.booking:id/room_pref_select")
    private WebElement confirmButton;

    @FindBy(xpath = "//android.widget.Button[@resource-id='com.booking:id/main_action']")
    private WebElement reserveWidgetButton;

    public RoomSelectionPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isTotalAmountCorrect(String totalAmount){
        logger.info("Is total amount correct?");
        return totalAmountElement.getText().equals(totalAmount);
    }

    public boolean isTaxesAmountCorrect(String taxes){
        logger.info("Is taxes amount correct?");
        return taxesAmountElement.getText().equals(taxes);
    }

    public boolean isReserveButtonDisplayed(){
        logger.info("Is reserve button displayed?");
        return reserveButton.isDisplayed();
    }

    public void clickOnReserveButton(){
        logger.info("Click on reserve button");
        reserveButton.click();;
    }

    public boolean isSelectRoomsButtonDisplayed(){
        logger.info("Is select rooms button displayed?");
        return selectRoomsButton.isDisplayed();
    }

    public void clickOnSelectRoomsButton(){
        logger.info("Click on select rooms button");
        selectRoomsButton.click();
    }

    public boolean isSelectAndCustomizeButtonDisplayed(){
        logger.info("Is select and customize button displayed?");
        return selectAndCustomizeButton.isDisplayed();
    }

    public void clickOnSelectAndCustomizeButton(){
        logger.info("Click on select and customize button");
        selectAndCustomizeButton.click();
    }

    public boolean isSelectButtonDisplayed(){
        logger.info("Is select button displayed?");
        return selectButton.isDisplayed();
    }

    public void clickOnSelectButton(){
        logger.info("Click on select button");
        selectButton.click();
    }

    public boolean isConfirmButtonDisplayed(){
        logger.info("Is confirm button displayed?");
        return confirmButton.isDisplayed();
    }

    public void clickOnConfirmButton(){
        logger.info("Click on confirm button");
        confirmButton.click();
    }

    public boolean isReserveWidgetButtonDisplayed(){
        logger.info("Is reserve widget button displayed?");
        return reserveWidgetButton.isDisplayed();
    }

    public void clickOnReserveWidgetButton(){
        logger.info("Click on reserve widget button");
        reserveWidgetButton.click();
    }

    public void selectButton(){
        logger.info("Select button");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        if(isSelectButtonDisplayed()){
            clickOnSelectButton();
        }
    }

    public void reserveButton(){
        logger.info("Reserve button");
        if(isReserveButtonDisplayed()){
            clickOnReserveButton();
        }
    }

    public void confirmButton(){
        logger.info("Confirm button");
        if(isConfirmButtonDisplayed()){
            clickOnConfirmButton();
        }
    }

    public void reserveWidgetButton(){
        logger.info("Reserve widget button");
        if(isReserveWidgetButtonDisplayed()){
            clickOnReserveWidgetButton();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

}
