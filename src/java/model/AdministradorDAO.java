package model;

import app.Usuario;
import app.Administrador;
import app.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "AdministradorDAO", urlPatterns = {"/AdministradorDAO"})
public class AdministradorDAO extends HttpServlet {
    static final Logger logger = Logger.getLogger(AdministradorDAO.class.getName());
    String erroSQL = "Erro de SQL: {0}";
    private Connection conexao;

    public AdministradorDAO() {
        try {
            conexao = ConexaoBD.criaConexao();
        }
        catch( Exception e ) {
            logger.log(Level.WARNING, "Erro de conexão com o banco de dados: {0}", e.getMessage());
        }
    }
    /*----------------------------------------------------------FUNÇÕES SOBRE USUÁRIOS----------------------------------------------------------*/
    //cadastra usuários no sistema
    public boolean cadastrarUsuario( Usuario usuario ) {
        String sql = "INSERT INTO usuarios (nome,cpf,senha,suspenso) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getSuspenso());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //edita os usuários do sistema
    public boolean editarUsuario( Usuario usuario ) {
        String sql = "UPDATE usuarios SET nome=?, senha=?, suspenso=? WHERE cpf = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getSuspenso());
            ps.setString(4, usuario.getCpf());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //exclui usuários do sistema
    public boolean excluirUsuario(String cpf ) {
        String sql = "DELETE FROM usuarios WHERE cpf = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, cpf);
            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //lista os usuários
    public ArrayList<Usuario> getListaUsuarios() {

        ArrayList<Usuario> resultado = new ArrayList<>();
        try (Statement stmt = conexao.createStatement()){

            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios ORDER BY id");

            while( rs.next() ) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome( rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setSenha( rs.getString("senha"));
                usuario.setSuspenso( rs.getString("suspenso"));

                resultado.add(usuario);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return resultado;
    }

    //lista os usuários liberados
    public ArrayList<Usuario> getListaUsuariosLiberados() {
        ArrayList<Usuario> resultado = new ArrayList<>();

        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE suspenso = 'N' ORDER BY id");

            while( rs.next() ) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome( rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setSenha( rs.getString("senha"));
                usuario.setSuspenso( rs.getString("suspenso"));
                resultado.add(usuario);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return resultado;
    }

    //lista os usuários suspensos
    public ArrayList<Usuario> getListaUsuariosSuspensos() {
        ArrayList<Usuario> resultado = new ArrayList<>();

        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE suspenso='S' ORDER BY id");

            while( rs.next() ) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id") );
                usuario.setNome( rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setSenha( rs.getString("senha") );
                usuario.setSuspenso( rs.getString("suspenso"));
                resultado.add(usuario);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return resultado;
    }

    //libera usuários que estão suspensos
    public boolean liberarUsuario(String cpf) {
        String sql = "UPDATE usuarios SET suspenso='N' WHERE cpf = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, cpf);

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //suspende usuários que estão liberados
    public boolean suspenderUsuario(String cpf) {
        String sql = "UPDATE usuarios SET suspenso='S' WHERE cpf = ?";


        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, cpf);

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    /*----------------------------------------------------------FUNÇÕES SOBRE ADMINISTRADORES----------------------------------------------------------*/
    //cadastra administradores no sistema
    public boolean cadastrarAdministrador( Administrador administrador ) {
        String sql = "INSERT INTO administradores (nome,cpf,senha) VALUES (?,?,?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, administrador.getNome());
            ps.setString(2, administrador.getCpf());
            ps.setString(3, administrador.getSenha());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //edita os administradores do sistema
    public boolean editarAdministrador(Administrador admin) {
        String sql = "UPDATE administradores SET nome=?, senha=? WHERE cpf = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, admin.getNome());
            ps.setString(2, admin.getSenha());
            ps.setString(3, admin.getCpf());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //exclui administradores do sistema
    public boolean excluirAdministrador(String cpf) {
        String sql = "DELETE FROM administradores WHERE cpf = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, cpf);

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //lista os administradores
    public ArrayList<Administrador> getListaAdministradores() {
        ArrayList<Administrador> resultado = new ArrayList<>();
        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM administradores ORDER BY id");

            while( rs.next() ) {
                Administrador administrador = new Administrador();

                administrador.setId(rs.getInt("id") );
                administrador.setNome( rs.getString("nome") );
                administrador.setCpf(rs.getString("cpf") );
                administrador.setSenha( rs.getString("senha") );
                resultado.add(administrador);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return resultado;
    }

    /*----------------------------------------------------------FUNÇÕES SOBRE CATEGORIAS----------------------------------------------------------*/
    //cadastra categorias no sistema
    public boolean cadastrarCategoria( Categoria categoria ) {
        String sql = "INSERT INTO categorias (descricao) VALUES (?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, categoria.getDescricao());

            if ( categoria.getId()> 0 )
                ps.setInt(1, categoria.getId());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //edita as categorias no sistema
    public boolean editarCategoria(Categoria cat) {
        String sql = "UPDATE categorias SET descricao=? WHERE id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setString(1, cat.getDescricao());
            ps.setInt(2, cat.getId());

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //exclui categorias do sistema
    public boolean excluirCategoria(String descricao) {
        String sql = "DELETE FROM categorias WHERE descricao = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, descricao);

            ps.execute();

            return true;
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
            return false;
        }
    }

    //lista as categorias
    public ArrayList<Categoria> getListaCategorias() {
        ArrayList<Categoria> resultado = new ArrayList<>();

        try (Statement stmt = conexao.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM categorias ORDER BY id");

            while( rs.next() ) {
                Categoria categoria = new Categoria();

                categoria.setId(rs.getInt("id"));
                categoria.setDescricao( rs.getString("descricao"));
                resultado.add(categoria);
            }
        }
        catch( SQLException e ) {
            logger.log(Level.WARNING, erroSQL, e.getMessage());
        }
        return resultado;
    }
}