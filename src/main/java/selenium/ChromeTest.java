package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 模拟浏览器入门操作
 */
public class ChromeTest {
    public static void main(String[] args) {
        doByLocal();
//        doByServer();
    }

    public static void doByServer(){
//        WebDriver driver = Driver.getGoogleDriver("http://localhost:4446/wd/hub");
        DesiredCapabilities chrome = DesiredCapabilities.chrome();
        try {
            //启动SeleniumFile文件夹下的bat文件即可
            RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL("http://localhost:4000/wd/hub"), chrome);
            remoteWebDriver.get("http://www.baidu.com");
            remoteWebDriver.manage().window().maximize();
            WebElement wd = remoteWebDriver.findElement(By.name("wd"));
            wd.sendKeys("WebDriver");
            WebElement btn = remoteWebDriver.findElement(By.id("su"));
            btn.click();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public static  void doByLocal(){
        //    src/main/java/selenium/SeleniumFile/chromedriver.exe
        //    src/main/java/selenium/SeleniumFile/chromedriver.exe
//        System.setProperty("webdriver.chrome.driver", "E:\\selenium\\SeleniumFile\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "src/main/java/selenium/SeleniumFile/chromedriver.exe");
        //初始化一个chrome浏览器实例，实例名称叫driver
        WebDriver driver = new ChromeDriver();
//        WebDriver driver=new ChromeDriver();
        driver.get("http://www.baidu.com");
        driver.manage().window().maximize();
        WebElement txtbox = driver.findElement(By.name("wd"));
        txtbox.sendKeys("WebDriver");
        WebElement btn = driver.findElement(By.id("su"));
        btn.click();
        //设置代理
        String proxyIpAndPort = "ip:post";
        DesiredCapabilities cap = new DesiredCapabilities();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        System.setProperty("http.nonProxyHosts", "localhost");
        cap.setCapability(CapabilityType.PROXY, proxy);

//        driver.close();
    }
}
