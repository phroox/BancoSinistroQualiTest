package model;

import app.Categoria;
import app.Conta;
import app.Lancamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "UsuarioDAO", urlPatterns = {"/UsuarioDAO"})
public class UsuarioDAO extends HttpServlet {
    static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    String erroSQL = "Erro de SQL: {0}";
    private Connection conexao;

    public UsuarioDAO() {

        try {
            // Cria a conexão com o banco de dados
            conexao = ConexaoBD.criaConexao();
        }
        catch( SQLException e ) {
            logger.log(Level.INFO, erroSQL, e.getMessage());

        }
    }

    /*----------------------------------------------------------FUNÇÕES SOBRE CONTAS----------------------------------------------------------*/
    //usada para o usuário logado na sessão cadastrar uma conta para si
    public boolean cadastrarConta(Conta conta) throws SQLException {
        String sql = "INSERT INTO contas (id_usuario, nome_conta, banco, agencia, conta_corrente) VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){


            ps.setInt(1, conta.getIdUsuario());
            ps.setString(2, conta.getNomeConta());
            ps.setString(3, conta.getBanco());
            ps.setString(4, conta.getAgencia());
            ps.setString(5, conta.getContaCorrente());

            if ( conta.getId()> 0 )
                ps.setInt(1, conta.getId());

            ps.execute();

            return true;
        } catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //usada para o usuário logado na sessão editar suas contas
    public boolean editarConta(Conta conta) throws SQLException {
        String sql = "UPDATE contas SET nome_conta=?, agencia=?, conta_corrente=? WHERE id_usuario = ? AND banco=?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, conta.getNomeConta());
            ps.setString(2, conta.getAgencia());
            ps.setString(3, conta.getContaCorrente());
            ps.setInt(4, conta.getIdUsuario());
            ps.setString(5, conta.getBanco());

            ps.execute();

            return true;

        } catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //usada para o usuário logado na sessão excluir uma de suas contas
    public boolean excluirConta(int id) throws SQLException {

        String sql = "DELETE FROM contas WHERE id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setInt(1, id);

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //usada para listar as contas do usuário logado na sessão
    public ArrayList<Conta> getListaContas(Integer idUsuario) throws SQLException {
        ArrayList<Conta> resultado = new ArrayList<>();

        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM contas WHERE id_usuario="+idUsuario);

            while( rs.next() ) {
                Conta conta = new Conta();

                conta.setId(rs.getInt("id") );
                conta.setNomeConta(rs.getString("nome_conta") );
                conta.setBanco( rs.getString("banco"));
                conta.setAgencia( rs.getString("agencia"));
                conta.setContaCorrente( rs.getString("conta_corrente"));

                resultado.add(conta);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }

        return resultado;
    }

    public ArrayList<Conta> getListaContasPorId(Integer idUsuario, int idConta) throws SQLException {
        ArrayList<Conta> resultado = new ArrayList<>();

        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT * FROM contas WHERE id_usuario="+idUsuario+" AND id_conta="+idConta);
            while( rs.next() ) {
                Conta conta = new Conta();

                conta.setId(rs.getInt("id") );
                conta.setNomeConta(rs.getString("nome_conta") );
                conta.setBanco( rs.getString("banco"));
                conta.setAgencia( rs.getString("agencia"));
                conta.setContaCorrente( rs.getString("conta_corrente"));

                resultado.add(conta);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }

        return resultado;
    }
    /*----------------------------------------------------------FUNÇÕES SOBRE LANÇAMENTOS----------------------------------------------------------*/
    //usada para cadastrar um lançamento sobre uma conta do usuário logado na sessão
    public boolean cadastrarLancamento(Lancamento lancamento) throws SQLException {
        String sql = "INSERT INTO lancamentos (id_conta, id_categoria, valor, operacao, data, descricao) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setInt(1, lancamento.getIdConta());
            ps.setInt(2, lancamento.getIdCategoria());
            ps.setFloat(3, lancamento.getValor());
            ps.setString(4, lancamento.getOperacao());
            ps.setString(5, lancamento.getData());
            ps.setString(6, lancamento.getDescricao());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //usada para listar os lançamentos feitos pelo usuário logado na sessão
    public ArrayList<Lancamento> getListaLancamentos(Integer idUsuario) throws SQLException {
        ArrayList<Lancamento> resultado = new ArrayList<>();


        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT * FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id)"
                    + " WHERE contas.id_usuario="+idUsuario+" ORDER BY contas.id, data");

            while( rs.next() ) {
                Lancamento lancamento = new Lancamento();

                lancamento.setId(rs.getInt("id") );
                lancamento.setIdConta(rs.getInt("id_conta") );
                lancamento.setIdCategoria(rs.getInt("id_categoria"));
                lancamento.setValor(rs.getFloat("valor"));
                lancamento.setOperacao(rs.getString("operacao"));
                lancamento.setData(rs.getString("data"));
                lancamento.setDescricao(rs.getString("descricao"));

                resultado.add(lancamento);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return resultado;
    }

    /*----------------------------------------------------------OUTRAS FUNÇÕES----------------------------------------------------------*/
    //usada para mostrar nome da categoria na lista de lançamentos do usuário logado na sessão
    public String getNomeContaPorId(int idConta) throws SQLException{

        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT * FROM contas WHERE id="+idConta);

            while( rs.next() ) {
                Conta conta = new Conta();
                conta.setNomeConta(rs.getString("nome_conta"));

                return rs.getString("nome_conta");

            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return null;
    }

    //usada para mostrar nome da categoria na lista de lançamentos do usuário logado na sessão
    public String getNomeCategoriaPorId(int idCategoria){
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias WHERE id="+idCategoria);
            while( rs.next() ) {
                Categoria categoria = new Categoria();
                categoria.setDescricao(rs.getString("descricao"));

                return rs.getString("descricao");

            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return null;
    }

    //usada para mostrar o saldo de cada categoria sobre cada conta do usuário logado na sessão
    public float getSaldoPorCategoria(Integer idUsuario, int idConta, int idCategoria) throws SQLException {


        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'C' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + "-sum(CASE WHEN lancamentos.operacao = 'D' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND contas.id ="+idConta+" AND"
                    + " categorias.id ="+idCategoria+" GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }

    public float getDebitoPorCategoria(Integer idUsuario, int idConta, int idCategoria) {
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'D' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND contas.id ="+idConta+" AND"
                    + " categorias.id ="+idCategoria+" GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }

    public float getCreditoPorCategoria(Integer idUsuario, int idConta, int idCategoria) {
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'C' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND contas.id ="+idConta+" AND"
                    + " categorias.id ="+idCategoria+" GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }


    //usada para mostrar o saldo total de cada conta do usuário logado na sessão
    public float getSaldoContas(Integer idUsuario, int idConta) {
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT id_conta, nome_conta, id_usuario, sum(CASE WHEN lancamentos.operacao = 'C' THEN lancamentos.valor ELSE 0 END)"
                    + "-sum(CASE WHEN lancamentos.operacao = 'D' THEN lancamentos.valor ELSE 0 END) AS saldo_conta FROM lancamentos JOIN contas ON"
                    + "(lancamentos.id_conta = contas.id) WHERE id_usuario = "+idUsuario+" AND contas.id ="+idConta+" GROUP  BY contas.nome_conta");

            while( rs.next() ) {
                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }
    //usada para mostrar o saldo total das contas do usuário logado na sessão
    public float getSaldoContasTotal(Integer idUsuario) {
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT sum(CASE WHEN lancamentos.operacao = 'C' THEN lancamentos.valor ELSE 0 END)"
                    + "-sum(CASE WHEN lancamentos.operacao = 'D' THEN lancamentos.valor ELSE 0 END) AS saldo_conta FROM lancamentos"
                    + " JOIN contas ON (lancamentos.id_conta = contas.id) JOIN usuarios ON (contas.id_usuario = usuarios.id) WHERE"
                    + " usuarios.id ="+idUsuario);

            while( rs.next() ) {
                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }

    public float getCreditadoContasTotal(Integer idUsuario) {
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT sum(CASE WHEN lancamentos.operacao = 'C' THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos"
                    + " JOIN contas ON (lancamentos.id_conta = contas.id) JOIN usuarios ON (contas.id_usuario = usuarios.id) WHERE"
                    + " usuarios.id ="+idUsuario);

            while( rs.next() ) {
                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }

    public float getDebitadoContasTotal(Integer idUsuario) throws SQLException {
        Statement stmt = conexao.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT "
                    + "sum(CASE WHEN lancamentos.operacao = 'D' THEN lancamentos.valor ELSE 0 END) AS saldo_conta FROM lancamentos"
                    + " JOIN contas ON (lancamentos.id_conta = contas.id) JOIN usuarios ON (contas.id_usuario = usuarios.id) WHERE"
                    + " usuarios.id ="+idUsuario);

            while( rs.next() ) {
                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }

        finally{
            stmt.close();
        }
        return 0;
    }

    public boolean verifSaldoTotalNegativo(Integer idUsuario){
        return getSaldoContasTotal(idUsuario) < 0;
    }

    //usada para verificar se alguma conta do usuário logado na sessão está no negativo e, caso alguma esteja, o usuário ser alertado na view
    public boolean verifExistenciaSaldoNegativo(Integer idUsuario) throws SQLException {

        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT id_conta, nome_conta, id_usuario, sum(CASE WHEN lancamentos.operacao = 'C' THEN lancamentos.valor ELSE 0 END)"
                    + "-sum(CASE WHEN lancamentos.operacao = 'D' THEN lancamentos.valor ELSE 0 END) AS saldo_conta FROM lancamentos JOIN contas ON"
                    + "(lancamentos.id_conta = contas.id)"
                    + " WHERE id_usuario = "+idUsuario+" GROUP  BY contas.nome_conta");

            while( rs.next() ) {
                if (rs.getFloat("saldo_conta") < 0){
                    return true;
                }
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }

        return false;
    }



    public String getPercentualCreditoPorCategoria(Integer idUsuario, int idConta, int idCategoria) {
        float soma = 0;
        UsuarioDAO usuariodao = new UsuarioDAO();
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'C' THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND contas.id ="+idConta+""
                    + " GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                soma += rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        if (soma == 0){
            return "0,0";
        }
        else{
            float resultado = usuariodao.getCreditoPorCategoria(idUsuario, idConta, idCategoria)/soma*100;

            DecimalFormat df = new DecimalFormat("0.0");

            return df.format(resultado);

        }
    }


    public String getPercentualDebitoPorCategoria(Integer idUsuario, int idConta, int idCategoria) throws SQLException {
        float soma = 0;
        UsuarioDAO usuariodao = new UsuarioDAO();


        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'D' THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND contas.id ="+idConta+""
                    + " GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                soma += rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }

        if (soma == 0){
            return "0,0";
        }
        else{
            float resultado = usuariodao.getDebitoPorCategoria(idUsuario, idConta, idCategoria)/soma*100;

            DecimalFormat df = new DecimalFormat("0.0");

            return df.format(resultado);
        }
    }

    public float getDebitoPorCategoriaTotal(Integer idUsuario, int idCategoria) {
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'D' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND"
                    + " categorias.id ="+idCategoria+" GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }

    public float getCreditoPorCategoriaTotal(Integer idUsuario, int idCategoria) {
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'C' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND"
                    + " categorias.id ="+idCategoria+" GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }

    public String getPercentualCreditoPorCategoriaTotal(Integer idUsuario, int idCategoria) {
        float soma = 0;
        UsuarioDAO usuariodao = new UsuarioDAO();
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'C' THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+""
                    + " GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                soma += rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        if (soma == 0){
            return "0,0";
        }
        else{
            float resultado = usuariodao.getCreditoPorCategoriaTotal(idUsuario, idCategoria)/soma*100;

            DecimalFormat df = new DecimalFormat("0.0");

            return df.format(resultado);
        }
    }

    public String getPercentualDebitoPorCategoriaTotal(Integer idUsuario, int idCategoria) throws SQLException {
        float soma = 0;
        UsuarioDAO usuariodao = new UsuarioDAO();
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'D' THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" "
                    + " GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                soma += rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }

        if (soma == 0){
            return "0,0";
        }
        else{
            float resultado = usuariodao.getDebitoPorCategoriaTotal(idUsuario, idCategoria)/soma*100;

            DecimalFormat df = new DecimalFormat("0.0");

            return df.format(resultado);
        }
    }

    public float getSaldoPorCategoriaTotal(Integer idUsuario, int idCategoria) throws SQLException {
        try (Statement stmt = conexao.createStatement()){


            ResultSet rs = stmt.executeQuery("SELECT categorias.id, id_conta, nome_conta, id_usuario,"
                    + " sum(CASE WHEN lancamentos.operacao = 'C' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + "-sum(CASE WHEN lancamentos.operacao = 'D' AND categorias.id ="+idCategoria+" THEN lancamentos.valor ELSE 0 END)"
                    + " AS saldo_conta FROM lancamentos JOIN contas ON (lancamentos.id_conta = contas.id) JOIN categorias ON"
                    + " (categorias.id = lancamentos.id_categoria) WHERE id_usuario ="+idUsuario+" AND"
                    + " categorias.id ="+idCategoria+" GROUP  BY categorias.id ORDER BY contas.id;");

            while( rs.next() ) {

                return rs.getFloat("saldo_conta");
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return 0;
    }
}