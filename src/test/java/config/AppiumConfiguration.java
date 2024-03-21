package config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.io.IOException;

public class AppiumConfiguration {
    private DesiredCapabilities capabilities;
    private JsonObject config;

    public AppiumConfiguration() {
        Gson gson = new Gson();
        try {            
            config = gson.fromJson(new FileReader(Urls.APPIUM_CONFIG), JsonObject.class);
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", config.get("platformName").getAsString());
            capabilities.setCapability("platformVersion", config.get("platformVersion").getAsString());
            capabilities.setCapability("deviceName", config.get("deviceName").getAsString());
            capabilities.setCapability("automationName", config.get("automationName").getAsString());
            capabilities.setCapability("app", config.get("app").getAsString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Appium configuration", e);
        }
    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public String getServerUrl() {
        return config.get("serverUrl").getAsString();
    }
}