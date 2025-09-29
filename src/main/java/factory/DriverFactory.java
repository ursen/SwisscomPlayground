package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver getNewChromeDriver() {
        return configureDriver(new ChromeDriver());
    }

    public static WebDriver getNewFirefoxDriver() {
        return configureDriver(new FirefoxDriver());
    }

    private static WebDriver configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
