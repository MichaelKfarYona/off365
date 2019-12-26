package pages;

import java.io.IOException;

import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.*;

public class ExtentReportWithTestNG {
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	@BeforeTest
	public void setUp() {
		htmlReporter = new ExtentHtmlReporter("extentReport.html");
	    
        // create ExtentReports and attach reporter(s)
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
	}
	 
	@Test
	public void test0001() throws Exception {
		ExtentTest test = extent.createTest("MyFirstTest", "Sample description");
			test.log(Status.INFO, "This step shows usage of log(status, details)");
			test.info("This step shows usage of info(details)");
			test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
	        test.addScreenCaptureFromPath("screenshot.png");
	}
	
	@AfterTest
	public void tearDown() {
		extent.flush();
	}
}
