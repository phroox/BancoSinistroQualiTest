package servlet;

import app.Conta;
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
import model.UsuarioDAO;
import model.ConexaoBD;

@WebServlet(name = "CadastrarConta", urlPatterns = {"/CadastrarConta"})
public class CadastrarConta extends HttpServlet {

    static final Logger logger = Logger.getLogger(CadastrarConta.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUsuario = (int) request.getSession().getAttribute("id_usuario");
        String nomeConta = (String) request.getParameter("nome_conta");
        String banco = (String) request.getParameter("banco");
        String agencia = (String) request.getParameter("agencia");
        String contaCorrente = (String) request.getParameter("conta_corrente");
        
        if ((!nomeConta.isEmpty()) && (!banco.isEmpty()) && (!agencia.isEmpty()) && (!contaCorrente.isEmpty())) {

            Conta conta = new Conta();

            conta.setId(0);
            conta.setIdUsuario(idUsuario);
            conta.setNomeConta(nomeConta);
            conta.setBanco(banco);
            conta.setAgencia(agencia);
            conta.setContaCorrente(contaCorrente);
  
            UsuarioDAO usuario = new UsuarioDAO();

            if (verifContaCadastrada(banco, agencia, contaCorrente)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroCadastrarContaExistente.jsp");
                rd.forward(request, response);
            }
            else if (verifContaCadastrada2(idUsuario, banco)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroCadastrarContaJaPossui.jsp");
                rd.forward(request, response);
            }
            else{
                try {
                    usuario.cadastrarConta(conta);
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Falha ao tentar cadastrar a conta: {0}", e.getMessage());
                }
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoConta.jsp");
                rd.forward(request, response);
            }
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
    
    private Connection conexao;
    //função auxiliar para verificar se a conta ja havia sido cadastrado    
    public boolean verifContaCadastrada(String banco, String agencia, String contaCorrente) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception e ) {
            logger.log(Level.WARNING, "Erro de conexão com o banco de dados: {0}", e.getMessage());
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM contas");
            while( rs.next() ) {
                Conta conta = new Conta();
                conta.setBanco(rs.getString("banco"));
                conta.setAgencia(rs.getString("agencia"));
                conta.setContaCorrente(rs.getString("conta_corrente") );
               
                if (banco.equals(rs.getString("banco")) && agencia.equals(rs.getString("agencia")) && contaCorrente.equals(rs.getString("conta_corrente"))){
                    return true;
                }
            }
        } catch( SQLException e ) {
            logger.log(Level.WARNING, "Erro de SQL: {0}", e.getMessage());
        }
        return false;
    }
    
    //função auxiliar para verificar se o usuário já havia cadastrado uma conta usando o banco inserido
    public boolean verifContaCadastrada2(int idUsuario, String banco) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, "Erro de SQL: {0}", e.getMessage());
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM contas");
            while( rs.next() ) {
                Conta conta = new Conta();
                conta.setIdUsuario(rs.getInt("id_usuario"));
                conta.setBanco(rs.getString("banco"));
               
                if (idUsuario == (rs.getInt("id_usuario")) && banco.equals(rs.getString("banco"))){
                    return true;
                }
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, "Erro de conexão com o banco de dados: {0}", e.getMessage());
        }
        return false;
    }
}