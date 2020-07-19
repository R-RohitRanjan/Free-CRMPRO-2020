package com.crm.qe.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.crm.qe.utility.TestUtil;
import com.crm.qe.utility.WebEventListener;

public class TestBase 
{
	public static WebDriver driver;
	public static Properties prop;
	
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public TestBase()
	{
		try
		{
			prop = new Properties();
			FileInputStream inputStream = new FileInputStream("C:\\Users\\Rohit Ranjan\\eclipse-workspace\\"
					+ "PageObjectModel.POM2020\\src\\main\\java\\com\\crm\\qe\\config\\config.properties");
			prop.load(inputStream);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void initialization()
	{
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", 
					"C:\\Users\\Rohit Ranjan\\eclipse-workspace\\PageObjectModel.POM2020\\Drivers\\chromedriver.exe");
			driver=new ChromeDriver(); //Launch Chrome
		}
		else if(browserName.equals("firefox"))
		{
			System.setProperty("webdriver.chrome.driver", 
					"C:\\Users\\Rohit Ranjan\\eclipse-workspace\\PageObjectModel.POM2020\\Drivers\\geckodriver.exe"); 
			driver=new FirefoxDriver(); //Launch Chrome	
		}
		else if(browserName.equals("firefox"))
		{
			System.setProperty("webdriver.chrome.driver", 
					"C:\\Users\\Rohit Ranjan\\eclipse-workspace\\PageObjectModel.POM2020\\Drivers\\msedgedriver.exe"); 
			driver=new EdgeDriver(); //Launch Chrome	
		}
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize(); //Maximizing
		driver.manage().deleteAllCookies(); //Deleting the cookies
		driver.get(prop.getProperty("url")); //Enter URL
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}
}
