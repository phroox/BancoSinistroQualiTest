package servlet;

import app.Categoria;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdministradorDAO;
import model.ConexaoBD;

@WebServlet(name = "EditarCategoria", urlPatterns = {"/EditarCategoria"})
public class EditarCategoria extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        int idInt = Integer.parseInt(id);
      
        String descricao = request.getParameter("descricao");

        if ((!descricao.isEmpty()) && (!id.isEmpty())) {

            Categoria cat = new Categoria();

            cat.setId(idInt);
            cat.setDescricao(descricao);
     
            AdministradorDAO admindao = new AdministradorDAO();

            
            if (verifNaoHouveAlteracao(descricao)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroEditarCategoriaSemAlteracao.jsp");
                rd.forward(request, response);
            }
            else{
                admindao.editarCategoria(cat);
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoEditarCategoria.jsp");
                rd.forward(request, response);
            }   
            
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
    
    private Connection conexao;
    //função para verificar se a edição de uma categoria não possui nenhuma alteração
    private boolean verifNaoHouveAlteracao(String descricao) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception ex ) {
            Logger.getLogger(EditarCategoria.class.getName()).log(Level.WARNING, "Erro de conexão com o banco de dados:", ex);
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias WHERE descricao ='"+descricao+"'");
            while( rs.next() ) {              

                if (descricao.equals(rs.getString("descricao"))){
                    return true;
                }
            }
        }
        catch( SQLException ex ) {
            Logger.getLogger(EditarCategoria.class.getName()).log(Level.WARNING, "Erro de SQL:", ex);
        }
        return false;
    }
}