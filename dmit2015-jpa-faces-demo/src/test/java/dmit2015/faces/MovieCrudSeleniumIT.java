package dmit2015.faces;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieCrudSeleniumIT {

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
    void afterEachTTestMethod() {

    }

    private void setValue(String id, String value) {
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(value);
    }

    private void setDateValue(String id, String value) {
        WebElement element = driver.findElement(By.id(id));
        element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        element.sendKeys(Keys.chord(Keys.BACK_SPACE));
        element.clear();
        element.sendKeys(value);
        element.sendKeys(Keys.chord(Keys.TAB));
    }

    @Order(1)
    @ParameterizedTest
    @CsvSource({
            "Java 17 Release Party,Action,G,9.14,2021-09-14",
    })
    void shouldCreateMovie(String title, String genre, String rating, String price, String releaseDate) throws InterruptedException {
        // Open a browser and navigate the page to create a new movie
        driver.get("http://localhost:8080/movies/create.xhtml");
        assertEquals("Movie - Create", driver.getTitle());

        // Assign form field input values
        setValue("title",title);
        setValue("genre",genre);
        setValue("rating",rating);
        setValue("price",price);
        //setValue("releaseDate_input",releaseDate);
//        setValue("releaseDate",releaseDate);
        setDateValue("releaseDate_input", releaseDate);

        Thread.sleep(1000);

        // Maximize the browser window so we can see the data being inputted
        driver.manage().window().maximize();
        // Find the create button and click on it
        driver.findElement(By.id("createButton")).click();

        // Wait for 3 seconds and verify navigate has been redirected to the listing page
        var wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        // Verify the title of the page
        assertEquals("Movie - List", driver.getTitle());
        // Verify the feedback message is displayed in the page
        String feedbackMessage = facesMessages.getText();
        assertThat(feedbackMessage, containsString("Create was successful."));
        // The primary key of the entity is at the end of the feedback message after the period
        final int indexOfPrimaryKeyValue = feedbackMessage.indexOf(".") + 2;
        // Extract the primary key for re-use if we need to edit or delete the entity
        sharedEditId = Long.parseLong(feedbackMessage.substring(indexOfPrimaryKeyValue));
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({
            "Java 17 Release Party,Action,G,$9.14,'Sep. 14, 2021'",
    })
    void shouldListAllMovies(
            String expectedLastRowTitle,
            String expectedLastRowGenre,
            String expectedLastRowRating,
            String expectedLastRowPrice,
            String expectedLastRowReleaseDate) {
        // Open a browser and navigate to the page to list entities
        driver.get("http://localhost:8080/movies/index.xhtml");
        assertEquals("Movie - List", driver.getTitle());

        // Check the number of rows in the entity listing table
        int lastRow = (driver.findElements(By.xpath("//table[@role='grid']/tbody/tr")).size());

        // Define the XPATH for locating the each column of the last row
        final String lastRowTitleColumnXpathExpression = String.format("//table[@role='grid']/tbody/tr[%d]/td[1]", lastRow);
        final String lastRowGenreColumnXpathExpression = String.format("//table[@role='grid']/tbody/tr[%d]/td[2]", lastRow);
        final String lastRowRatingColumnXpathExpression = String.format("//table[@role='grid']/tbody/tr[%d]/td[3]", lastRow);
        final String lastRowPriceColumnXpathExpression = String.format("//table[@role='grid']/tbody/tr[%d]/td[4]", lastRow);
        final String lastRowReleaseDateColumnXpathExpression = String.format("//table[@role='grid']/tbody/tr[%d]/td[5]", lastRow);

        // Get the text for each column in the last row
        String lastRowTitle = driver.findElement(By.xpath(lastRowTitleColumnXpathExpression)).getText();
        String lastRowGenre = driver.findElement(By.xpath(lastRowGenreColumnXpathExpression)).getText();
        String lastRowRating = driver.findElement(By.xpath(lastRowRatingColumnXpathExpression)).getText();
        String lastRowPrice = driver.findElement(By.xpath(lastRowPriceColumnXpathExpression)).getText();
        String lastRowReleaseDate = driver.findElement(By.xpath(lastRowReleaseDateColumnXpathExpression)).getText();

        // Verify each column of the last row
        assertEquals(expectedLastRowTitle, lastRowTitle);
        assertEquals(expectedLastRowGenre, lastRowGenre);
        assertEquals(expectedLastRowRating, lastRowRating);
        assertEquals(expectedLastRowPrice, lastRowPrice);
        assertEquals(expectedLastRowReleaseDate, lastRowReleaseDate);

        // Verify that clicking on the edit link navigates to the Edit Movie page
        driver.findElements(By.xpath("//a[contains(@id,'editLink')]")).get(0).click();
        assertEquals("Movie - Edit", driver.getTitle());
        // Navigate back to the listing page
        driver.navigate().back();

        // Verify that clicking on the details link navigates to the Edit Details page
        driver.findElements(By.xpath("//a[contains(@id,'detailsLink')]")).get(0).click();
        assertEquals("Movie Details", driver.getTitle());
        // Navigate back to the listing page
        driver.navigate().back();

        // Verify that clicking on the delete link navigates to the Delete Movie page
        driver.findElements(By.xpath("//a[contains(@id,'deleteLink')]")).get(0).click();
        assertEquals("Movie - Delete", driver.getTitle());
        // Navigate back to the listing page
        driver.navigate().back();
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource({
            "Java 17 Release Party,Action,G,$9.14,2021-09-14",
    })
    void shouldShowMovieDetails(String expectedTitle, String expectedGenre, String expectedRating, String expectedPrice, String expectedReleaseDate) {
        driver.get("http://localhost:8080/movies/index.xhtml");
        assertEquals("Movie - List", driver.getTitle());

        int tableRowCount = (driver.findElements(By.xpath("//table[@role='grid']/tbody/tr")).size());
        int lastRowIndex = tableRowCount - 1;

        driver.findElements(By.xpath("//a[contains(@id,'detailsLink')]")).get(lastRowIndex).click();
        assertEquals("Movie Details", driver.getTitle());

        var actualTitle = driver.findElement(By.id("title")).getText();
        var actualGenre = driver.findElement(By.id("genre")).getText();
        var actualRating = driver.findElement(By.id("rating")).getText();
        var actualPrice = driver.findElement(By.id("price")).getText();
        var actualReleaseDate = driver.findElement(By.id("releaseDate")).getText();

        assertEquals(expectedTitle, actualTitle);
        assertEquals(expectedGenre, actualGenre);
        assertEquals(expectedRating, actualRating);
        assertEquals(expectedReleaseDate, actualReleaseDate);
        assertEquals(expectedPrice, actualPrice);

    }

    @Order(4)
    @ParameterizedTest
    @CsvSource({
            "Java 19 Release Party,Action,G,9.20,2022-09-20",
    })
    void shouldEditMovie(String updatedTitle, String updatedGenre, String updatedRating, String updatedPrice, String updatedReleaseDate) {
        driver.get("http://localhost:8080/movies/index.xhtml");
        assertEquals("Movie - List", driver.getTitle());

        int tableRowCount = (driver.findElements(By.xpath("//table[@role='grid']/tbody/tr")).size());
        int lastRowIndex = tableRowCount - 1;

        driver.findElements(By.xpath("//a[contains(@id,'editLink')]")).get(lastRowIndex).click();
        assertEquals("Movie - Edit", driver.getTitle());

        setValue("title",updatedTitle);
        setValue("genre",updatedGenre);
        setValue("rating",updatedRating);
        setValue("price",updatedPrice);
//        var releaseDateElement = driver.findElement(By.id("releaseDate_input"));
//        releaseDateElement.click();
//        releaseDateElement.sendKeys(Keys.CONTROL,"a");
//        releaseDateElement.sendKeys(updatedReleaseDate);
        setDateValue("releaseDate_input", updatedReleaseDate);

        driver.manage().window().maximize();
        driver.findElement(By.id("updateButton")).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(11));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        assertEquals("Movie - List", driver.getTitle());
        assertThat(facesMessages.getText(), containsString("Update was successful."));
    }

    @Order(5)
    @Test
    void shouldDeleteMovie() {
        driver.get("http://localhost:8080/movies/index.xhtml");
        assertEquals("Movie - List", driver.getTitle());

        int tableRowCount = (driver.findElements(By.xpath("//table[@role='grid']/tbody/tr")).size());
        int lastRowIndex = tableRowCount - 1;
        driver.findElements(By.xpath("//a[contains(@id,'deleteLink')]")).get(lastRowIndex).click();
        assertEquals("Movie - Delete", driver.getTitle());

        driver.findElement(By.id("deleteButton")).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        var yesConfirmationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-confirmdialog-yes")));
        yesConfirmationButton.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        assertEquals("Movie - List", driver.getTitle());
        assertThat(facesMessages.getText(), containsString("Delete was successful."));
    }

}