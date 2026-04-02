package reporting;

import config.FrameworkConfig;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class HtmlReportManager {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<ReportEntry> ENTRIES = new CopyOnWriteArrayList<>();

    private static LocalDateTime executionStart;

    private HtmlReportManager() {
    }

    public static void startExecution() {
        ENTRIES.clear();
        executionStart = LocalDateTime.now();
    }

    public static void addResult(ITestResult result, String status, String details, String screenshotPath) {
        ENTRIES.add(new ReportEntry(
                result.getTestClass().getName(),
                result.getMethod().getMethodName(),
                status,
                result.getEndMillis() - result.getStartMillis(),
                LocalDateTime.now(),
                buildGroups(result),
                details,
                screenshotPath
        ));
    }

    public static String flush() {
        try {
            Path reportDirectory = Path.of(FrameworkConfig.getReportDirectory());
            Files.createDirectories(reportDirectory);

            Path reportPath = reportDirectory.resolve(FrameworkConfig.getReportFileName());
            Files.writeString(reportPath, buildHtml(reportPath));
            return reportPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create HTML report.", e);
        }
    }

    private static String buildHtml(Path reportPath) {
        List<ReportEntry> sortedEntries = new ArrayList<>(ENTRIES);
        sortedEntries.sort(Comparator
                .comparing(ReportEntry::executedAt)
                .thenComparing(ReportEntry::className)
                .thenComparing(ReportEntry::methodName));

        long passedCount = sortedEntries.stream().filter(entry -> "PASS".equals(entry.status())).count();
        long failedCount = sortedEntries.stream().filter(entry -> "FAIL".equals(entry.status())).count();
        long skippedCount = sortedEntries.stream().filter(entry -> "SKIP".equals(entry.status())).count();
        long retryCount = sortedEntries.stream().filter(entry -> "RETRY".equals(entry.status())).count();

        StringBuilder html = new StringBuilder();
        html.append("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Selenium Learning Report</title>
                    <style>
                        body { font-family: Segoe UI, Arial, sans-serif; margin: 24px; background: #f6f8fb; color: #1f2937; }
                        h1, h2 { margin-bottom: 8px; }
                        .meta, .summary { display: flex; gap: 16px; flex-wrap: wrap; margin-bottom: 20px; }
                        .card { background: #ffffff; border-radius: 10px; padding: 14px 16px; box-shadow: 0 2px 10px rgba(15, 23, 42, 0.08); }
                        .summary .card { min-width: 140px; }
                        table { width: 100%; border-collapse: collapse; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(15, 23, 42, 0.08); }
                        th, td { padding: 12px 14px; border-bottom: 1px solid #e5e7eb; text-align: left; vertical-align: top; }
                        th { background: #eef2ff; font-weight: 600; }
                        .status { font-weight: 700; }
                        .PASS { color: #15803d; }
                        .FAIL { color: #b91c1c; }
                        .SKIP { color: #92400e; }
                        .RETRY { color: #1d4ed8; }
                        .details { white-space: pre-wrap; max-width: 520px; }
                        a { color: #1d4ed8; text-decoration: none; }
                    </style>
                </head>
                <body>
                <h1>Selenium Learning Framework Report</h1>
                """);

        html.append("<div class=\"meta\">")
                .append(card("Started", format(executionStart)))
                .append(card("Finished", format(LocalDateTime.now())))
                .append(card("Base URL", escape(FrameworkConfig.getBaseUrl())))
                .append(card("Browser", escape(FrameworkConfig.getBrowser())))
                .append(card("Headless", String.valueOf(FrameworkConfig.isHeadless())))
                .append(card("Retry Count", String.valueOf(FrameworkConfig.getRetryCount())))
                .append("</div>");

        html.append("<div class=\"summary\">")
                .append(card("Passed", String.valueOf(passedCount)))
                .append(card("Failed", String.valueOf(failedCount)))
                .append(card("Skipped", String.valueOf(skippedCount)))
                .append(card("Retried", String.valueOf(retryCount)))
                .append(card("Total", String.valueOf(sortedEntries.size())))
                .append("</div>");

        html.append("""
                <table>
                    <thead>
                        <tr>
                            <th>Test</th>
                            <th>Status</th>
                            <th>Groups</th>
                            <th>Duration</th>
                            <th>Executed At</th>
                            <th>Details</th>
                            <th>Artifacts</th>
                        </tr>
                    </thead>
                    <tbody>
                """);

        for (ReportEntry entry : sortedEntries) {
            html.append("<tr>")
                    .append("<td>").append(escape(entry.className())).append("<br>").append(escape(entry.methodName())).append("</td>")
                    .append("<td class=\"status ").append(entry.status()).append("\">").append(entry.status()).append("</td>")
                    .append("<td>").append(escape(entry.groups())).append("</td>")
                    .append("<td>").append(formatDuration(entry.durationMs())).append("</td>")
                    .append("<td>").append(format(entry.executedAt())).append("</td>")
                    .append("<td class=\"details\">").append(escape(entry.details())).append("</td>")
                    .append("<td>").append(buildArtifactLink(reportPath, entry.screenshotPath())).append("</td>")
                    .append("</tr>");
        }

        html.append("""
                    </tbody>
                </table>
                </body>
                </html>
                """);

        return html.toString();
    }

    private static String buildGroups(ITestResult result) {
        String[] groups = result.getMethod().getGroups();
        return groups.length == 0 ? "-" : String.join(", ", groups);
    }

    private static String buildArtifactLink(Path reportPath, String screenshotPath) {
        if (screenshotPath == null || screenshotPath.isBlank()) {
            return "-";
        }

        Path absoluteReportPath = reportPath.toAbsolutePath().normalize();
        Path absoluteScreenshotPath = Path.of(screenshotPath).toAbsolutePath().normalize();
        Path relativePath = absoluteReportPath.getParent().relativize(absoluteScreenshotPath);
        return "<a href=\"" + escape(relativePath.toString().replace("\\", "/")) + "\">Screenshot</a>";
    }

    private static String formatDuration(long durationMs) {
        Duration duration = Duration.ofMillis(durationMs);
        return duration.toMillis() + " ms";
    }

    private static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }

        return dateTime.format(TIMESTAMP_FORMAT);
    }

    private static String card(String label, String value) {
        return "<div class=\"card\"><strong>" + escape(label) + "</strong><br>" + escape(value) + "</div>";
    }

    private static String escape(String value) {
        if (value == null) {
            return "-";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private record ReportEntry(
            String className,
            String methodName,
            String status,
            long durationMs,
            LocalDateTime executedAt,
            String groups,
            String details,
            String screenshotPath
    ) {
    }
}
