package Scripts.UserFunctions;

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

public class Contas {
    @Test
    public void CadastrarConta(){
        
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
       driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
       String loginResponse = driver.findElement(By.id("welcomeMessage")).getText();
       
       //Cadastrar conta e visualizar a mesma na lista
        driver.findElement(By.id("createBankAccounts")).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        driver.findElement(By.id("accountName")).sendKeys("Conta Nubank");
        Random random = new Random();
        int min_val_cpf = 100;
        int max_val_cpf  = 999;
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
        String numberString =  String.valueOf(number);
        driver.findElement(By.id("bank")).sendKeys(numberString);
        min_val_cpf = 1000;
        max_val_cpf  = 9999;
        number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
        numberString =  String.valueOf(number);
        driver.findElement(By.id("agency")).sendKeys(numberString);
        min_val_cpf = 1000;
        max_val_cpf  = 9999;
        number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
        numberString =  String.valueOf(number);
        String contaCorrente = numberString;
        min_val_cpf = 0;
        max_val_cpf  = 9;
        number = tlr.nextInt(min_val_cpf, max_val_cpf + 1);
        numberString =  String.valueOf(number);
        contaCorrente = contaCorrente+"-"+numberString;
        driver.findElement(By.id("accountId")).sendKeys(contaCorrente);
        driver.findElement(By.id("saveAccount")).click();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        
        //Captura a mensagem de sucesso
       String response = driver.findElement(By.id("successMessage")).getText();
       
       assertEquals(loginResponse,"Bem-vindo(a), leo!");
       assertEquals(response,"Conta registrada com sucesso!");
        
    }
}
