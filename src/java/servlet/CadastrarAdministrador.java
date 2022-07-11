package servlet;

import app.Administrador;
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

@WebServlet(name = "CadastrarAdministrador", urlPatterns = {"/CadastrarAdministrador"})
public class CadastrarAdministrador extends HttpServlet {

    static final Logger logger = Logger.getLogger(CadastrarAdministrador.class.getName());

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

            if (verifAdminCadastrado(cpf)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroCadastrarAdminExistente.jsp");
                rd.forward(request, response);
            }
            else{
                admindao.cadastrarAdministrador(admin);
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoAdmin.jsp");
                rd.forward(request, response);
            }
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }

    private Connection conexao;
    //função auxiliar para verificar se o administrador já havia sido cadastrado
    public boolean verifAdminCadastrado(String cpf) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception e ) {
            logger.log(Level.WARNING, "Erro de conexão com o banco de dados: {0}", e.getMessage());
        }
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT * FROM administradores");
            while( rs.next() ) {
                Administrador admin = new Administrador();
                admin.setCpf(rs.getString("cpf") );

                if (cpf.equals(rs.getString("cpf"))){
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