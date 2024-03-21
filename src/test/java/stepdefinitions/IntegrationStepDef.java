package stepdefinitions;

import io.cucumber.java.en.And;
import org.junit.Assert;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.yape.pages.SignInPage;
import org.yape.pages.HomePage;
import org.yape.pages.SearchResultsPage;
import org.yape.pages.PropertyDetailsPage;
import org.yape.pages.RoomSelectionPage;
import org.yape.pages.RegistrationPage;
import org.yape.pages.BookingOverviewPage;
import org.yape.pages.FinishBooking;

import config.TestSetup;

public class IntegrationStepDef {
    private AppiumDriver<MobileElement> driver;
    private SignInPage signInPage;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private PropertyDetailsPage propertyDetailsPage;
    private RoomSelectionPage roomSelectionPage;
    private RegistrationPage registrationPage;
    private BookingOverviewPage bookingOverviewPage;
    private FinishBooking finishBooking;

    private String stayDetails;
    private String totalAmount;
    private String taxesAmount;

    @Before
    public void setUp() throws Exception {
        TestSetup setup = new TestSetup();
        driver = setup.getDriver();
        signInPage = setup.getSignInPage();
        homePage = setup.getHomePage();
        searchResultsPage = setup.getSearchResultsPage();
        propertyDetailsPage = setup.getPropertyDetailsPage();
        roomSelectionPage = setup.getRoomSelectionPage();
        registrationPage = setup.getRegistrationPage();
        bookingOverviewPage = setup.getBookingOverviewPage();
        finishBooking = setup.getFinishBooking();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("I am on the sign in page and decide not to sign in")
    public void iAmOnTheSignInPageAndDecideNotToSignIn() {
        signInPage.closeLoginPage();
    }

    @Then("The main page should be opened")
    public void theMainPageShouldBeOpened() {
        Assert.assertEquals(true, homePage.isMainPageOpened());
    }

    @Given("I am on the main in page")
    public void iAmOnTheMainInPage() {
        Assert.assertEquals(true, homePage.isSearchButtonEnabled());
    }

    @When("I enter my destination and select the first item")
    public void iEnterMyDestinationAndSelectTheFirstItem() {
        homePage.enterYourDestination();
        Assert.assertEquals(true, homePage.isDestinationModalOpened());

        homePage.enterDestinationToBeSearched();
        Assert.assertEquals(true, homePage.allItemsContainSearchWord());

        homePage.selectFirstItem();
    }

    @And("I set the date range")
    public void iSetTheDateRange() throws ParseException {
        Assert.assertEquals(true, homePage.isCalendarOpened());
        homePage.scrollInCalendar();

        homePage.selectStartDate();
        Assert.assertEquals(false, homePage.isSelectDateButtonEnabled());

        homePage.selectEndDate();
        Assert.assertEquals(true, homePage.isSelectDateButtonEnabled());

        Assert.assertEquals(true, homePage.isDateRangeCorrect());
        Assert.assertEquals(true, homePage.isNumberOfNightsCorrect());

        homePage.clickOnSelectDatesButton();

        Assert.assertEquals(true, homePage.isDestinationSet());
        Assert.assertEquals(true, homePage.isDateRangeSet());
    }

    @And("I set the room details")
    public void iSetTheRoomDetails() {
        homePage.clickOnOccupancyField();
        Assert.assertEquals(true, homePage.isRoomAndGuestModalOpened());

        homePage.addAChildGuest();
        Assert.assertEquals(true, homePage.isAgeOfChildModalOpened());
        Assert.assertEquals(false, homePage.isOKButtonEnabled());

        homePage.scrollIntoAgeChildModal();
        homePage.selectDesiredAgeOption();

        Assert.assertEquals(true, homePage.isOKButtonEnabled());
        homePage.clickOnOKbutton();

        Assert.assertEquals(true, homePage.isChildrensAgesHeaderDisplayed());
        Assert.assertEquals(true, homePage.isQuantityOfChildrenCorrect());
        Assert.assertEquals(true, homePage.isChildrensAgeSelectedCorrect());

        Assert.assertEquals(true, homePage.isApplyButtonEnabled());
        homePage.clickOnApplyButton();

        Assert.assertEquals(true, homePage.isNumberQuestsSet());

    }

    @And("I search for properties and select the second option")
    public void iSearchForPropertiesAndSelectTheSecondOption() {

        Assert.assertEquals(true, homePage.isSearchButtonEnabled());
        homePage.clickOnSearchButton();

        Assert.assertEquals(true, searchResultsPage.isPropertiesListDisplayed());
        searchResultsPage.scrollUntilDesiredItem();

        stayDetails = searchResultsPage.extractStayDetailsText();
        totalAmount = searchResultsPage.extractAmountText();
        taxesAmount = searchResultsPage.extractTaxesText();

        searchResultsPage.selectDesiredOption();

        Assert.assertEquals(true, propertyDetailsPage.isSelectRoomModalOpened());

        Assert.assertEquals(true, propertyDetailsPage.isTotalAmountCorrect(totalAmount));
        Assert.assertEquals(true, propertyDetailsPage.isTaxesAmountCorrect(taxesAmount));

        propertyDetailsPage.clickOnSelectRoomsButton();
        roomSelectionPage.reserveButton();

    }

    @And("I fill in my personal details")
    public void iFillInMyPersonalDetails() {

        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonEnabled());

        registrationPage.enterFirstName();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.enterLastName();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.enterEmail();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.enterAddress();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.enterZipCode();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.enterCity();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.scrollForm();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.selectCountry();
        Assert.assertEquals(true, registrationPage.isAddMissingDetailsButtonDisplayed());

        registrationPage.enterPhoneNumber();
        Assert.assertEquals(false, registrationPage.isAddMissingDetailsButtonDisplayed());

        Assert.assertEquals(true, registrationPage.isNextStepButtonDisplayed());

        registrationPage.selectPurpouseOption();

        Assert.assertEquals(true, registrationPage.isTotalAmountInFillInfoDetailsCorrect(totalAmount));
        Assert.assertEquals(true, registrationPage.isTaxesAmountInFillInfoDetailsCorrect(taxesAmount));

    }

    @And("I proceed to the next step")
    public void iProceedToTheNextStep() throws Exception {

        Assert.assertEquals(true, registrationPage.isNextStepButtonEnabled());
        registrationPage.clickOnNextStepButton();

        Assert.assertEquals(true, bookingOverviewPage.isCheckInDateCorrect());
        Assert.assertEquals(true, bookingOverviewPage.isCheckOutDateCorrect());

        Assert.assertEquals(true, bookingOverviewPage.isBookingOverviewTotalAmountCorrect(totalAmount));
        Assert.assertEquals(true, bookingOverviewPage.isBookingOverviewTaxesAmountCorrect(taxesAmount));

        Assert.assertEquals(true, bookingOverviewPage.isTotalAmountFooterCorrect(totalAmount));
        Assert.assertEquals(true, bookingOverviewPage.isTaxesAmountFooterCorrect(taxesAmount));

        bookingOverviewPage.clickOnFinalStepButton();

    }

    @And("I enter invalid card details")
    public void iEnterInvalidCardDetails() throws Exception {

        finishBooking.enterCardNumber();
        Assert.assertEquals(true, finishBooking.isHolderNameCorrect());
        finishBooking.enterExpirationDate();

        Assert.assertEquals(true, finishBooking.isTotalAmountFooterCorrect(totalAmount));
        Assert.assertEquals(true, finishBooking.isTaxesAmountFooterCorrect(taxesAmount));

        finishBooking.scrollIntoFinishBookingModal();
        Assert.assertEquals(true, finishBooking.isCheckInDateCorrect());
        Assert.assertEquals(true, finishBooking.isCheckOutDateCorrect());

        finishBooking.scrollUntilAmountDetailsAreFound();

        Assert.assertEquals(true, finishBooking.isNameCorrect());
        Assert.assertEquals(true, finishBooking.isEmailCorrect());
        Assert.assertEquals(true, finishBooking.isAddressCorrect());
        Assert.assertEquals(true, finishBooking.isCityCountryCorrect());
        Assert.assertEquals(true, finishBooking.isPhoneNumberCorrect());
        Assert.assertEquals(true, finishBooking.isTotalAmountCorrect(totalAmount));
        Assert.assertEquals(true, finishBooking.isTaxesAmountCorrect(taxesAmount));
        Assert.assertEquals(true, finishBooking.isBookNowButtonEnabled());

        finishBooking.clickOnBookNowButton();


    }

    @Then("The error message should be {string}")
    public void theErrorMessageShouldBe(String errorMessage) {
        String message = finishBooking.checkErrorMessage();
        Assert.assertEquals(errorMessage, message);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


}