package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.FrameworkConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTest implements FrameworkConsts {

    public static WebDriver driver;
    public ExtentSparkReporter extentSparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

   Object[][] excelTestData;

    @BeforeTest
    public void beforeTestMethod() {
        extentSparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator +
                "test-output" + File.separator + "testReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentReports.setSystemInfo("HostName", "Mullai");
        extentReports.setSystemInfo("Username", "root");
        extentSparkReporter.config().setDocumentTitle("Automation Report");
        extentSparkReporter.config().setReportName("Automation Team");
    }

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethodMethod(String browser, Method testMethod) throws IOException {
        extentTest = extentReports.createTest(testMethod.getName());
        setupDriver(browser);
        driver.manage().window().maximize();
        driver.get(FrameworkConsts.URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void afterMethodMethod(ITestResult iTestResult) {
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(iTestResult.getName() + " - Test Case Failed", ExtentColor.RED));
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(iTestResult.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        } else if (iTestResult.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel(iTestResult.getName() + " - Test Case is Skipped", ExtentColor.ORANGE));
        } else if (iTestResult.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(iTestResult.getName() + "- Test Case is Passed", ExtentColor.GREEN));

        }
    }

    @AfterTest
    public void afterTestMethod() {
        extentReports.flush();
    }

    public void setupDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = WebDriverManager.chromedriver().create();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = WebDriverManager.firefoxdriver().create();

        }
    }

    @DataProvider(name = "getData")
    public Object[][] getData(Method methodName) throws IOException {
        // Implement logic to read data from Excel based on method name and column name
        return readDataFromExcel(methodName.getName(), "TestData");
    }

    public Object[][] readDataFromExcel(String methodName, String excelFileName) throws IOException {
        try {
            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "test" + File.separator + "Resource" + File.separator + excelFileName + ".xlsx");
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("Sheet1");

            if (sheet != null) {
                Map<String, String> testdata = readHeadersFromExcel(sheet, methodName);

                workbook.close();
                file.close();
                if(testdata.equals(null)){
                    throw new RuntimeException("Not matching with MethodName: " + methodName);
                }

                return new Object[][]{{testdata}};
            }

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Sheet not found for method: " + methodName);
    }

    public Map<String, String> readHeadersFromExcel(Sheet sheet, String methodName) {
        Row headerRow = sheet.getRow(0);
        int colCount = sheet.getRow(0).getLastCellNum();
        int rowCount = sheet.getLastRowNum();
        Map<String, String> rowData = new HashMap<>();
        for (int rowNum = 1; rowNum <= rowCount; rowNum++) {
            String value = String.valueOf(sheet.getRow(rowNum).getCell(0));
            if (value.equals(methodName))
                for (int colNum = 0; colNum < colCount; colNum++) {
                    rowData.put(String.valueOf(headerRow.getCell(colNum)), String.valueOf(sheet.getRow(rowNum).getCell(colNum)));
                }
        }
        return rowData;
    }

//    public static void main(String args[]) throws IOException {
//        BaseTest baseTest = new BaseTest();
//        baseTest.getData("LoginFreeCRM2", "TestData");
//    }
}


