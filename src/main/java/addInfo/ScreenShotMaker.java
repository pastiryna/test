package addInfo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import addInfo.AbstractPage;
import java.io.File;
import java.io.IOException;

/**
 * Created by techmagic on 12/16/2015.
 */
public class ScreenShotMaker implements ITestListener {


        @Override
        public void onTestStart(ITestResult result) {

        }

        @Override
        public void onTestSuccess(ITestResult result) {

        }

        @Override
        public void onTestFailure(ITestResult result) {
            File file = new File("ScreenShots", result.getName() + ".png");
            File tmpFile = ((TakesScreenshot) AbstractPage.driver)
                    .getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(tmpFile, file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onTestSkipped(ITestResult result) {
            File file = new File("ScreenShots", result.getName() + "WasSkipped" + ".png");
            File tmpFile = ((TakesScreenshot) AbstractPage.driver)
                    .getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(tmpFile, file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        }

        @Override
        public void onStart(ITestContext context) {

        }

        @Override
        public void onFinish(ITestContext context) {

        }


}
