package Scripts;

import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Category {
     @Test
    public void CriarCategoria() throws InterruptedException{
        
        //Realiza o Login como usuário administrador 
       System.setProperty("webdriver.chrome.driver",".\\test\\Drivers\\chromedriver.exe");
       WebDriver driver = new ChromeDriver(); 
       driver.get("http://localhost:8080/BancoSinistroQualiTest-master/index.jsp");
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       driver.findElement(By.id("login")).click();
       Thread.sleep(2000);
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       driver.findElement(By.id("cpf")).sendKeys("249.252.810-38");
       Thread.sleep(2000);
       driver.findElement(By.id("senha")).sendKeys("111");
       Thread.sleep(2000);
       driver.findElement(By.id("administrador")).click();
       Thread.sleep(2000);
       driver.findElement(By.id("loginSubmit")).click();
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       Thread.sleep(2000);
       String loginResponse = driver.findElement(By.id("welcomeMessage")).getText();
       
       //Cadastra o categoria
       Thread.sleep(2000);
       driver.findElement(By.id("createCategory")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       Thread.sleep(2000);
       driver.findElement(By.id("description")).sendKeys("gatonet");
       Thread.sleep(2000);
       driver.findElement(By.id("saveCategory")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       Thread.sleep(2000);
       
       //Captura a mensagem de sucesso
       String response = driver.findElement(By.id("successMessage")).getText();
       driver.close();
       
       assertEquals(loginResponse,"Bem-vindo(a), leo!");
       assertEquals(response,"Categoria registrada com sucesso!");
    }
}
