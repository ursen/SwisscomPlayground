package StepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static factory.DriverFactory.getNewChromeDriver;
import static factory.DriverFactory.getNewFirefoxDriver;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPageSteps extends BaseSteps {

    // URL is here because this class is for the test page, base class only gets URL from the outside
    private final String baseUrl = "https://automationintesting.com/selenium/testpage/";

    // This is a way how we could go for different browsers,
    // Alternatively we could set up the driver in a @Before method, but then we would need to switch browsers in a different way
    @Given("I am on the selenium test page using Chrome")
    public void i_am_on_the_selenium_test_page_using_chrome() {
        SetupChromeDriverForPageUrl(baseUrl);
    }

    @Given("I am on the selenium test page using Firefox")
    public void i_am_on_the_selenium_test_page_using_firefox() {
        SetupFirefoxDriverForPageUrl(baseUrl);
    }

    @When("I fill in the test form with valid details")
    public void i_fill_in_the_test_form_with_valid_details() {

        SendTextToTextFieldById("Urs", "firstname");
        SendTextToTextFieldById("Rust", "surname");

        FindSelectByIdAndSelectEntryByVisibleText("Male", "gender");

        FindCheckBoxByIdAndClickIt("red");

        // Xpath stuff breaks easily as soon as the structure of the page is changed, by id is better but this field does not have a unique id
        // Could also do by css class but this can give us multiple elements IF there are multiple elements with this class
        SendTextToTextFieldByXpath("This is a simple test of this page.", "/html/body/div/div/div[2]/div/form/div[5]/div/label/textarea");

        // Continents is a multi select
        FindSelectByIdAndSelectEntryByVisibleText("Europe", "continent");
        FindSelectByIdAndSelectEntryByVisibleText("Asia", "continent");

        FindCheckBoxByIdAndClickIt("checkbox1");
        FindCheckBoxByIdAndClickIt("checkbox2");
    }

    @When("I submit my data with the do nothing button")
    public void i_submit_my_data_with_the_do_nothing_button() {
        WebElement submitButton = driver.findElement(By.id("submitbutton"));
        submitButton.click();
    }

    @When("I select the red check box")
    public void i_select_the_red_check_box() {
        FindCheckBoxByIdAndClickIt("red");
    }

    @When("I select the blue check box")
    public void i_select_the_blue_check_box() {
        FindCheckBoxByIdAndClickIt("blue");
    }

    @When("I select Europe")
    public void i_select_europe() {
        SelectCountry("Europe");
    }

    @When("I select North America")
    public void i_select_north_america() {
        SelectCountry("North America");
    }

    @When("I select Asia")
    public void i_select_asia() {
        SelectCountry("Asia");
    }

    @Then("I should see my input on the page")
    public void i_should_see_my_input_on_the_page() {

        AssertThatTextFieldIsNotEmptyAndHasTextById("firstname", "Urs");
        AssertThatTextFieldIsNotEmptyAndHasTextById("surname", "Rust");

        AssertThatElementInSelectIsSelectedByCssSelector("option[value=male]");

        AssertThatCheckBoxIsSelectedById("red");

        AssertThatTextFieldIsNotEmptyAndHasTextByXPath("/html/body/div/div/div[2]/div/form/div[5]/div/label/textarea", "This is a simple test of this page.");

        AssertThatElementInSelectIsSelectedBySelectIdAndVisibleText("continent", "Asia");
        AssertThatElementInSelectIsSelectedBySelectIdAndVisibleText("continent", "Europe");

        AssertThatCheckBoxIsSelectedById("checkbox1");
        AssertThatCheckBoxIsSelectedById("checkbox2");
    }

    @Then("I should not see my input on the page")
    public void i_should_not_see_my_input_on_the_page() {

        AssertThatTextFieldIsEmptyById("firstname");
        AssertThatTextFieldIsEmptyById("surname");

        WebElement maleSelectElement = driver.findElement(By.cssSelector("option[value=male]"));
        assertFalse(maleSelectElement.isSelected());

        AssertThatCheckBoxIsNotSelectedById("red");

        AssertThatTextFieldIsEmptyByXPath("/html/body/div/div/div[2]/div/form/div[5]/div/label/textarea");

        WebElement continentsVisitedSelection = driver.findElement(By.id("continent"));
        Select continentsSelect = new Select(continentsVisitedSelection);
        // The continent multi select does not have ids for the individual selections, so we just get them all and foreach them.
        // We want to test if all of them are selected so this is fine. For individual tests we could go for value or visible text again
        var options = continentsSelect.getOptions();
        options.forEach(option -> assertFalse(option.isSelected()));

        AssertThatCheckBoxIsNotSelectedById("checkbox1");
        AssertThatCheckBoxIsNotSelectedById("checkbox2");
    }


    @Then("The blue check box is selected and the red checkbox is not selected")
    public void the_blue_check_box_is_selected_and_the_red_checkbox_is_not_selected() {
        AssertThatCheckBoxIsSelectedById("blue");
        AssertThatCheckBoxIsNotSelectedById("red");
    }

    // Cleanup after each scenario even if it fails to make sure there are no drivers left behind
    @After
    public void teardown() {
        driver.quit();
    }

    // This is here because country selection is a page specific thing that is probably not available on other pages
    private void SelectCountry(String country) {
        FindSelectByIdAndSelectEntryByVisibleText(country, "continent");
    }
}
