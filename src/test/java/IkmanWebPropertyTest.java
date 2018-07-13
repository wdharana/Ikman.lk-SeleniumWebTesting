import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class IkmanWebPropertyTest {

    @Test
    public void searchWithFilter(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://ikman.lk");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        String xPathOfPropertyAnchor = "//div/a[contains(.,'Property')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathOfPropertyAnchor))).click();

        String xPathOfHouseAnchor = "//li/a[contains(.,'Houses')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathOfHouseAnchor))).click();

        String xPathOfColomboAnchor = "//li/a[contains(.,'Colombo')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathOfColomboAnchor))).click();

        String xPathOfDropPrice = "//a[contains(.,'Price')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathOfDropPrice))).click();

        String minimumFieldSelector = "input[name='filters[0][minimum]']";
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(minimumFieldSelector)))
                .sendKeys("5000000");

        String maximumFieldSelector = "input[name='filters[0][maximum]']";
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(maximumFieldSelector)))
                .sendKeys("7500000");

        String xPathOfFilters = "//div[contains(@class,'filter-price')]//button[contains(.,'Apply filters')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathOfFilters))).click();

        String xPathOfDropBeds = "//a[contains(.,'Beds')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPathOfDropBeds))).click();

        String checkBoxSelector = "input[id='filters2values-3']";
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(checkBoxSelector))).click();

        String xPathOfTotalResults = "//div/span[contains(@class,'summary-count')]";
        int total = Integer.parseInt(driver.findElement(By.xpath(xPathOfTotalResults)).getText().split(" ")[3]);
        int pages = total / 25;

        String valueXpath = "//div[contains(@class,'ui-item') and not(contains(@class,'is-top'))]/div/p/strong";
        String xPathOfNextPage = "//a[contains(@class,'pag-next')]";
        String xPathOfBeds = "//div[contains(@class,'ui-item') and not(contains(@class,'is-top'))]/div/p[contains(@class,'item-meta')]/span[contains(text(),'Beds')]";

        System.out.println("No of Ads Found : "+ total);

        int loopVal = 1;
        for (int i = 0; i <= pages ; i++ ) {
            List<WebElement> valueResults = driver.findElements(By.xpath(valueXpath));
            List<WebElement> bedResults = driver.findElements(By.xpath(xPathOfBeds));
            int bedIndex = 0;
            for (WebElement value : valueResults) {
                int price = Integer.parseInt(value.getText().split(" ")[1].replace(",",""));
                int beds = Integer.parseInt(bedResults.get(bedIndex).getText().split(" ")[1]);
                String validity = "";
                if (price <= 7500000 && price >= 5000000 && beds == 3){
                    validity = "Valid Result";
                }else{
                    validity = "Invalid Result";
                }
                System.out.println("Ad Number " + loopVal + " Price is : " + value.getText() + " => " + validity);
                loopVal++;
                bedIndex++;
            }
            if(i < pages){
                driver.findElement(By.xpath(xPathOfNextPage)).click();
            }

        }

        driver.close();
        driver.quit();

    }
}
