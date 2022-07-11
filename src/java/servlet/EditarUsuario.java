package servlet;

import app.Usuario;
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

@WebServlet(name = "EditarUsuario", urlPatterns = {"/EditarUsuario"})
public class EditarUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String suspenso = request.getParameter("suspenso");
        
        if ((!nome.isEmpty()) && (!cpf.isEmpty()) && (!senha.isEmpty()) &&(!suspenso.isEmpty())) {

            Usuario usuario = new Usuario();

            usuario.setId(0);
            usuario.setNome(nome);
            usuario.setCpf(cpf);
            usuario.setSenha(senha);
            usuario.setSuspenso(suspenso);
     
            AdministradorDAO admindao = new AdministradorDAO();

            if (verifNaoHouveAlteracao(nome, senha, suspenso, cpf)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroEditarUsuarioSemAlteracao.jsp");
                rd.forward(request, response);
            }
            else{
                admindao.editarUsuario(usuario);
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoEditarUsuario.jsp");
                rd.forward(request, response);
            }          
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
    
    private Connection conexao;
    //função para verificar se a edição de um usuário não possui nenhuma alteração
    private boolean verifNaoHouveAlteracao(String nome, String senha, String suspenso, String cpf) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception ex ) {
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.WARNING, "Erro de conexão com o banco de dados:", ex);
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE cpf ='"+cpf+"'");
            while( rs.next() ) {
            
                if (nome.equals(rs.getString("nome")) && senha.equals(rs.getString("senha")) && suspenso.equals(rs.getString("suspenso"))){
                    return true;
                }
            }
        }
        catch( SQLException ex ) {
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.WARNING, "Erro de SQL:", ex);
        }
        return false;
    }
}