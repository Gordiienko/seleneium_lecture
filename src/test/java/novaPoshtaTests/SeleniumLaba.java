package novaPoshtaTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tools.JavaScript;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumLaba {
    private WebDriver driver;
    private JavaScript javaScript;


    @BeforeMethod
    public void setUpBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        javaScript = new JavaScript(driver);
        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();


    }

    @Test(priority = 1)
    public void checkInputsFieldsTest() {
        String exceptedName = "Ievgenii";
        String exceptedEmailAddress = "Gordiienko@dot.com";
        String exceptedCurrentAddress = "Vinnitsa";
        String exceptedPermanentAddress = "Ukraine";
        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();
        driver.findElement(By.id("userName")).sendKeys("Ievgenii");
        driver.findElement(By.id("userEmail")).sendKeys("Gordiienko@dot.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Vinnitsa");
        driver.findElement(By.id("permanentAddress")).sendKeys("Ukraine");
        WebElement submit = driver.findElement(By.id("submit"));
        javaScript.scrollIntoView(submit).click();

        WebElement actualUserName = driver.findElement(By.id("name"));
        String actualResultName = actualUserName.getText();
        assertThat(actualResultName).isEqualTo("Name:" + exceptedName);

        WebElement actualEmailAddress = driver.findElement(By.id("email"));
        String actualResultEmailAddress = actualEmailAddress.getText();
        assertThat(actualResultEmailAddress).isEqualTo("Email:" + exceptedEmailAddress);

        String actualResultCurrentAddress = driver.findElement(By.cssSelector("#output #currentAddress")).getText();
        assertThat(actualResultCurrentAddress).isEqualTo("Current Address :" + exceptedCurrentAddress);

        String actualResultPermanentAddress = driver.findElement(By.cssSelector("#output #permanentAddress")).getText();
        Assertions.assertThat(actualResultPermanentAddress)
                .as("the filled data the same as appears below").isEqualTo("Permananet Address :" + exceptedPermanentAddress);


    }

    @Test(priority = 2)
    public void checkBoxTest() {
        String exceptedResult = "wordFile";
        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//li[@id='item-1']//span[text()='Check Box']")).click();
        driver.findElement(By.xpath("//button[@class='rct-collapse rct-collapse-btn']")).click();
        driver.findElement(By.xpath("//label[@for='tree-node-downloads']/../button")).click();
        driver.findElement(By.xpath("//label[@for='tree-node-wordFile']")).click();
        String actualResultSelected = driver.findElement(By.xpath("//div[@id='result']/span[@class='text-success']")).getText();
        Assertions.assertThat(actualResultSelected).as("You must selected :wordFile").isEqualTo(exceptedResult);

    }

    @Test(priority = 3)
    public void checkButtonsTest() {
        String exceptedResultOfClick = "You have done a dynamic click";
        String exceptedResultOfRightClick = "You have done a right click";
        String exceptedResultDoubleClick = "You have done a double click";
        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Buttons']")).click();
        driver.findElement(By.xpath("//button[text()='Click Me']")).click();
        String actualResult = driver.findElement(By.xpath("//p[@id='dynamicClickMessage']")).getText();
        Assertions.assertThat(actualResult)
                .as("We must do dynamic click").isEqualTo(exceptedResultOfClick);

        WebElement elementRightClick = driver.findElement(By.xpath("//button[@id='rightClickBtn']"));
        Actions action = new Actions(driver);
        action.contextClick(elementRightClick).build().perform();
        String actualResultOfRightClick = driver.findElement(By.id("rightClickMessage")).getText();
        Assertions.assertThat(actualResultOfRightClick)
                .as("We must do a right click").isEqualTo(exceptedResultOfRightClick);

        WebElement doubleClickOnButton = driver.findElement(By.id("doubleClickBtn"));
        action.doubleClick(doubleClickOnButton).build().perform();
        String actualResultDoubleClick = driver.findElement(By.id("doubleClickMessage")).getText();
        Assertions.assertThat(actualResultDoubleClick)
                .as("We must do a double click").isEqualTo(exceptedResultDoubleClick);

    }

    @Test(priority = 4)
    public void checkTheNewTabTest() {
        String exceptedWebElement = "This is a sample page";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();
        String currentWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//button[text()='New Tab']")).click();
        List<String> allWindows = new ArrayList<>(driver.getWindowHandles());
        String newTab = allWindows.stream().filter(a -> !a.equals(currentWindow)).findFirst().get();

        driver.switchTo().window(newTab);

        String actualElement = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertThat(actualElement)
                .as("Tab must open with text 'This is a sample page'").isEqualTo(exceptedWebElement);
    }

    @Test(priority = 5)
    public void checkClickOnAlertsTest() {
        String exceptedAlert = "You clicked a button";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Alerts']")).click();
        driver.findElement(By.xpath("//div/button[@id='alertButton']")).click();

        Alert alert = driver.switchTo().alert();
        String actualAlert = alert.getText();
        alert.accept();
        Assertions.assertThat(actualAlert)
                .as("Alert text must be - You clicked a button").isEqualTo(exceptedAlert);



    }

    @Test(priority = 6)
    public void checkThatAlertDisappearTest() {
        String exceptedAlert = "This alert appeared after 5 seconds";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Alerts']")).click();
        driver.findElement(By.xpath("//button[@id='timerAlertButton']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String actualAlert = alert.getText();
        Assertions.assertThat(actualAlert)
                .as("alert must opened with text “This alert appeared after 5 seconds”").isEqualTo(exceptedAlert);
        alert.accept();
        alert = ExpectedConditions.alertIsPresent().apply(driver);
        Assertions.assertThat(alert)
                .as("Alert must disappear").isEqualTo(null);
    }

    @Test(priority = 7)
    public void checkClickFramesTest() {
        String exceptedText = "This is a sample page";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Frames']")).click();
        driver.switchTo().frame("frame1");
        String actualText = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertThat(actualText)
                .as("Text - This is a sample page - must exist if first big square").isEqualTo(exceptedText);
    }

    @Test(priority = 8)
    public void checkModalsTest() {
        String exceptedText = "This is a small modal. It has very less content";
        String exceptedTitle = "Small Modal";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Modal Dialogs']")).click();
        driver.findElement(By.id("showSmallModal")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
        String actualTitle = driver.findElement(By.xpath("//div[@class='modal-header']/div[text()='Small Modal']")).getText();
        Assertions.assertThat(actualTitle)
                .as("Title must be - Small Modal").isEqualTo(exceptedTitle);

        WebElement actualText = driver.findElement(By.xpath("//div[@class='modal-body']"));
        Assertions.assertThat(actualText.getText())
                .as("Text must be - This is a small modal. It has very less content").isEqualTo(exceptedText);

        driver.findElement(By.id("closeSmallModal")).click();
        wait.until(ExpectedConditions.invisibilityOf(actualText));
        Assertions.assertThat(driver.findElements(By.className("modal-content")).size())
                .as("Modal must disappear").isZero();


    }

    @Test(priority = 9)
    public void checkProgressBarTest() {
        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Widgets']"))).click();
        driver.findElement(By.xpath("//span[text()='Progress Bar']")).click();
        driver.findElement(By.id("startStopButton")).click();
        By byProgressBar = By.cssSelector("[role='progressbar']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.attributeToBe(byProgressBar, "aria-valuenow", "100"));
        Assertions.assertThat(driver.findElement(byProgressBar).getText())
                .as("Progress bar must be 100%").isEqualTo("100%");

        driver.findElement(By.id("resetButton")).click();
        wait.until(ExpectedConditions.attributeToBe(byProgressBar, "aria-valuenow", "0"));
        Assertions.assertThat(driver.findElement(byProgressBar).getAttribute("textContent"))
                .as("Progress bar must be 0%").isEqualTo("0%");
    }

    @Test(priority = 10)
    public void checkTabsTest() {
        List<String> activeTabs = Arrays.asList("what", "origin", "use");
        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Widgets']"))).click();
        javaScript.scrollIntoView(driver.findElement(By.xpath("//span[text()='Tabs']"))).click();

        for (String tab : activeTabs) {
            WebElement webElement = driver.findElement(By.cssSelector(String.format("[data-rb-event-key='%s']", tab)));
            Assertions.assertThat(webElement.getAttribute("class").contains("disabled")).as("Tabs must be Active").isFalse();
        }
        WebElement tabMore = driver.findElement(By.cssSelector("[data-rb-event-key='more'"));
        Assertions.assertThat(tabMore.getAttribute("class").contains("disabled"))
                .as("Tab must be disabled").isTrue();


    }

    @Test(priority = 11)
    public void checkSelectMenuTest() {
        String exceptedColor = "White";
        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Widgets']"))).click();
        javaScript.scrollIntoView(driver.findElement(By.xpath("//span[text()='Select Menu']"))).click();

        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByVisibleText(exceptedColor);
        Assertions.assertThat(select.getFirstSelectedOption().getText())
                .as("Excepted text must be - White").isEqualTo(exceptedColor);
    }

    @Test(priority = 12)
    public void checkSelectTableTest() {
        String exceptedSelectedOneOption = "Ms.";
        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Widgets']"))).click();
        javaScript.scrollIntoView(driver.findElement(By.xpath("//span[text()='Select Menu']"))).click();
        driver.findElement(By.id("selectOne")).click();
        driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]", exceptedSelectedOneOption))).click();

        WebElement actualSelectedOneOption = driver.findElement(By.xpath("//div[contains(@class,'singleValue')]"));
        Assertions.assertThat(actualSelectedOneOption.getText())
                .as("Excepted text must be - Ms.").isEqualTo(exceptedSelectedOneOption);


    }

    @Test(priority = 13)
    public void checkDynamicPropertiesTest() {
        List<String> activeTabs = Arrays.asList("Cras justo odio", "Morbi leo risus");

        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Interactions']"))).click();
        javaScript.scrollIntoView(driver.findElement(By.xpath("//span[text()='Selectable']"))).click();

        for (String tab : activeTabs) {
            WebElement webElement = driver.findElement(By.xpath(String.format("//li[text()='%s']", tab)));
            webElement.click();

            Assertions.assertThat(webElement.getAttribute("class").contains("active"))
                    .as("Tab must be Active").isTrue();
        }
    }

    @Test(priority = 14)
    public void CheckPracticeFormTest() throws InterruptedException {
        String exceptedColor = "#dc3545";
        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Forms']"))).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        driver.findElement(By.xpath("//div[text()='Widgets']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        By bySubmit = By.id("submit");
        By byFirstName = By.id("firstName");

        javaScript.click(driver.findElement(bySubmit));
        /*wait.until(ExpectedConditions.attributeToBe(By.id("userForm"),"class", "was-validated"));
        TimeUnit.MILLISECONDS.sleep(500);

        String actualColor = Color.fromString(driver.findElement(byFirstName).getCssValue("border-color")).asHex();
        Assertions.assertThat(actualColor).as("Expected color must be Red").isEqualTo(exceptedColor);*/

    }
    @Test(priority = 15)
    public void checkColorsTest(){
        javaScript.scrollIntoView(driver.findElement(By.xpath("//h5[text()='Widgets']"))).click();
        javaScript.scrollIntoView(driver.findElement(By.xpath("//span[text()='Auto Complete']"))).click();
        driver.findElement(By.id("autoCompleteMultipleContainer")).click();
        Actions actions = new Actions(driver);
        actions.sendKeys("o").build().perform();

        List<WebElement> colorList = driver.findElements(By.xpath("//div[contains(@class,'auto-complete__option')]"));

        List<String> actualColors = new ArrayList<>();

        for (int i = 0; i < colorList.size(); i++){
            String color = colorList.get(i).getText();
            actualColors.add(color);
        }
        Assertions.assertThat(actualColors).as("Excepted to be Yellow, Voilet, Indigo")
                .containsExactly("Yellow","Voilet","Indigo");

    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }
}