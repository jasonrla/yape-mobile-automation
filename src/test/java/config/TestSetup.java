package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.yape.model.Reservation;
import org.yape.model.User;
import org.yape.pages.SignInPage;
import org.yape.pages.PropertyDetailsPage;
import org.yape.pages.HomePage;
import org.yape.pages.SearchResultsPage;
import org.yape.pages.RoomSelectionPage;
import org.yape.pages.RegistrationPage;
import org.yape.pages.BookingOverviewPage;
import org.yape.pages.FinishBooking;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestSetup {
    private AndroidDriver<MobileElement> driver;
    private HomePage homePage;
    private SignInPage signInPage;
    private SearchResultsPage searchResultsPage;
    private PropertyDetailsPage propertyDetailsPage;
    private RoomSelectionPage roomSelectionPage;
    private RegistrationPage registrationPage;
    private BookingOverviewPage bookingOverviewPage;
    private FinishBooking finishBooking;

    public TestSetup() throws MalformedURLException, URISyntaxException, FileNotFoundException {
        setupDriver();
        JsonObject jsonObject = readJsonFile(Urls.DATA);
        User user = createUser(jsonObject);
        Reservation reservation = createReservation(jsonObject);
        setupPages(user, reservation);
    }

    private void setupDriver() throws MalformedURLException, URISyntaxException {
        AppiumConfiguration config = new AppiumConfiguration();
        DesiredCapabilities capabilities = config.getCapabilities();
        String serverUrl = config.getServerUrl();
        driver = new AndroidDriver<>(new URI(serverUrl).toURL(), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private JsonObject readJsonFile(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(filePath), JsonObject.class);
    }

    private User createUser(JsonObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.get("user"), User.class);
    }

    private Reservation createReservation(JsonObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.get("reservation"), Reservation.class);
    }

    private void setupPages(User user, Reservation reservation) {
        homePage = new HomePage(driver, reservation);
        signInPage = new SignInPage(driver);
        searchResultsPage = new SearchResultsPage(driver, reservation);
        propertyDetailsPage = new PropertyDetailsPage(driver);
        roomSelectionPage = new RoomSelectionPage(driver);
        registrationPage = new RegistrationPage(driver, user, reservation);
        bookingOverviewPage = new BookingOverviewPage(driver, reservation);
        finishBooking = new FinishBooking(driver, user, reservation);
    }

    public AndroidDriver<MobileElement> getDriver() {
        return driver;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public SignInPage getSignInPage() {
        return signInPage;
    }

    public SearchResultsPage getSearchResultsPage() {
        return searchResultsPage;
    }

    public PropertyDetailsPage getPropertyDetailsPage() {
        return propertyDetailsPage;
    }

    public RoomSelectionPage getRoomSelectionPage() {
        return roomSelectionPage;
    }

    public RegistrationPage getRegistrationPage() {
        return registrationPage;
    }

    public BookingOverviewPage getBookingOverviewPage() {
        return bookingOverviewPage;
    }

    public FinishBooking getFinishBooking() {
        return finishBooking;
    }
}
