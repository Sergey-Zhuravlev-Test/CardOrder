package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome/driver", "./drivers/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {

       // driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void test() {
        driver.get("http://localhost:7777/");
        driver.findElements(By.tagName("input")).get(0).sendKeys("Петров Василий");
        driver.findElements(By.tagName("input")).get(1).sendKeys("+79158883524");
        driver.findElement(By.className("checkbox_box")).click();
        driver.findElement(By.className("button_content")).click();
        String expected = "Ваша заявка успешно отправлена!";
        String actual = driver.findElement(By.className("paragraph")).getText();
        Assertions.assertEquals(expected, actual);

    }
}
