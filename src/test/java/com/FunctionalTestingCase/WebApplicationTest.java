package com.FunctionalTestingCase;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WebApplicationTest {

	private WebDriver driver;

	@Test(priority = 0)
	public void setUp() throws Exception {

		// To start recording
		ScreenRecorderUtil.startRecord("main");
		// Launching Chrome and firefox
		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();

		// To maximize the Screen
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void testWebApplication() throws InterruptedException, IOException {
		// Navigate to the login page
		driver.get("https://demo.dealsdray.com/");
		driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);

		// Log in
		driver.findElement(By.xpath("//*[@id=\"mui-1\"]")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("//*[@id=\"mui-2\"]")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/form/div[3]/div/button")).click();

		// Dashbord

		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[2]/div[1]/div[2]/button/div[1]/span[2]"))
				.click();
		// Clicking on orders

		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[2]/div[1]/div[2]/div/div[1]/a/button"))
				.click();

		// Clicking on Add bulk Orders
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/div[2]/button")).click();

		// clicking on choose file

		WebElement Upload = driver.findElement(By.xpath("//*[@id=\"mui-7\"]"));

		Upload.sendKeys("C:\\Users\\JB\\eclipse-workspace\\DealsDray_Assessment\\file\\demo-data.xlsx");

		Thread.sleep(6000);
		// clicking on Import
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div/div[2]/div[3]/button")).click();

		// clicking on Validate Data
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div/div[2]/div[3]/button")).click();

		Thread.sleep(6000);

		driver.switchTo().alert().accept();

		Thread.sleep(6000);
	}

	@Test(priority = 2)
	public void takeFullPageScreenshot() throws Exception {

		// To Scroll down horizontal and vertical
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,500)");
		js.executeScript("window.scrollTo(500,0)");

		// Take full page screenshot
		try {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
					.takeScreenshot(driver);

			ImageIO.write(screenshot.getImage(), "PNG",
					new File("C:\\Users\\JB\\eclipse-workspace\\DealsDray_Assessment\\Screenshot\\screenshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
			// To Stop recording

		}
		// To close the browser
		driver.close();
		// To Stop recording
		ScreenRecorderUtil.stopRecord();
	}
}