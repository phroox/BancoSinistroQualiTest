package servlet;

import app.Categoria;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministradorDAO;
import model.ConexaoBD;

@WebServlet(name = "CadastrarCategoria", urlPatterns = {"/CadastrarCategoria"})
public class CadastrarCategoria extends HttpServlet {

    static final Logger logger = Logger.getLogger(CadastrarCategoria.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String descricao = request.getParameter("descricao");
        
        if ((!descricao.isEmpty())) {

            Categoria categoria = new Categoria();

            categoria.setId(0);
            categoria.setDescricao(descricao);
  
            AdministradorDAO admindao = new AdministradorDAO();

            if (verifCatCadastrada(descricao)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroCadastrarCategoriaExistente.jsp");
                rd.forward(request, response);
            }
            else{
                admindao.cadastrarCategoria(categoria);
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoCategoria.jsp");
                rd.forward(request, response);
            }
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
    
    private Connection conexao;
    //função auxiliar para verificar se a categoria já havia sido cadastrado    
    public boolean verifCatCadastrada(String descricao) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception e ) {
            logger.log(Level.WARNING, "Erro de conexão com o banco de dados: {0}", e.getMessage());
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias");
            while( rs.next() ) {
                Categoria cat = new Categoria();
                cat.setDescricao(rs.getString("descricao") );
               
                
                //usuário com cpf e senha válidos, porém suspenso
                if (descricao.equals(rs.getString("descricao"))){
                    return true;
                }
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, "Erro de SQL: {0}", e.getMessage());
        }
        return false;
    }
}