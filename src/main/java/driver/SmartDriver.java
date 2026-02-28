package driver;

import utils.HealingReport;
import utils.StringSimilarity;
import utils.ScreenshotUtil;
import org.openqa.selenium.NoSuchElementException;
import model.ElementMetadata;
import storage.LocatorStorage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SmartDriver {

    private WebDriver driver;

    public SmartDriver() {
        driver = new ChromeDriver();
    }

    public void get(String url) {
        driver.get(url);
    }

    public WebElement findElement(By locator) {

        System.out.println("\n==============================");
        System.out.println("Trying to locate element: " + locator);
        System.out.println("==============================");

        ElementMetadata storedMeta = LocatorStorage.get(locator.toString());

        // 🔹 1️⃣ Try stored healed locator first
        if (storedMeta != null && storedMeta.locatorStrategy != null) {
            try {
                By rebuilt = buildLocator(
                        storedMeta.locatorStrategy,
                        storedMeta.locatorValue
                );

                WebElement element = driver.findElement(rebuilt);

                System.out.println("Element found using STORED healed locator.");

                String screenshot = ScreenshotUtil.capture(driver);
                HealingReport.logSuccess(locator.toString(),
                        "Used stored healed locator",
                        storedMeta.locatorStrategy,
                        storedMeta.locatorValue,
                        screenshot);

                return element;

            } catch (Exception e) {
                System.out.println("Stored locator failed. Trying original...");
            }
        }

        // 🔹 2️⃣ Try original locator
        try {
            WebElement element = driver.findElement(locator);

            System.out.println("Element found using ORIGINAL locator.");

            saveMetadata(locator, element);

            String screenshot = ScreenshotUtil.capture(driver);
            HealingReport.logSuccess(locator.toString(),
                    "Original locator worked",
                    detectStrategy(locator),
                    extractValue(locator),
                    screenshot);

            return element;

        } catch (NoSuchElementException e) {

            System.out.println("Element FAILED using locator: " + locator);
            String failShot = ScreenshotUtil.capture(driver);
            HealingReport.logFailure(locator.toString(), failShot);

            // 🔹 3️⃣ Attempt healing
            WebElement healedElement = healElement(locator);

            if (healedElement != null) {
                System.out.println("Healing successful!");
                return healedElement;
            }

            System.out.println("Healing failed completely.");
            throw e;
        }
    }

    // ===============================
    // SAVE METADATA
    // ===============================
    private void saveMetadata(By locator, WebElement element) {

        ElementMetadata meta = new ElementMetadata();
        meta.tag = element.getTagName();
        meta.id = element.getAttribute("id");
        meta.name = element.getAttribute("name");
        meta.className = element.getAttribute("class");
        meta.text = element.getText();
        meta.locatorStrategy = detectStrategy(locator);
        meta.locatorValue = extractValue(locator);

        LocatorStorage.save(locator.toString(), meta);
    }

    // ===============================
    // 🔥 HYBRID AI HEALING ENGINE
    // ===============================
    private WebElement healElement(By oldLocator) {

        ElementMetadata oldMeta = LocatorStorage.get(oldLocator.toString());

        if (oldMeta == null) {
            System.out.println("No metadata found for healing.");
            return null;
        }

        System.out.println("Scanning DOM for best match...");

        List<WebElement> allElements = driver.findElements(By.xpath("//*"));

        WebElement bestMatch = null;
        double highestFinalScore = 0;

        for (WebElement element : allElements) {

            int rawScore = 0;

            String tag = element.getTagName();
            String id = element.getAttribute("id");
            String name = element.getAttribute("name");
            String className = element.getAttribute("class");
            String text = element.getText();

            // ===== RAW WEIGHTED SCORING (Max 12) =====
            if (safeEquals(tag, oldMeta.tag)) rawScore += 3;
            if (safeEqualsIgnoreCase(id, oldMeta.id)) rawScore += 5;
            if (safeEqualsIgnoreCase(name, oldMeta.name)) rawScore += 4;
            if (safeEqualsIgnoreCase(className, oldMeta.className)) rawScore += 2;
            if (safeEqualsIgnoreCase(text, oldMeta.text)) rawScore += 2;

            // ===== FUZZY SIMILARITY =====
            double idSimilarity = StringSimilarity.similarityPercent(oldMeta.id, id);
            double nameSimilarity = StringSimilarity.similarityPercent(oldMeta.name, name);  // ✅ ADD
            double textSimilarity = StringSimilarity.similarityPercent(oldMeta.text, text);

            double fuzzyScore = (idSimilarity * 0.5) 
                              + (nameSimilarity * 0.3)   // ✅ ADD
                              + (textSimilarity * 0.2);

            // ===== HYBRID FINAL SCORE =====
            double rawPercent = (rawScore / 16.0) * 100;  // ✅ UPDATED
            double finalScore = (rawPercent * 0.6) + (fuzzyScore * 0.4);

            if (finalScore > highestFinalScore) {
                highestFinalScore = finalScore;
                bestMatch = element;
            }
        }

        // 🔹 Threshold (AI confidence)
        if (highestFinalScore < 40) {
            System.out.println("Healing confidence too low: "
                    + String.format("%.2f", highestFinalScore) + "%");

            String healFailShot = ScreenshotUtil.capture(driver);
            HealingReport.logHealingFailure(oldLocator.toString(), healFailShot);
            return null;
        }

        System.out.println("Best match found!");
        System.out.println("AI Healing Confidence: "
                + String.format("%.2f", highestFinalScore) + "%");

        // ===== Build new locator =====
        ElementMetadata updatedMeta = new ElementMetadata();
        updatedMeta.tag = bestMatch.getTagName();
        updatedMeta.name = bestMatch.getAttribute("name");
        updatedMeta.id = bestMatch.getAttribute("id");
        updatedMeta.className = bestMatch.getAttribute("class");
        updatedMeta.text = bestMatch.getText();

        if (updatedMeta.id != null && !updatedMeta.id.isEmpty()) {
            updatedMeta.locatorStrategy = "id";
            updatedMeta.locatorValue = updatedMeta.id;
        }
        else if (updatedMeta.name != null && !updatedMeta.name.isEmpty()) {   // ✅ ADD
            updatedMeta.locatorStrategy = "name";
            updatedMeta.locatorValue = updatedMeta.name;
        }
        else if (updatedMeta.className != null &&
                !updatedMeta.className.isEmpty()) {

            updatedMeta.locatorStrategy = "class";
            updatedMeta.locatorValue = updatedMeta.className.split(" ")[0];
        }
        else {
            updatedMeta.locatorStrategy = "xpath";
            updatedMeta.locatorValue = generateSimpleXPath(bestMatch);
        }

        LocatorStorage.save(oldLocator.toString(), updatedMeta);

        String healedShot = ScreenshotUtil.capture(driver);
        HealingReport.logHealingSuccess(
                oldLocator.toString(),
                updatedMeta.locatorStrategy,
                updatedMeta.locatorValue,
                highestFinalScore,
                healedShot
        );

        return bestMatch;
    }

    // ===============================
    // Utility Methods
    // ===============================
    private boolean safeEquals(String a, String b) {
        return a != null && b != null && a.equals(b);
    }

    private boolean safeEqualsIgnoreCase(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }

    private By buildLocator(String strategy, String value) {

        switch (strategy.toLowerCase()) {
            case "id": return By.id(value);
            case "name": return By.name(value);
            case "css": return By.cssSelector(value);
            case "xpath": return By.xpath(value);
            case "class": return By.className(value);
            default:
                throw new RuntimeException("Unknown locator strategy: " + strategy);
        }
    }

    private String detectStrategy(By locator) {

        String loc = locator.toString();

        if (loc.contains("By.id")) return "id";
        if (loc.contains("By.name")) return "name";
        if (loc.contains("By.cssSelector")) return "css";
        if (loc.contains("By.xpath")) return "xpath";
        if (loc.contains("By.className")) return "class";

        return "unknown";
    }

    private String extractValue(By locator) {
        String loc = locator.toString();
        return loc.substring(loc.indexOf(":") + 2);
    }

    private String generateSimpleXPath(WebElement element) {

        if (element.getAttribute("id") != null &&
                !element.getAttribute("id").isEmpty()) {
            return "//" + element.getTagName()
                    + "[@id='" + element.getAttribute("id") + "']";
        }

        return "//" + element.getTagName();
    }

    public void quit() {
        HealingReport.closeReport(); 
       driver.quit();
    }
}