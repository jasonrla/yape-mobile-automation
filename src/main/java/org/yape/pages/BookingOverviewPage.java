package org.yape.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.yape.model.Reservation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class BookingOverviewPage extends BasePage{

    private final Reservation reservation;
    private static final Logger logger = Logger.getLogger(BookingOverviewPage.class.getName());

    @FindBy (id = "com.booking:id/checkin_date")
    private WebElement checkinDateElement;

    @FindBy (id = "com.booking:id/checkout_date")
    private WebElement checkoutDateElement;

    @FindBy (id = "com.booking:id/bp_price_summary_total_price_value") 
    private WebElement totalAmountElement;

    @FindBy (id = "com.booking:id/bp_price_summary_taxes_and_charges")
    private WebElement taxesAmountElement;

    @FindBy (xpath = "(//android.widget.TextView[@resource-id='com.booking:id/title'])[2]")
    private WebElement totalAmountFooter;

    @FindBy (xpath = "(//android.widget.TextView[@resource-id='com.booking:id/subtitle'])[2]")
    private WebElement taxesAmountFooter;

    @FindBy (id = "com.booking:id/action_button")
    private WebElement finalStepButton;

    public BookingOverviewPage(AppiumDriver<MobileElement> driver, Reservation reservation) {
        super(driver);
        this.reservation = reservation;
        PageFactory.initElements(driver, this);
    }

    public boolean isCheckInDateCorrect() throws Exception {
        logger.info("Check in date");
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date date = originalFormat.parse(reservation.getStartDate());
        String formattedCheckInDate = targetFormat.format(date);
    
        return checkinDateElement.getText().equals(formattedCheckInDate);   
    }
    
    public boolean isCheckOutDateCorrect() throws Exception {
        logger.info("is Check out date correct ?");
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date date = originalFormat.parse(reservation.getEndDate());
        String formattedCheckOutDate = targetFormat.format(date);

        return checkoutDateElement.getText().equals(formattedCheckOutDate);   
    }

    public boolean isBookingOverviewTotalAmountCorrect(String totalAmount){
        logger.info("is Booking overview total amount correct ?");
        return totalAmountElement.getText().contains(totalAmount);   
    }

    public boolean isBookingOverviewTotalAmountCorrect(String totalAmount, String totalOriginalAmount){
        logger.info("is Booking overview total amount correct ?");
        return totalAmountElement.getText().equals(totalOriginalAmount + " " + totalAmount);   
    }

    public boolean isBookingOverviewTaxesAmountCorrect(String taxesAmount){
        logger.info("is Booking overview taxes amount correct ?");
        return taxesAmountElement.getText().equals(taxesAmount);   
    }

    public boolean isTotalAmountFooterCorrect(String totalAmount){
        logger.info("is Total amount footer correct ?");
        return totalAmountFooter.getText().equals(totalAmount);   
    }

    public boolean isTaxesAmountFooterCorrect(String taxesAmount){
        logger.info("is Taxes amount footer correct ?");
        return taxesAmountFooter.getText().equals(taxesAmount);   
    }

    public void clickOnFinalStepButton(){
        logger.info("Click on final step button");
        finalStepButton.click();
    }

}
