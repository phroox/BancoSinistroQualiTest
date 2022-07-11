package servlet;

import app.Conta;
import app.Emprestimo;
import app.Lancamento;
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

@WebServlet(name = "RealizarEmprestimo", urlPatterns = {"/RealizarEmprestimo"})
public class RealizarEmprestimo extends HttpServlet {

    static final Logger logger = Logger.getLogger(CadastrarConta.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idConta = Integer.parseInt(request.getParameter("id_conta"));
        Float valor = Float.parseFloat(request.getParameter("valor"));

        if ((idConta != 0) && (valor != 0)) {

            Emprestimo emprestimo = new Emprestimo();

            emprestimo.setId(0);
            emprestimo.setIdConta(idConta);
            emprestimo.setValor(valor);

            UsuarioDAO usuario = new UsuarioDAO();

            try {
                usuario.cadastrarEmprestimo(emprestimo);
            } catch (SQLException ex) {
                Logger.getLogger(CadastrarLancamento.class.getName()).log(Level.WARNING, "Erro ao tentar registrar emprestimo:", ex);
            }
            RequestDispatcher rd = request.getRequestDispatcher("/SucessoEmprestimo.jsp");
            rd.forward(request, response);
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");
            rd.forward(request, response);
        }
    }
}