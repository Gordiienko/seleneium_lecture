package novaPoshtaTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class FirstTestClass {
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    @BeforeMethod
    public void setUpBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //driver.manage().window().maximize();
        driver.get("https://novaposhta.ua/");

    }
    @Test(priority = 1)
    public void checkThatLabelToTrackExistOnPageTest() {
        String expectedResult = "Відстежити";

        WebElement trackLabel = driver.findElement(By.xpath("//div[@class='search_cargo']/div[@class='header']"));
        String actualResult = trackLabel.getText();
        //Assert.assertEquals(actualResult,expectedResult);
        Assertions.assertThat(actualResult).as(" Expected text "+ "not exist on the main page").isEqualTo(expectedResult);
        //trackButton
        //trackInput
        //trackLink
        //trackImg
    }
    @Test(priority = 2)
    public void checkThatLabelShippingCoastExistOnShippingPageTest() {
        String expectedText = "Вартість доставки";
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#popup_info")));
        driver.findElement(By.cssSelector("#popup_info i")).click();
        WebElement costButton = driver.findElement(By.xpath("//a[@class='cost']//span[text()='Вартість доставки']"));
        costButton.click();
        WebElement pageTitleLabel = driver.findElement(By.xpath("//h1[@class='page_title']"));
        String actualPageTitleText = pageTitleLabel.getText();
        Assertions.assertThat(actualPageTitleText).as("Page title should "+ expectedText).isEqualTo(expectedText);

    }
    //@AfterMethod(alwaysRun = true)
   // public void quiteDriver(){
      //  driver.quit();
    }

//}
