/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scripts.UserFunctions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class Registro_de_Movimento {
    @Test
    public void CadastrarMovimento() throws InterruptedException{
         
        //Realiza o Login como usuário administrador 
       System.setProperty("webdriver.chrome.driver",".\\test\\Drivers\\chromedriver.exe");
       WebDriver driver = new ChromeDriver(); 
       driver.get("http://localhost:8080/BancoSinistroQualiTest-master/index.jsp");
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       driver.findElement(By.id("login")).click();
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       driver.findElement(By.id("cpf")).sendKeys("870.304.050-05");
       driver.findElement(By.id("senha")).sendKeys("111");
       driver.findElement(By.id("loginSubmit")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       String loginResponse = driver.findElement(By.id("welcomeMessage")).getText();
       
       
       //Cadastra movimento
       driver.manage().window().maximize();
       driver.findElement(By.id("createAccountData")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       WebElement element = driver.findElement(By.xpath("//select[@id='id_conta']"));
       Select sel = new Select(element);
       sel.selectByValue("1");
       WebElement elementCat = driver.findElement(By.xpath("//select[@id='id_categoria']"));
       Select cat = new Select(elementCat);
       cat.selectByValue("1");
       int min_val_val = 1;
       int max_val_val  = 999999999;
       ThreadLocalRandom tlr = ThreadLocalRandom.current();
       int numberVal = tlr.nextInt(min_val_val, max_val_val + 1);
       String numberStringVal =  String.valueOf(numberVal);
       driver.findElement(By.id("value")).sendKeys(numberStringVal);
       int min_val_cpf = 1;
       int max_val_cpf  = 2;
       int numberOperation = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
       WebElement elementOp = driver.findElement(By.xpath("//select[@id='operacao']"));
       Select op = new Select(elementOp);
       op.selectByIndex(numberOperation);
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       String date = "dd/MM/yyyy"+dtf.format(LocalDateTime.now());
       driver.findElement(By.id("date")).sendKeys(date);
       Thread.sleep(3000);
       driver.findElement(By.id("registerData")).click();
       driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
       String response = driver.findElement(By.id("successMessage")).getText();
       driver.close();
       
       
       assertEquals(loginResponse,"Bem-vindo(a), leo!");
       assertEquals(response,"Movimentação realizada com sucesso!");
       
}
}
