package dmit2015.faces;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieSeleniumIT {

    private static WebDriver driver;

    static Long sharedEditId;

    @BeforeAll
    static void beforeAllTests() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // https://www.omgubuntu.co.uk/2022/04/how-to-install-firefox-deb-apt-ubuntu-22-04
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
    }

    @AfterAll
    static void afterAllTests() {
        driver.quit();
    }

    @BeforeEach
    void beforeEachTestMethod() {

    }

    @AfterEach
    void afterEachTestMethod() {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    private void setValue(String id, String value) {
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(value);
    }

    private void setDateValue(String id, String value) {
        WebElement element = driver.findElement(By.id(id));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.BACK_SPACE));
        element.clear();
        element.sendKeys(value);
        element.sendKeys(Keys.chord(Keys.TAB));
    }

    @Order(3)
    @Test
    void shouldCreate() {
        driver.get("http://localhost:8080/movies/create.xhtml");
        assertEquals("Movie - Create", driver.getTitle());

        // TODO: Assign form field values
        setValue("title", "Step Brothers");
        setValue("releaseDate", "2008-06-18");
        setValue("rating", "G");
        setValue("genre", "Comedy");
        setValue("price", "19.95");


        driver.manage().window().maximize();
        driver.findElement(By.id("createButton")).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        assertEquals("Movie - List", driver.getTitle());
        String feedbackMessage = facesMessages.getText();

        assertThat(feedbackMessage, containsString("Create was successful."));
        final int indexOfPrimaryKeyValue = feedbackMessage.indexOf(".") + 2;
        sharedEditId = Long.parseLong(feedbackMessage.substring(indexOfPrimaryKeyValue));
    }

//    @Order(1)
//    @Test
//    void shouldList() {
//        driver.get("http://localhost:8080/movies/index.xhtml");
//        assertEquals("Movie - List", driver.getTitle());
//
//        // TODO: Compare the column values in the first row of the data table
//        String firstRowColumn1 = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[1]")).getText();
//        String firstRowColumn2 = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[2]")).getText();
//        assertEquals("Expected value for row 1 column 1", firstRowColumn1);
//        assertEquals("Expected value for row 1 column 2", firstRowColumn2);
//        // TODO: Compare the column values in the last row of the data table. You will need to change the row number of the last row
//        String lastRowColumn1 = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[1]")).getText();
//        String lastRowColumn2 = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[2]")).getText();
//        assertEquals("Expected value for last row column 1", lastRowColumn1);
//        assertEquals("Expected value for last row column 2", lastRowColumn2);
//
//        driver.findElements(By.xpath("//a[contains(@id,'editLink')]")).get(0).click();
//        assertEquals("Movie - Edit", driver.getTitle());
//        driver.navigate().back();
//
//        driver.findElements(By.xpath("//a[contains(@id,'detailsLink')]")).get(0).click();
//        assertEquals("Movie - Details", driver.getTitle());
//        driver.navigate().back();
//
//        driver.findElements(By.xpath("//a[contains(@id,'deleteLink')]")).get(0).click();
//        assertEquals("Movie - Delete", driver.getTitle());
//        driver.navigate().back();
//    }
//
//    @Order(4)
//    @Test
//    void shouldEdit() {
//        driver.get("http://localhost:8080/movies/edit.xhtml?editId=" + sharedEditId);
//        assertEquals("Movie - Edit", driver.getTitle());
//
//        // TODO: Set the form field values that you want to change
//        setValue("field1Id", "Field 1 Value");
//        setValue("field2Id", "Field 2 Value");
//
//        driver.manage().window().maximize();
//        driver.findElement(By.id("updateButton")).click();
//
//        var wait = new WebDriverWait(driver, Duration.ofSeconds(11));
//        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
//        assertEquals("Movie - List", driver.getTitle());
//        assertThat(facesMessages.getText(), containsString("Update was successful."));
//    }
//
//    @Order(2)
//    @Test
//    void shouldDetails() {
//        // TODO: change the editId value to a valid entity
//        Long primaryKeyValue = 2;
//        driver.get("http://localhost:8080/movies/details.xhtml?editId=" + primaryKeyValue);
//        assertEquals("Movie - Details", driver.getTitle());
//
//        // TODO: change the form field names and values you are expecting
//        var actualField1Value = driver.findElement(By.id("field1Id")).getText();
//        var actualField2Value = driver.findElement(By.id("field2Id")).getText();
//        assertEquals("Expected field 1 value", actualField1Value);
//        assertEquals("Expected field 2 value", actualField1Value);
//
//    }
//
//    @Order(5)
//    @Test
//    void shouldDelete() {
//        driver.get("http://localhost:8080/movies/delete.xhtml?editId=" + sharedEditId);
//        assertEquals("Movie - Delete", driver.getTitle());
//
//        driver.findElement(By.id("deleteButton")).click();
//
//        var wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//
//        var yesConfirmationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-confirmdialog-yes")));
//        yesConfirmationButton.click();
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
//        assertEquals("Movie - List", driver.getTitle());
//        assertThat(facesMessages.getText(), containsString("Delete was successful."));
//    }

}