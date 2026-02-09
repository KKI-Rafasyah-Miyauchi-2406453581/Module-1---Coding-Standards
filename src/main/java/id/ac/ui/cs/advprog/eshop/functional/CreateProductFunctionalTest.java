package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
        // 1. Navigate to the Create Product page
        driver.get(baseUrl + "/product/create");

        // 2. Fill in the product details
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Sampo Cap Bambang");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("100");

        // 3. Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 4. Verify we are redirected to the Product List page
        assertEquals(baseUrl + "/product/list", driver.getCurrentUrl());

        // 5. Verify the new product is visible in the table
        List<WebElement> cells = driver.findElements(By.tagName("td"));
        boolean isProductFound = false;
        for (WebElement cell : cells) {
            if (cell.getText().equals("Sampo Cap Bambang")) {
                isProductFound = true;
                break;
            }
        }
        assertTrue(isProductFound, "The created product should be visible in the list.");
    }
}