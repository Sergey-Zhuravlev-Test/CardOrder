package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {

        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    void setUp() {

        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        option.addArguments("--disable-dev-shm-usage");
        option.addArguments("--no-sandbox");
        option.addArguments("--headless");
        driver = new ChromeDriver(option);


    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSuccessfulFilled() {
        driver.get("http://localhost:7777/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров-Курочкин Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258884411");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void shouldFillPhone() {
        driver.get("http://localhost:7777/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров-Курочкин Василий");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFillCorrectPhone() {
        driver.get("http://localhost:7777/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров-Курочкин Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("3332288");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void shouldFillName() {
        driver.get("http://localhost:7777/");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258884411");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFillCorrectName() {
        driver.get("http://localhost:7777/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("X");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258884411");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void shouldFillAgreement() {
        driver.get("http://localhost:7777/");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров-Курочкин Василий");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258884411");
        driver.findElement(By.cssSelector("[role='button']")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText().trim();
        Assertions.assertEquals(expected, actual);

    }
}
