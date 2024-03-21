package org.yape.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.yape.model.Reservation;
import org.yape.model.User;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class RegistrationPage extends BasePage {

    private final User user;
    private final Reservation reservation;
    private static final Logger logger = Logger.getLogger(RegistrationPage.class.getName());

    @FindBy(xpath = "//android.widget.TextView[contains(@text,'First Name')]/following-sibling::android.widget.EditText")
    private WebElement firstNameElement;
    
    @FindBy(xpath = "//android.widget.TextView[contains(@text,'Last Name')]/following-sibling::android.widget.EditText")
    private WebElement lastNameElement;

    @FindBy(xpath = "//android.widget.TextView[contains(@text,'Email')]/following-sibling::android.widget.AutoCompleteTextView")
    private WebElement emailElement;

    private String addressElement = "//android.widget.TextView[contains(@text,'Address')]/following-sibling::android.widget.EditText";

    private String zipCodeElement = "//android.widget.TextView[contains(@text,'Zip Code')]/following-sibling::android.widget.EditText";

    private String cityElement = "//android.widget.TextView[contains(@text,'City')]/following-sibling::android.widget.EditText";

    private String formElement = "//android.widget.LinearLayout[@resource-id='com.booking:id/bstage1_contact_layout']";

    private String leisurePurposeElement = "com.booking:id/business_purpose_leisure";

    @FindBy (xpath = "//android.widget.TextView[contains(@text,'Country/Region')]/following-sibling::android.widget.AutoCompleteTextView")
    private WebElement countryElement;

    @FindBy (xpath = "//android.widget.TextView[contains(@text,'Mobile Phone')]/following-sibling::android.widget.EditText")
    private WebElement phoneNumberElement;

    private String purposeOption = "//android.widget.RadioButton[@text='REPLACE-TEXT']";

    @FindBy (id = "com.booking:id/title")
    private WebElement totalAmountElement;

    @FindBy (xpath = "//android.widget.TextView[@resource-id='com.booking:id/subtitle']")
    private WebElement taxesAmountElement;

    @FindBy (id = "com.booking:id/action_button")
    private WebElement nextStepButton;

    public RegistrationPage(AppiumDriver<MobileElement> driver, User user, Reservation reservation) {
        super(driver);
        this.user = user;
        this.reservation = reservation;
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(){
        logger.info("Enter first name");
        firstNameElement.sendKeys(user.getFirstName());
    }

    public void enterLastName(){
        logger.info("Enter last name");
        lastNameElement.sendKeys(user.getLastName());
    }

    public void enterEmail(){
        logger.info("Enter email");
        emailElement.sendKeys(user.getEmail());
    }

    public void enterAddress(){
        logger.info("Enter address");
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

        By addressFieldSelector = By.xpath(addressElement);
        List<MobileElement> addressFields = driver.findElements(addressFieldSelector);

        if (!addressFields.isEmpty()) {
            addressFields.get(0).sendKeys(user.getAddress());
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    public void enterZipCode(){
        logger.info("Enter zip code");
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

        By zipCodeFieldSelector = By.xpath(zipCodeElement);
        List<MobileElement> zipCodeFields = driver.findElements(zipCodeFieldSelector);

        if (!zipCodeFields.isEmpty()) {
            zipCodeFields.get(0).sendKeys(user.getZipCode());
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void enterCity(){
        logger.info("Enter city");
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

        By cityFieldSelector = By.xpath(cityElement);
        List<MobileElement> cityFields = driver.findElements(cityFieldSelector);
    
        if (!cityFields.isEmpty()) {
            cityFields.get(0).sendKeys(user.getCity());
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void scrollForm(){
        logger.info("Scroll form");
        Utils.scrollUntilElementFound(
            driver,
            By.xpath(formElement),
            By.id(leisurePurposeElement)
        );
    }

    public void selectCountry(){
        logger.info("Select country");
        countryElement.clear();
        countryElement.sendKeys(user.getCountry());
    }

    public void enterPhoneNumber(){
        logger.info("Enter phone number");
        phoneNumberElement.sendKeys(user.getPhoneNumber());
    }

    public boolean isValueSetCorrectly(WebElement element, String expectedValue){
        logger.info("Is value set correctly ?");
        boolean a = element.getText().equals(expectedValue);
        return a;
    }

    public void selectPurpouseOption(){
        logger.info("Select purpose option");
        Utils.getElement(driver, purposeOption, reservation.getPurpose()).click();
    }

    public boolean isTotalAmountInFillInfoDetailsCorrect(String totalAmount){
        logger.info("Is total amount in fill info details correct ?");
        return totalAmountElement.getText().contains(totalAmount);   
    }

    public boolean isTaxesAmountInFillInfoDetailsCorrect(String taxes){
        logger.info("Is taxes amount in fill info details correct ?");
        return taxesAmountElement.getText().equals(taxes);   
    }

    public boolean isAddMissingDetailsButtonDisplayed() {
        logger.info("Is add missing details button displayed ?");
        return nextStepButton.getText().equals("Add missing details");
    }

    public boolean isAddMissingDetailsButtonEnabled() {
        logger.info("Is add missing details button enabled ?");
        return nextStepButton.isEnabled();
    }

    public boolean isNextStepButtonEnabled() {
        logger.info("Is next step button enabled ?");
        return nextStepButton.isEnabled();
    }

    public boolean isNextStepButtonDisplayed() {
        logger.info("Is next step button displayed ?");
        return nextStepButton.getText().equals("Next step");
    }

    public void clickOnNextStepButton(){
        logger.info("Click on next step button");
        nextStepButton.click();
    }

}
