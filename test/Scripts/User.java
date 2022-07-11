package Scripts;

import static java.lang.Math.random;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class User {
    @Test
    public void CriarUsuario(){
        
        //Realiza o Login como usuário administrador 
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
       
       //Cadastra o usuário
       
       driver.findElement(By.id("createUser")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       Random random = new Random();
       int min_val_cpf = 100;
       int max_val_cpf  = 999;
       ThreadLocalRandom tlr = ThreadLocalRandom.current();
       int number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
       String numberString =  String.valueOf(number);
       String name = "Usuário "+numberString;
       driver.findElement(By.id("name")).sendKeys(name); 
       String cpf = numberString;
       number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
       numberString =  String.valueOf(number);
       cpf = cpf+"."+numberString;
       number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
       numberString =  String.valueOf(number);
       cpf = cpf+"."+numberString;
       int min_val = 10;
       int max_val = 99;
       int randomNum = tlr.nextInt(min_val, max_val + 1);
       numberString =  String.valueOf(randomNum);
       cpf = cpf+"-"+numberString;
       driver.findElement(By.id("cpf")).sendKeys(cpf);
       int senha = random.nextInt(999);
       String senhaString =  String.valueOf(senha);
       driver.findElement(By.id("password")).sendKeys(senhaString); 
       WebElement element = driver.findElement(By.xpath("//select[@id='status']"));
       Select sel = new Select(element);
       sel.selectByIndex(2);
       driver.findElement(By.id("saveUser")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       
       //Captura a mensagem de sucesso
       String response = driver.findElement(By.id("successMessage")).getText();
       driver.close();
       
       assertEquals(loginResponse,"Bem-vindo(a), leo!");
       assertEquals(response,"Usuário cadastrado com sucesso!");
       
    }
    @Test
    public void EditarUsuario(){
         
        //Realiza o Login como usuário administrador 
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
       
       //Editar Usuário
       
       driver.findElement(By.id("editUser")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       WebElement elementCpf = driver.findElement(By.xpath("//select[@id='cpf']"));
       Select selCpf = new Select(elementCpf);
       selCpf.selectByValue("123.345.323-21");
       int min_val_cpf = 100;
       int max_val_cpf  = 999;
       ThreadLocalRandom tlr = ThreadLocalRandom.current();
       int number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
       String numberString =  String.valueOf(number);
       String name = "Usuário "+numberString;
       driver.findElement(By.id("name")).sendKeys(name); 
       Random random = new Random();
       int senha = random.nextInt(999);
       String senhaString =  String.valueOf(senha);
       driver.findElement(By.id("password")).sendKeys(senhaString); 
       
       WebElement elementStatus = driver.findElement(By.xpath("//select[@id='status']"));
       Select selStatus = new Select(elementStatus);
       selStatus.selectByIndex(2);
       driver.findElement(By.id("saveEdit")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       
       //Captura a mensagem de sucesso
       String response = driver.findElement(By.id("successMessage")).getText();
       driver.close();
       
       assertEquals(loginResponse,"Bem-vindo(a), leo!");
       assertEquals(response,"Usuário editado com sucesso!");
    }
}
