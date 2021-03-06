package servlet;

import app.Administrador;
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

@WebServlet(name = "EditarAdministrador", urlPatterns = {"/EditarAdministrador"})
public class EditarAdministrador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        
        if ((!nome.isEmpty()) && (!cpf.isEmpty()) && (!senha.isEmpty())) {

            Administrador admin = new Administrador();

            admin.setId(0);
            admin.setNome(nome);
            admin.setCpf(cpf);
            admin.setSenha(senha);
     
            AdministradorDAO admindao = new AdministradorDAO();

            if (verifNaoHouveAlteracao(nome, senha, cpf)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroEditarAdministradorSemAlteracao.jsp");
                rd.forward(request, response);
            }
            else{
                admindao.editarAdministrador(admin);
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoEditarAdministrador.jsp");
                rd.forward(request, response); 
            }
            
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
    
    private Connection conexao;
    //função para verificar se a edição de um administrador não possui nenhuma alteração
    private boolean verifNaoHouveAlteracao(String nome, String senha, String cpf) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception ex ) {
            Logger.getLogger(CadastrarAdministrador.class.getName()).log(Level.WARNING, "Erro ao tentar conexão com o banco de dados:", ex);
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM administradores WHERE cpf ='"+cpf+"'");
            while( rs.next() ) {              

                if (nome.equals(rs.getString("nome")) && senha.equals(rs.getString("senha"))){
                    return true;
                }
            }
        }
        catch( SQLException ex ) {
            Logger.getLogger(CadastrarAdministrador.class.getName()).log(Level.WARNING, "Erro de SQL:", ex);
        }
        return false;
    }
}