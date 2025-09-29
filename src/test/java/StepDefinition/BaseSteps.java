package StepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static factory.DriverFactory.getNewChromeDriver;
import static factory.DriverFactory.getNewFirefoxDriver;
import static org.junit.jupiter.api.Assertions.*;

// Base class that has testing code that should work on every page
public class BaseSteps {
    protected WebDriver driver;

    // Setup

    protected void SetupChromeDriverForPageUrl(String baseUrl) {
        driver = getNewChromeDriver();
        driver.get(baseUrl);
    }

    protected void SetupFirefoxDriverForPageUrl(String baseUrl) {
        driver = getNewFirefoxDriver();
        driver.get(baseUrl);
    }

    // Send input

    protected void SendTextToTextFieldById(String text, String id) {
        WebElement textField = driver.findElement(By.id(id));
        textField.sendKeys(text);
    }

    protected void SendTextToTextFieldByXpath(String text, String xpath) {
        WebElement textField = driver.findElement(By.xpath(xpath));
        textField.sendKeys(text);
    }

    protected void FindSelectByIdAndSelectEntryByVisibleText(String text, String id) {
        WebElement selectElement = driver.findElement(By.id(id));
        Select select = new Select(selectElement);
        select.selectByVisibleText(text);
    }

    protected void FindSelectByIdAndDeSelectEntryByVisibleText(String text, String id) {
        WebElement selectElement = driver.findElement(By.id(id));
        Select select = new Select(selectElement);
        select.deselectByVisibleText(text);
    }

    protected void ClickSelectElementByCssSelector(String cssSelector) {
        WebElement selectElement = driver.findElement(By.cssSelector(cssSelector));
        selectElement.click();
    }

    protected void FindCheckBoxByIdAndClickIt(String id) {
        WebElement checkbox = driver.findElement(By.id(id));
        checkbox.click();
    }

    // Assert input

    protected void AssertThatTextFieldIsNotEmptyAndHasTextById(String id, String text) {
        WebElement textField = driver.findElement(By.id(id));
        AssertThatTextFieldIsNotEmptyAndHasText(textField, text);
    }

    protected void AssertThatTextFieldIsNotEmptyAndHasTextByXPath(String xpath, String text) {
        WebElement textField = driver.findElement(By.xpath(xpath));
        AssertThatTextFieldIsNotEmptyAndHasText(textField, text);
    }

    // Not visible from subclasses but used in the methods that are visible to subclasses
    private void AssertThatTextFieldIsNotEmptyAndHasText(WebElement textField, String text) {
        var textFieldContent = textField.getAttribute("value");
        assert textFieldContent != null;
        assertTrue(textFieldContent.contains(text));
    }

    protected void AssertThatCheckBoxIsSelectedById(String id) {
        WebElement checkBox = driver.findElement(By.id(id));
        assertTrue(checkBox.isSelected());
    }

    protected void AssertThatCheckBoxIsNotSelectedById(String id) {
        WebElement checkBox = driver.findElement(By.id(id));
        assertFalse(checkBox.isSelected());
    }

    protected void AssertThatElementInSelectIsSelectedByCssSelector(String cssSelector) {
        WebElement selectElement = driver.findElement(By.cssSelector(cssSelector));
        assertTrue(selectElement.isSelected());
    }
    protected void AssertThatElementInSelectIsNotSelectedByCssSelector(String cssSelector) {
        WebElement selectElement = driver.findElement(By.cssSelector(cssSelector));
        assertFalse(selectElement.isSelected());
    }

    // This will not work fur multi selects because this checks the first selected option,
    // In a multi select we might be looking for the second or third or last
    protected void AssertThatElementInSelectIsSelectedBySelectIdAndVisibleText(String id, String text) {
        WebElement selectElement = driver.findElement(By.id(id));
        Select select = new Select(selectElement);
        var option = select.getFirstSelectedOption();
        assertEquals(option.getText(), text);
    }

    protected void AssertThatNoElementInSelectIsSelectedBySelectId(String id) {
        WebElement continentsVisitedSelection = driver.findElement(By.id(id));
        Select continentsSelect = new Select(continentsVisitedSelection);
        var options = continentsSelect.getOptions();
        options.forEach(option -> assertFalse(option.isSelected()));
    }

    // Assert not inout

    protected void AssertThatTextFieldIsEmptyById(String id) {
        WebElement textField = driver.findElement(By.id(id));
        AssertThatTextFieldIsEmpty(textField);
    }

    protected void AssertThatTextFieldIsEmptyByXPath(String xpath) {
        WebElement textField = driver.findElement(By.xpath(xpath));
        AssertThatTextFieldIsEmpty(textField);
    }

    private void AssertThatTextFieldIsEmpty(WebElement textField) {
        var textFieldContent = textField.getAttribute("value");
        assert textFieldContent != null;
        assertTrue(textFieldContent.isEmpty());
    }

}
