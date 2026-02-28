package jar;

import driver.SmartDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class BasicTest {

    @Test
    public void openLoginPage() throws InterruptedException {

        SmartDriver driver = new SmartDriver();

        try {
            driver.get("file:///C:/Users/harshitha.m/OneDrive%20-%20Praval/Desktop/login%20site/login.html");
            Thread.sleep(2000);
            driver.findElement(By.cssSelector(".input-field.username-field")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("1234");
//            driver.findElement(By.cssSelector("[data-test='username-input']")).sendKeys("admin");
//            driver.findElement(By.cssSelector("[data-test='password-input']")).sendKeys("1234");

            driver.findElement(By.id("loginBtn")).click();
            Thread.sleep(2000);

            driver.findElement(By.id("email")).sendKeys("admin@gmail.com");
            Thread.sleep(2000);

            driver.findElement(By.id("phone")).sendKeys("9876543210");
            Thread.sleep(2000);

            driver.findElement(By.id("updateProfileBtn")).click();
            Thread.sleep(2000);

            driver.findElement(By.id("logoutBtn")).click();
            Thread.sleep(2000);

           

        } finally {
            Thread.sleep(2000);
            driver.quit();
        }
    }
}