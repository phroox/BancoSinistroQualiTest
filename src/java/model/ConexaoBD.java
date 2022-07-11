package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "Conexao", urlPatterns = {"/Conexao"})
public class ConexaoBD extends HttpServlet {

    private static Connection conexao = null;
    
    public static Connection criaConexao() throws SQLException {
        Logger logger = Logger.getLogger(ConexaoBD.class.getName());
        if ( conexao == null ) {
            try {             
                Class.forName("com.mysql.jdbc.Driver");                       
                logger.log(Level.INFO, "Driver foi carregado!");
                
                conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/financeiro", "root", "");
                logger.log(Level.INFO, "Conexão realizada com sucesso!");
            }
            catch( ClassNotFoundException e ) {
                logger.log(Level.WARNING, "Driver não foi localizado!");
            }
        }
        return conexao;
    }
}
