package servlet;

import app.Conta;
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
import model.UsuarioDAO;
import model.ConexaoBD;

@WebServlet(name = "EditarConta", urlPatterns = {"/EditarConta"})
public class EditarConta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int idUsuario = (int) request.getSession().getAttribute("id_usuario");
        
        String banco = request.getParameter("banco");
        String nomeConta = request.getParameter("nome_conta");
        String agencia = request.getParameter("agencia");
        String contaCorrente = request.getParameter("conta_corrente");
        
        if ((!banco.isEmpty()) && (!agencia.isEmpty()) && (!contaCorrente.isEmpty())) {

            Conta conta = new Conta();

            conta.setIdUsuario(idUsuario);
            conta.setBanco(banco);
            conta.setNomeConta(nomeConta);
            conta.setAgencia(agencia);
            conta.setContaCorrente(contaCorrente);
     
            UsuarioDAO usuario = new UsuarioDAO();

            if (verifContaCadastradaEdicao(banco, agencia, contaCorrente, idUsuario)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroEditarContaViraExistente.jsp");
                rd.forward(request, response);
            }
            else if (verifNaoHouveAlteracao(banco, nomeConta, agencia, contaCorrente, idUsuario)){
                RequestDispatcher rd = request.getRequestDispatcher("/ErroEditarContaSemAlteracao.jsp");
                rd.forward(request, response);
            }
            else{
                try {
                    usuario.editarConta(conta);
                } catch (SQLException ex) {
                    Logger.getLogger(EditarConta.class.getName()).log(Level.WARNING, "Falha ao tentar editar a conta:", ex);
                }
                RequestDispatcher rd = request.getRequestDispatcher("/SucessoEditarConta.jsp");
                rd.forward(request, response);
            }
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
    
    private Connection conexao;
    
    //função para verificar se a edição de uma conta iguala corra-corrente e agência já existente no banco da conta sendo editada (para impedir que isso ocorra)
    public boolean verifContaCadastradaEdicao(String banco, String agencia, String contaCorrente, int idUsuario) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception ex ) {
            Logger.getLogger(EditarConta.class.getName()).log(Level.WARNING, "Erro de conexão com o banco de dados:", ex);
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM contas");
            while( rs.next() ) {
                Conta conta = new Conta();
                conta.setBanco(rs.getString("banco"));
                conta.setAgencia(rs.getString("agencia"));
                conta.setContaCorrente(rs.getString("conta_corrente"));
                conta.setIdUsuario(rs.getInt("id_usuario"));              
                //banco, agência e conta-corrente iguais, porém com usuário diferente
                if (banco.equals(rs.getString("banco")) && agencia.equals(rs.getString("agencia")) && contaCorrente.equals(rs.getString("conta_corrente")) && idUsuario != (rs.getInt("id_usuario"))){
                    return true;
                }
            }
        } catch( SQLException ex ) {
            Logger.getLogger(EditarConta.class.getName()).log(Level.WARNING, "Erro de SQL:", ex);
        }
        return false;
    }
    
    //função para verificar se a edição de uma conta não possui nenhuma alteração
    private boolean verifNaoHouveAlteracao(String banco, String nomeConta, String agencia, String contaCorrente, int idUsuario) {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception ex ) {
            Logger.getLogger(EditarConta.class.getName()).log(Level.WARNING, "Erro de conexão com o banco de dados:", ex);
        }
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM contas WHERE id_usuario ="+idUsuario);
            while( rs.next() ) {
            
                //banco, agência e conta-corrente iguais, porém com usuário diferente
                if (banco.equals(rs.getString("banco")) && nomeConta.equals(rs.getString("nome_conta")) && agencia.equals(rs.getString("agencia")) && contaCorrente.equals(rs.getString("conta_corrente"))){
                    return true;
                }
            }
        }
        catch( SQLException ex ) {
            Logger.getLogger(EditarConta.class.getName()).log(Level.WARNING, "Erro de SQL:", ex);
        }
        return false;
    }
}