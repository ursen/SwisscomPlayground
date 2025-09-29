package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

// This is a simple example how we could move some setup code into an external factory
// This factory creates the driver and adds our base configuration
// but has no idea about the URL that we want to navigate to
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
