package org.yape.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
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

public class FinishBooking extends BasePage{

    private final User user;
    private final Reservation reservation;
    private static final Logger logger = Logger.getLogger(FinishBooking.class.getName());

    @FindBy (id = "com.booking:id/new_credit_card_number_edit")
    private WebElement cardNumberField;

    @FindBy (id = "com.booking:id/new_credit_card_holder_edit")
    private WebElement cardHolderField;

    @FindBy (id = "com.booking:id/new_credit_card_expiry_date_edit")
    private WebElement expirationDateField;

    @FindBy (xpath = "(//android.widget.TextView[@resource-id='com.booking:id/title'])[2]")
    private WebElement totalAmountFooter;
    
    @FindBy (id = "com.booking:id/subtitle") 
    private WebElement taxesAmountFooter;

    private String finishBookingModal = "android:id/content";

    private String checkinDate = "com.booking:id/checkin_date";

    @FindBy (id = "com.booking:id/checkin_date")
    private WebElement checkinDateElement;

    @FindBy (id = "com.booking:id/checkout_date")
    private WebElement checkoutDateElement;

    private String taxes = "com.booking:id/bp_price_summary_taxes_and_charges";

    @FindBy (id = "com.booking:id/name")
    private WebElement nameElement;

    @FindBy (id = "com.booking:id/email")
    private WebElement emailElement;

    @FindBy (id = "com.booking:id/address_zip")
    private WebElement addressElement;

    @FindBy (id = "com.booking:id/city_country")
    private WebElement cityCountryElement;

    @FindBy (id = "com.booking:id/phone")
    private WebElement phoneElement;

    @FindBy (id = "com.booking:id/bp_price_summary_total_price_value")
    private WebElement totalAmountElement;

    @FindBy (id = "com.booking:id/bp_price_summary_taxes_and_charges")
    private WebElement taxesElement;

    @FindBy (id = "com.booking:id/action_button")
    private WebElement bookNowButton;

    @FindBy (id = "com.booking:id/textinput_error")
    private WebElement cardNumberErrorMessage;

    public FinishBooking(AppiumDriver<MobileElement> driver, User user, Reservation reservation) {
        super(driver);
        this.user = user;
        this.reservation = reservation;
        PageFactory.initElements(driver, this);
    }

    public void enterCardNumber(){
        logger.info("Enter card number");
        cardNumberField.sendKeys(user.getCardNumber());
    }

    public void enterHolderName(String cardHolder){
        logger.info("Enter holder name");
        cardHolderField.sendKeys(cardHolder);
    }

    public boolean isHolderNameCorrect(){
        logger.info("Is holder name correct ?");
        return cardHolderField.getText().equals(String.format("%s %s", user.getFirstName(), user.getLastName()));
    }

    public void enterExpirationDate(){
        logger.info("Enter expiration date");
        expirationDateField.sendKeys(user.getExpirationDate());
    }

    public boolean isTotalAmountFooterCorrect(String totalAmount){
        logger.info("Is total amount footer correct ?");
        return totalAmountFooter.getText().equals(totalAmount);   
    }

    public boolean isTaxesAmountFooterCorrect(String taxesAmount){
        logger.info("Is taxes amount footer correct ?");
        return taxesAmountFooter.getText().equals(taxesAmount);   
    }

    public void scrollIntoFinishBookingModal(){
        logger.info("Scroll into finish booking modal");
        Utils.scrollUntilElementFound(
            driver,
            By.id(finishBookingModal),
            By.id(checkinDate)
        );
    }

    public boolean isCheckInDateCorrect() throws Exception {
        logger.info("Is check in date correct ?");
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date date = originalFormat.parse(reservation.getStartDate());
        String formattedCheckInDate = targetFormat.format(date);
    
        return checkinDateElement.getText().equals(formattedCheckInDate);  
    }

    public boolean isCheckOutDateCorrect() throws Exception {
        logger.info("Is check out date correct ?");
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date date = originalFormat.parse(reservation.getEndDate());
        String formattedCheckOutDate = targetFormat.format(date);
    
        return checkoutDateElement.getText().equals(formattedCheckOutDate);   
    }

    public void scrollUntilAmountDetailsAreFound(){
        logger.info("Scroll until amount details are found");
        Utils.scrollUntilElementFound(
            driver,
            By.id(finishBookingModal),
            By.id(taxes)
        );
    }

    
    public boolean isNameCorrect() {
        logger.info("Is name correct ?");
        String expectedValue = String.format("%s %s", user.getFirstName(), user.getLastName());
        return nameElement.getText().equals(expectedValue);
        }
    
    public boolean isEmailCorrect() {
        logger.info("Is email correct ?");
        return emailElement.getText().equals(user.getEmail());
    }
    

    public boolean isAddressCorrect(){
        logger.info("Is address correct ?");
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

        List<MobileElement> addressFields = driver.findElements(By.id("com.booking:id/address_zip"));

        if (!addressFields.isEmpty()) {
            String expectedValue = String.format("%s, %s", user.getAddress(), user.getZipCode());
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return addressFields.get(0).getText().equals(expectedValue);
        } else {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return true;
        }
        
    }

    public boolean isCityCountryCorrect() {
        logger.info("Is city country correct ?");
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

        List<MobileElement> cityCountryElement = driver.findElements(By.id("com.booking:id/city_country"));

        if (!cityCountryElement.isEmpty()) {
            String expectedValue = String.format("%s, %s", user.getCity(), user.getCountry());
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return cityCountryElement.get(0).getText().equals(expectedValue) || cityCountryElement.get(0).getText().contains(user.getCountry());
        } else {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return true;
        }
    }
    
    public boolean isPhoneNumberCorrect() {
        logger.info("Is phone number correct ?");
        return phoneElement.getText().equals(user.getPhoneNumber());
    }

    public boolean isTotalAmountCorrect(String totalAmount){
        logger.info("Is total amount correct ?");
        return totalAmountElement.getText().equals(totalAmount);
    }

    public boolean isTaxesAmountCorrect(String taxesAmount){
        logger.info("Is taxes amount correct ?");
        return taxesElement.getText().equals(taxesAmount);
    }

    public boolean isBookNowButtonEnabled() {
        logger.info("Is book now button enabled ?");
        return bookNowButton.isEnabled();
    }

    public void clickOnBookNowButton(){
        logger.info("Click on book now button");
        bookNowButton.click();
    }

    public String checkErrorMessage(){
        logger.info("Check error message");
        return cardNumberErrorMessage.getText();
    }
    
}
