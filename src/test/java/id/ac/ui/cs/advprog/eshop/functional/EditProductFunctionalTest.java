package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditProductFunctionalTest {

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
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/edit/1");
        String pageTitle = driver.getTitle();
        System.out.println("DEBUG: The page title is: " + pageTitle);

        Assertions.assertEquals("ADV Shop", pageTitle);
    }

    @Test
    void editFormFields_areCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/edit/1");
        String headerText = driver.findElement(By.tagName("h3"))
                .getText();

        assertEquals("Welcome", headerText);
    }

    @Test
    void editProduct_isSuccessful(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput"))
                .sendKeys("Initial Product");
        driver.findElement(By.id("quantityInput"))
                .sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']"))
                .click();

        driver.findElement(By.linkText("Edit"))
                .click();

        driver.findElement(By.id("nameInput"))
                .clear();
        driver.findElement(By.id("nameInput"))
                .sendKeys("Updated Product");
        driver.findElement(By.id("quantityInput"))
                .clear();
        driver.findElement(By.id("quantityInput"))
                .sendKeys("20");
        driver.findElement(By.cssSelector("button[type='submit']"))
                .click();

        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Updated Product"));
        Assertions.assertTrue(pageSource.contains("20"));
    }
}