package novaPoshtaTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tools.JavaScript;

import java.time.Duration;

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
    public void testInputsFields() {
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
        //#output #permanentAddress
        String actualResultPermanentAddress = driver.findElement(By.cssSelector("#output #permanentAddress")).getText();
        assertThat(actualResultPermanentAddress).isEqualTo("Permananet Address :" + exceptedPermanentAddress);


    }

    @Test(priority = 2)
    public void checkBox() {
        String exceptedResult = "wordFile";
        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//li[@id='item-1']//span[text()='Check Box']")).click();
        driver.findElement(By.xpath("//button[@class='rct-collapse rct-collapse-btn']")).click();
        driver.findElement(By.xpath("//label[@for='tree-node-downloads']/../button")).click();
        driver.findElement(By.xpath("//label[@for='tree-node-wordFile']")).click();
        String actualResultSelected = driver.findElement(By.xpath("//div[@id='result']/span[@class='text-success']")).getText();
        assertThat(actualResultSelected).isEqualTo(exceptedResult);

    }

    @Test(priority = 3)
    public void checkButtons() {
        String exceptedResultOfClick = "You have done a dynamic click";
        String exceptedResultOfRightClick = "You have done a right click";
        String exceptedResultDoubleClick = "You have done a double click";
        WebElement elements = driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Buttons']")).click();
        driver.findElement(By.xpath("//button[text()='Click Me']")).click();
        String actualResult = driver.findElement(By.xpath("//p[@id='dynamicClickMessage']")).getText();
        assertThat(actualResult).isEqualTo(exceptedResultOfClick);
        WebElement elementRightClick = driver.findElement(By.xpath("//button[@id='rightClickBtn']"));
        Actions action= new Actions(driver);
        action.contextClick(elementRightClick).build().perform();
        String actualResultOfRightClick=driver.findElement(By.id("rightClickMessage")).getText();
        assertThat(actualResultOfRightClick).isEqualTo(exceptedResultOfRightClick);
        WebElement doubleClickOnButton = driver.findElement(By.id("doubleClickBtn"));
        action.doubleClick(doubleClickOnButton).build().perform();
        String actualResultDoubleClick = driver.findElement(By.id("doubleClickMessage")).getText();
        assertThat(actualResultDoubleClick).isEqualTo(exceptedResultDoubleClick);

    }
    @Test(priority = 4)
    public void checkTheNewTab(){
        String exceptedWebElement = "This is a sample page";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();
        driver.findElement(By.xpath("//button[text()='New Tab']")).click();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        String actualElement = driver.findElement(By.id("sampleHeading")).getText();
        assertThat(actualElement).isEqualTo(exceptedWebElement);
    }
    @Test(priority = 5)
    public void ClickOnAlerts(){
        String exceptedAlert = "You clicked a button";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Alerts']")).click();
        driver.findElement(By.xpath("//div/button[@id='alertButton']")).click();

        Alert alert= driver.switchTo().alert();
        String actualAlert = alert.getText();
        assertThat(actualAlert).isEqualTo(exceptedAlert);
    }
    @Test(priority = 6)
    public void checkThatAlertDisappear(){
        String exceptedAlert = "This alert appeared after 5 seconds";
        WebElement elements = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        javaScript.scrollIntoView(elements).click();
        driver.findElement(By.xpath("//span[text()='Alerts']")).click();
        driver.findElement(By.xpath("//button[@id='timerAlertButton']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert= driver.switchTo().alert();
        String actualAlert = alert.getText();
        assertThat(actualAlert).isEqualTo(exceptedAlert);
        alert.accept();
        alert = ExpectedConditions.alertIsPresent().apply(driver);
        Assertions.assertThat(alert).isEqualTo(null);
    }

 @AfterMethod(alwaysRun = true)
    public void quitDriver() {
       driver.quit();
}
}