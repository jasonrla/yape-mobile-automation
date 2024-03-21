package org.yape.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.yape.model.Reservation;

public class HomePage extends BasePage {

    private final Reservation reservation;

    private static final Logger logger = Logger.getLogger(BookingOverviewPage.class.getName());
    
    @FindBy(id = "com.booking:id/facet_search_box_outline")
    private WebElement mainPageElement;

    @FindBy(xpath = "//android.widget.TextView[@text='Enter your destination']")
    private WebElement destinationFieldElement;

    @FindBy(id = "com.booking:id/facet_with_bui_free_search_booking_header_appbar_layout")
    private WebElement destinationModalElement;

    @FindBy(id = "com.booking:id/facet_with_bui_free_search_booking_header_toolbar_content")
    private WebElement searchBoxElement;

    @FindBy(id = "com.booking:id/facet_disambiguation_content")
    private WebElement searchResultListElement;

    private String searchResultItem = "android.view.ViewGroup";

    private String destinationTitle = ".//android.widget.TextView[contains(@resource-id,'destination_title')]";

    private String destinationSubtitle = ".//android.widget.TextView[contains(@resource-id,'destination_subtitle') and not(contains(@text,'properties'))]";

    private String firstItemElement = "(//android.widget.TextView[@text='REPLACE-TEXT'])[1]";

    @FindBy(id = "com.booking:id/facet_date_picker_confirm")
    private WebElement selectDateButtonElement;

    @FindBy(id = "com.booking:id/facet_date_picker_selection_summary")
    private WebElement dateRangeSelectionSummaryElement;

    @FindBy(id = "com.booking:id/facet_with_bottom_sheet_header_content")
    private WebElement calendarElement;

    private String calendarModaElement = "com.booking:id/calendar_month_list";

    private String searchElement = "//android.widget.TextView[@resource-id='com.booking:id/facet_search_box_basic_field_label' and @text='REPLACE-TEXT']";

    @FindBy(xpath = "(//android.widget.TextView[@resource-id='com.booking:id/facet_search_box_basic_field_label'])[2]")
    private WebElement datesFieldElement;

    @FindBy(xpath = "(//android.widget.TextView[@resource-id='com.booking:id/facet_search_box_basic_field_label'])[3]")
    private WebElement guestFieldElement;

    private String occupancyFieldElement = "//android.widget.TextView[contains(@text,'REPLACE-TEXT adults')]";

    @FindBy(id = "com.booking:id/design_bottom_sheet")
    private WebElement roomAndGuestModalElement;

    @FindBy(xpath = "(//android.widget.Button[@resource-id='com.booking:id/bui_input_stepper_add_button'])[3]")
    private WebElement increaseChildrenGuestElement;

    @FindBy(id = "android:id/parentPanel")
    private WebElement ageOfChildModalElement;

    private String agePickerViewElement = "com.booking:id/age_picker_view";

    private String ageScrollElement = "//android.widget.EditText[@text='REPLACE-TEXT years old']";

    @FindBy(xpath = "//android.widget.ScrollView[@resource-id='android:id/buttonPanel']/android.widget.LinearLayout")
    private WebElement selectAgeModaElement;

    @FindBy(id = "android:id/button1")
    private WebElement okButtonElement;

    @FindBy(id = "com.booking:id/group_config_children_ages_header")
    private WebElement childrensAgesHeaderElement;

    private String numberOfChildrenElements = "com.booking:id/group_config_children_ages_recycler_view";

    @FindBy(id = "com.booking:id/group_config_children_ages_recycler_view")
    private WebElement numberOfChildrenElement;

    private String childrenLayoutElement = "android.widget.LinearLayout";

    private String childrenNumElement = ".//android.widget.TextView[contains(@text,'Child')]";
    private String childrenAgeElement = ".//android.widget.TextView[contains(@text,'old')]";

    @FindBy(id = "com.booking:id/group_config_apply_button")
    private WebElement applyButtonElement;

    @FindBy(id = "com.booking:id/facet_search_box_cta")
    private WebElement searchButtonElement;

    
    public HomePage(AppiumDriver<MobileElement> driver, Reservation reservation) {
        super(driver);
        this.reservation = reservation;
        PageFactory.initElements(driver, this);
    }

    public boolean isMainPageOpened() {
        logger.info("Main page is opened");
        return mainPageElement.isDisplayed();
    }

    public void enterYourDestination() {
        logger.info("Enter destination");
        destinationFieldElement.click();
    }

    public String getDestinationText() {
        logger.info("Get destination text");
        return destinationFieldElement.getText();
    }

    public boolean isDestinationDefaultTextDisplayed(){
        logger.info("Get default destination text");
        return getDestinationText().equals("Enter your destination");
    }

    public String getDateRangeText(){
        logger.info("Get date range text");
        return datesFieldElement.getText();
    }

    public boolean isDateRangeDefaultTextDisplayed() throws ParseException{
        logger.info("Get default date range text");
        String defaultDate = getFormattedDate(Utils.getCurrentDate(), Utils.getNextDate());
        return getDateRangeText().equals(defaultDate);
    }

    public String getGuestText(){
        logger.info("Get occupancy text");
        return guestFieldElement.getText();
    }

    public Object isGuestDefaultTextDisplayed() {
        logger.info("Get occupancy default text");
        return getGuestText().equals("1 room · 2 adults · 0 children");
    }

    public boolean isDestinationModalOpened() {
        logger.info("Destination modal opened");
        return destinationModalElement.isDisplayed();
    }

    public void enterDestinationToBeSearched() {
        logger.info("Enter destination to be searched");
        searchBoxElement.sendKeys(reservation.getDestination());
    }

    public boolean allItemsContainSearchWord() {
        logger.info("All items contain search word ?");
        List<WebElement> viewGroups = getViewGroups();
        return checkAllGroupsContainSearchWord(viewGroups);
    }
    
    private List<WebElement> getViewGroups() {
        logger.info("Get view groups");
        return searchResultListElement.findElements(By.className(searchResultItem));
    }
    
    private boolean checkAllGroupsContainSearchWord(List<WebElement> viewGroups) {
        logger.info("Check if all groups contain search word");
        for (WebElement viewGroup : viewGroups) {
            if (!groupContainsSearchWord(viewGroup)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean groupContainsSearchWord(WebElement viewGroup) {
        logger.info("Group contains search word");
        List<WebElement> textViews = viewGroup.findElements(By.xpath(destinationTitle));
        List<WebElement> textViewsST = viewGroup.findElements(By.xpath(destinationSubtitle));
        return textViewsContainSearchWord(textViews) || textViewsContainSearchWord(textViewsST);
    }
    
    private boolean textViewsContainSearchWord(List<WebElement> textViews) {
        logger.info("Text views contain search word");
        for (WebElement textView : textViews) {
            String text = textView.getText();
            if (text.contains(reservation.getDestination())) {
                return true;
            }
        }
        return false;
    }

    public void selectFirstItem() {
        logger.info("Select first item");
        Utils.getElement(driver, firstItemElement, reservation.getDestination()).click();
    }

    public boolean nextDateIsSelected(String element) {
        logger.info("Next date is selected");
        MobileElement elem = driver.findElement(MobileBy.AccessibilityId(element));
        return elem.isSelected();
    }

    public boolean isSelectDateButtonEnabled() {
        logger.info("Is Select date button enabled ?");
        return selectDateButtonElement.isEnabled();
    }

    public boolean isDateRangeCorrect() throws ParseException {
        logger.info("Is date range correct ?");
        String dateRangeExpected = getFormattedDateRange();
        String dateRange = dateRangeSelectionSummaryElement.getText();
        return dateRange.contains(dateRangeExpected);
    }
    
    public boolean isNumberOfNightsCorrect() throws ParseException {
        logger.info("Is number of nights correct ?");
        String numNightsExpected = getNumberOfNights();
        String numNights = dateRangeSelectionSummaryElement.getText();
        return numNights.contains(numNightsExpected + " nights");
    }
    
    private String getFormattedDateRange() throws ParseException {
        logger.info("Get formatted date range");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d");
        Date start = inputFormat.parse(reservation.getStartDate());
        Date end = inputFormat.parse(reservation.getEndDate());
        String formattedStartDate = outputFormat.format(start);
        String formattedEndDate = outputFormat.format(end);
        return formattedStartDate + " - " + formattedEndDate;
    }
    
    private String getNumberOfNights() throws ParseException {
        logger.info("Get number of nights");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        Date start = format.parse(reservation.getStartDate());
        Date end = format.parse(reservation.getEndDate());
        long differenceInMilliseconds = end.getTime() - start.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);
        return String.valueOf(differenceInDays);
    }


    public boolean isCalendarOpened() {
        logger.info("Is calendar opened ?");
        return calendarElement.isDisplayed();
    }

    public void scrollInCalendar() {
        logger.info("Scroll in calendar");
        Utils.scrollUntilElementFound(
            driver,
            By.id(calendarModaElement),
            MobileBy.AccessibilityId(reservation.getEndDate())
        );
    }

    public void selectStartDate() {
        logger.info("Select start date");
        driver.findElementByAccessibilityId(reservation.getStartDate()).click();
    }

    public void selectEndDate() {
        logger.info("Select end date");
        driver.findElementByAccessibilityId(reservation.getEndDate()).click();
    }

    public void clickOnSelectDatesButton() {
        logger.info("Click on select dates button");
        selectDateButtonElement.click();
    }

    public boolean isDestinationSet() {
        logger.info("Is destination set ?");
        return Utils.getElement(driver, searchElement, reservation.getDestination()).isDisplayed();
    }

    public boolean isDateRangeSet() throws ParseException {
        logger.info("Is date range set ?");
        String dateRangeExpected = getFormattedDate(reservation.getStartDate(), reservation.getEndDate());

        return Utils.getElement(driver, searchElement, dateRangeExpected).isDisplayed();
    }

    public String getFormattedDate(String startDate, String endDate) throws ParseException{
        logger.info("Get formatted date");
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEE, MMM dd");

        Date dateObj1 = originalFormat.parse(startDate);
        Date dateObj2 = originalFormat.parse(endDate);

        String formattedStartDate = targetFormat.format(dateObj1);
        String formattedEndDate = targetFormat.format(dateObj2);
        return formattedStartDate + " - " + formattedEndDate;
    }

    public boolean isNumberQuestsSet() {
        logger.info("Is number quests set ?");
        String quests = reservation.getNumRooms()+" room · "+reservation.getNumAdults()+" adults · "+reservation.getNumChild()+" child";
        return Utils.getElement(driver, searchElement, quests).isDisplayed();
    }

    public void clickOnOccupancyField() {
        logger.info("Click on occupancy field");
        Utils.getElement(driver, occupancyFieldElement, String.valueOf(reservation.getNumAdults())).click();
    }

    public boolean isRoomAndGuestModalOpened() {
        logger.info("Is room and guest modal opened ?");
        return roomAndGuestModalElement.isDisplayed();
    }

    public void addAChildGuest() {
        logger.info("Add a child guest");
        increaseChildrenGuestElement.click();
    }

    public boolean isAgeOfChildModalOpened() {
        logger.info("Is age of child modal opened ?");
        return ageOfChildModalElement.isDisplayed();
    }

    public void scrollIntoAgeChildModal() {
        logger.info("Scroll into age child modal");
        Utils.scrollUntilElementFound(
            driver,
            By.id(agePickerViewElement),
            Utils.getElementBy(ageScrollElement, reservation.getAgeChildren()[0])
        );
    }

    public void selectDesiredAgeOption() {
        logger.info("Select desired age option");
        Utils.getElement(driver, ageScrollElement, reservation.getAgeChildren()[0]).click();
    }

    public boolean isOKButtonEnabled(){
        logger.info("Is OK button enabled ?");
        return okButtonElement.isEnabled();
    }

    public void clickOnOKbutton(){
        logger.info("Click on OK button");
        okButtonElement.click();
    }

    public boolean isChildrensAgesHeaderDisplayed() {
        logger.info("Is childrens ages header displayed ?");
        return childrensAgesHeaderElement.isDisplayed();
    }

    public boolean isQuantityOfChildrenCorrect() {
        logger.info("Is quantity of children correct ?");
        List<MobileElement> elems = driver.findElements(By.id(numberOfChildrenElements));   
        return String.valueOf(elems.size()).equals(String.valueOf(reservation.getNumChild()));
    }

    public WebElement[] getChildrenElements(int childIndex) {
        logger.info("Get children elements");
        WebElement linearLayout = numberOfChildrenElement.findElements(By.className(childrenLayoutElement)).get(childIndex);
    
        WebElement childNumElement = linearLayout.findElement(By.xpath(childrenNumElement));
        WebElement childAgeElement = linearLayout.findElement(By.xpath(childrenAgeElement));
    
        return new WebElement[]{childNumElement, childAgeElement};
    }
    
    public boolean isChildrensAgeSelectedCorrect() {
        logger.info("Is childrens age selected correct ?");
        boolean childDataCorrect = true;
    
        for (int i = 0; i < reservation.getNumChild(); i++) {
            String childNumExpected = "Child " + (i + 1);
            String childAgeExpected = reservation.getAgeChildren()[i] + " years old";
    
            WebElement[] childrenElements = getChildrenElements(i);
            WebElement childNumElement = childrenElements[0];
            WebElement childAgeElement = childrenElements[1];
    
            if (!childNumElement.getText().equals(childNumExpected) || !childAgeElement.getText().equals(childAgeExpected)) {
                childDataCorrect = false;
                break;
            }
        }
    
        return childDataCorrect;
    }

    public boolean isApplyButtonEnabled() {
        logger.info("Is apply button enabled ?");
        return applyButtonElement.isEnabled();
    }

    public void clickOnApplyButton(){
        logger.info("Click on apply button");
        applyButtonElement.click();
    }

    public boolean isSearchButtonEnabled() {
        logger.info("Is search button enabled ?");
        return searchButtonElement.isEnabled();
    }

    public void clickOnSearchButton(){
        logger.info("Click on search button");
        searchButtonElement.click();
    }

    

}  