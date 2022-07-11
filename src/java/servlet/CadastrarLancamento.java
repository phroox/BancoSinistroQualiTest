package servlet;

import app.Categoria;
import app.Conta;
import app.Lancamento;
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

@WebServlet(name = "CadastrarLancamento", urlPatterns = {"/CadastrarLancamento"})
public class CadastrarLancamento extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idConta = Integer.parseInt(request.getParameter("id_conta"));
        int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));
        Float valor = Float.parseFloat(request.getParameter("valor"));
        String operacao = (String) request.getParameter("operacao");
        String data = (String) request.getParameter("data");
        String descricao = (String) request.getParameter("descricao");

        if ((idConta != 0) && (idCategoria != 0) && (valor != 0) && (!operacao.isEmpty()) && (!data.isEmpty())) {

            Lancamento lancamento = new Lancamento();

            lancamento.setId(0);
            lancamento.setIdConta(idConta);
            lancamento.setIdCategoria(idCategoria);
            lancamento.setValor(valor);
            lancamento.setOperacao(operacao);
            lancamento.setData(data);
            lancamento.setDescricao(descricao);

            UsuarioDAO usuario = new UsuarioDAO();

            try {
                usuario.cadastrarLancamento(lancamento);
            } catch (SQLException ex) {
                Logger.getLogger(CadastrarLancamento.class.getName()).log(Level.WARNING, "Erro ao tentar registrar um lançamento:", ex);
            }
            RequestDispatcher rd = request.getRequestDispatcher("/SucessoLancamento.jsp");
            rd.forward(request, response);
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
}