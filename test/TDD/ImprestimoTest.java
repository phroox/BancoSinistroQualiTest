package TDD;

import app.Emprestimo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import model.UsuarioDAO;
import org.junit.Test;
import servlet.CadastrarLancamento;

public class ImprestimoTest {
        
    @Test
    public void testeEmprestimo1() {
        Emprestimo emprestimo = new Emprestimo();
    }
    
    private boolean teste;
    @Test
    public void testeEmprestimo2() {
        Emprestimo emprestimo = new Emprestimo();
        UsuarioDAO usuario = new UsuarioDAO();
        
        try {
            usuario.cadastrarEmprestimo(emprestimo);
            teste = true;
        } catch (SQLException ex) {
            teste = false;
        }
        
        Assert.assertTrue(teste);
    }
    /*
    @Test
    public void testeEmprestimo_deveFalhar() {
        Emprestimo emprestimo = new Emprestimo();
        UsuarioDAO usuario = new UsuarioDAO();
        
        try {
            usuario.cadastrarEmprestimo(emprestimo);
            teste = false;
        } catch (SQLException ex) {
            teste = true;
        }
        
        Assert.assertTrue(teste);
    }
       */
	
}
