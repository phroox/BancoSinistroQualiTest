package Scripts;

import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    
    @Test
    public void AdmLoginTest(){
       System.setProperty("webdriver.chrome.driver",".\\test\\Drivers\\chromedriver.exe");
       WebDriver driver = new ChromeDriver(); 
       driver.get("http://localhost:8080/BancoSinistroQualiTest-master/index.jsp");
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       driver.findElement(By.id("login")).click();
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       driver.findElement(By.id("cpf")).sendKeys("249.252.810-38");
       driver.findElement(By.id("senha")).sendKeys("111");
       driver.findElement(By.id("administrador")).click();
       driver.findElement(By.id("loginSubmit")).click();
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       String loginResponse = driver.findElement(By.id("welcomeMessage")).getText();
       driver.close();
       
       assertEquals(loginResponse,"Bem-vindo(a), leo!");
    }
    
    @Test
     public void UserLoginTest(){
       System.setProperty("webdriver.chrome.driver",".\\test\\Drivers\\chromedriver.exe");
       WebDriver driver = new ChromeDriver(); 
       driver.get("http://localhost:8080/BancoSinistroQualiTest-master/index.jsp");
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       driver.findElement(By.id("login")).click();
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       driver.findElement(By.id("cpf")).sendKeys("192.212.212-12");
       driver.findElement(By.id("senha")).sendKeys("123");
       driver.findElement(By.id("loginSubmit")).click();
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       String loginResponse = driver.findElement(By.id("welcomeMessage")).getText();
       driver.close();
       
       assertEquals(loginResponse,"Bem-vindo(a), Mario Alberto!");
    }
}

