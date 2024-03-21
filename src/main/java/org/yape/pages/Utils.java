package org.yape.pages;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Utils {
    
    public static void scrollUntilElementFound(AppiumDriver<MobileElement> driver, By modalSelector, By elementToFindSelector){
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.MILLISECONDS);

        while (!isElementVisible(driver, elementToFindSelector)) {
            scrollDown(driver, modalSelector);
        }   

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
    }

    public static boolean isElementVisible(WebDriver driver, By elementToFindSelector) {
        List<WebElement> elements = driver.findElements(elementToFindSelector);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }
    
    public static void scrollDown(WebDriver driver, By modalSelector) {
        WebElement modal = driver.findElement(modalSelector);
    
        int startX = modal.getLocation().getX() + (modal.getSize().getWidth() / 2);
        int startY = modal.getLocation().getY() + (modal.getSize().getHeight() / 2);
        int endY = startY - (modal.getSize().getHeight() / 3);

        TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
    
        touchAction.press(PointOption.point(startX, startY))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
            .moveTo(PointOption.point(startX, endY))
            .release()
            .perform();
    }

    
    public static WebElement getElement(WebDriver driver, String stringElement, String value) {
        String xpath = stringElement.replace("REPLACE-TEXT", value);
        return driver.findElement(By.xpath(xpath));
    }

    public static By getElementBy(String stringElement, String value) {
        String xpath = stringElement.replace("REPLACE-TEXT", value);
        return By.xpath(xpath);
    }

    public static List<WebElement> getElements(WebDriver driver, String stringElement, String value) {
        String xpath = stringElement.replace("REPLACE-TEXT", value);
        return driver.findElements(By.xpath(xpath));
    }

    public static List<By> getElementsBy(String stringElement, String value) {
        String xpath = stringElement.replace("REPLACE-TEXT", value);
        return Arrays.asList(By.xpath(xpath));
    }
    
    public static void selectItemAtIndex(AppiumDriver<MobileElement> driver, By modalSelector, int index) {
        int count = 0;
        int positionInView = 0;
        String xpathTemplate = "//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[%s]";
    
        while (count < index) {
            List<MobileElement> items = driver.findElements(modalSelector);
            for (MobileElement item : items) {
                String resourceId = item.getAttribute("resource-id");
                if (resourceId.contains("banner")) {
                    continue; 
                }
                if (count == index) {
                    positionInView = (count % 3 == 0) ? 3 : count % 3;
                    String itemXpath = String.format(xpathTemplate, positionInView);
                    By itemSelector = By.xpath(itemXpath);
                    item = driver.findElement(itemSelector);
                    if (item.isEnabled()) {
                        item.click();
                    } else {
                        positionInView++;
                        itemXpath = String.format(xpathTemplate, positionInView);
                        itemSelector = By.xpath(itemXpath);
                        item = driver.findElement(itemSelector);
                        item.click();
                    }
                    return;
                }
                count++;
            }
            Utils.scrollDown(driver, modalSelector);
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }

    public static String getNextDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(calendar.getTime());
    }

}
