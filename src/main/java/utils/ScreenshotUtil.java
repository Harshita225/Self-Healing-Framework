package utils;

import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class ScreenshotUtil {

    public static String capture(WebDriver driver) {

        String fileName = "healing_screenshot_"
                + LocalDateTime.now().toString()
                .replace(":", "_")
                .replace(".", "_")
                + ".png";

        File src = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        File dest = new File("screenshots/" + fileName);

        dest.getParentFile().mkdirs();

        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dest.getPath();
    }
}