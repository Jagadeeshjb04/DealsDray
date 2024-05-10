package com.UITesting;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.*;

import com.FunctionalTestingCase.ScreenRecorderUtil;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebTester {
	
	

    private static final String[] resolutionsDesktop = {"1920x1080", "1366x768", "1536x864"};
    private static final String[] resolutionsMobile = {"360x640", "414x896", "375x667"};

    private WebDriver chromeDriver;
    private WebDriver firefoxDriver;
  

    @BeforeClass
    public void setUp() throws Exception {
    	
    	 //To start recording
    	ScreenRecorderUtil.startRecord("main");
        // Launching Chrome, Firefox
    	WebDriverManager.chromedriver().setup();
    	
    	WebDriverManager.firefoxdriver().setup();
    	
        chromeDriver = new ChromeDriver();
        firefoxDriver = new FirefoxDriver();
 
    }

    @AfterClass
    public void tearDown() {
        //To close
        chromeDriver.quit();
        firefoxDriver.quit();
       
    }

    @DataProvider(name = "resolutions")
    public Object[][] resolutionsData() {
        return new Object[][]{{"Desktop", resolutionsDesktop}, {"Mobile", resolutionsMobile}};
    }

    @Test(dataProvider = "resolutions")
    public void testWebsite(String device, String[] resolutions) throws Exception {
        // Creating folder for Screenshot
        for (String resolution : resolutions) {
            new File(device + "/" + resolution).mkdirs();
        }

        //Running Test for each resolution
        for (String resolution : resolutions) {
            captureScreenshot(chromeDriver, device, resolution, "https://www.getcalley.com/page-sitemap.xml");
            captureScreenshot(firefoxDriver, device, resolution, "https://www.getcalley.com/page-sitemap.xml");
         
        }
    }

    private void captureScreenshot(WebDriver driver, String device, String resolution, String url) throws Exception {
        driver.manage().window().setSize(new Dimension(Integer.parseInt(resolution.split("x")[0]),
                Integer.parseInt(resolution.split("x")[1])));
        driver.get(url);

        //Full page screenshot 
        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String fileName = device + "/" + resolution + "/Screenshot-" + timestamp + ".png";
            ImageIO.write(screenshot.getImage(), "PNG", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //To Stop recording
    			ScreenRecorderUtil.stopRecord();
    }
}
