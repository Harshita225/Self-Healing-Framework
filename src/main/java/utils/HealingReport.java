package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class HealingReport {

    private static final String FILE_PATH = "healing-report.html";

    // Initialize HTML report
    static {
        try (FileWriter writer = new FileWriter(FILE_PATH, false)) {
            writer.write("<html><head><title>Healing Report</title></head><body>");
            writer.write("<h1>UI Self-Healing Report</h1><hr>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logSuccess(String locator, String message,
                                  String strategy, String value, String screenshotPath) {
        writeToFile(
            "<div style='background:#d4edda;padding:10px;margin:5px;border-left:5px solid green;'>" +
            "<b>[SUCCESS]</b> " + LocalDateTime.now() + "<br>" +
            "Locator: " + locator + "<br>" +
            "Message: " + message + "<br>" +
            "Strategy Used: " + strategy + "<br>" +
            "Value Used: " + value + "<br>" +
            (screenshotPath != null ? "<img src='" + screenshotPath + "' width='400'><br>" : "") +
            "</div>"
        );
    }

    public static void logFailure(String locator, String screenshotPath) {
        writeToFile(
            "<div style='background:#f8d7da;padding:10px;margin:5px;border-left:5px solid red;'>" +
            "<b>[FAILED]</b> " + LocalDateTime.now() + "<br>" +
            "Locator Failed: " + locator + "<br>" +
            (screenshotPath != null ? "<img src='" + screenshotPath + "' width='400'><br>" : "") +
            "</div>"
        );
    }

    public static void logHealingSuccess(String oldLocator,
                                         String newStrategy,
                                         String newValue,
                                         double confidence,
                                         String screenshotPath) {
        writeToFile(
            "<div style='background:#fff3cd;padding:10px;margin:5px;border-left:5px solid orange;'>" +
            "<b>[HEALED SUCCESS]</b><br>" +
            "Old Locator: " + oldLocator + "<br>" +
            "New Strategy: " + newStrategy + "<br>" +
            "New Value: " + newValue + "<br>" +
            "AI Confidence: " + String.format("%.2f", confidence) + "%<br>" +
            (screenshotPath != null ? "<img src='" + screenshotPath + "' width='400'><br>" : "") +
            "</div>"
        );
    }

    public static void logHealingFailure(String locator, String screenshotPath) {
        writeToFile(
            "<div style='background:#f5c6cb;padding:10px;margin:5px;border-left:5px solid darkred;'>" +
            "<b>[HEALING FAILED]</b><br>" +
            "Locator: " + locator + "<br>" +
            (screenshotPath != null ? "<img src='" + screenshotPath + "' width='400'><br>" : "") +
            "</div>"
        );
    }

    private static void writeToFile(String content) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(content + "<br>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close HTML report at the end
    public static void closeReport() {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}