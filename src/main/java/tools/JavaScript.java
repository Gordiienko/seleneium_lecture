package tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScript {
    private JavascriptExecutor javascriptExecutor;

    public JavaScript(WebDriver driver) {
        this.javascriptExecutor = (JavascriptExecutor) driver;
    }

    public WebElement scrollIntoView(WebElement webElement) {
        this.javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", webElement);
        return webElement;
    }
    public void click(WebElement webElement){
        this.javascriptExecutor.executeScript("arguments[0].click();", webElement);
    }
}
