<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Home - Administrador</title>
    </head>
    <body>
        <%@include file="headerAdministrador.html" %>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-home centralizar">
                    <div class="d-flex flex-row justify-content-center align-items-center mt-5" id="welcomeMessage">
                        <h1>Bem-vindo(a), ${nome}!</h1>
                    </div>
                    <hr><h4>CADASTRAR</h4><hr>
                    <a href="CadastroUsuario.jsp" class="btn btn-primary btn mb-2" id="createUser">Cadastrar Usu�rio</a>
                    <a href="CadastroAdmin.jsp" class="btn btn-primary btn mb-2" id="createAdmin">Cadastrar Administrador</a>
                    <a href="CadastroCategoria.jsp" class="btn btn-primary btn  mb-2" id="createCategory">Cadastrar Categoria</a>
                    
                    <hr><h4>EDITAR</h4><hr>
                    <a href="EditarUsuario.jsp" class="btn btn-primary btn mb-2" id="editUser">Editar Usu�rio</a>
                    <a href="EditarAdministrador.jsp" class="btn btn-primary btn mb-2">Editar Administrador</a>
                    <a href="EditarCategoria.jsp" class="btn btn-primary btn mb-2">Editar Categoria</a>
                    
                    <hr><h4>SUSPENDER / LIBERAR</h4><hr>
                    <a href="SuspenderUsuario.jsp" class="btn btn-primary btn mb-2">Suspender Usu�rio</a>
                    <a href="LiberarUsuario.jsp" class="btn btn-primary btn mb-2">Liberar Usu�rio</a>
                    
                    <hr><h4>EXCLUIR</h4><hr>
                    <a href="ExcluirUsuario.jsp" class="btn btn-primary btn mb-2">Excluir Usu�rio</a>
                    <a href="ExcluirAdministrador.jsp" class="btn btn-primary btn mb-2">Excluir Administrador</a>
                    <a href="ExcluirCategoria.jsp" class="btn btn-primary btn mb-2">Excluir Categoria</a>      
                    
                    <hr><h4>LISTAS</h4><hr>
                    <a href="ListarUsuarios.jsp" class="btn btn-primary btn mb-2">Lista de Usu�rios</a>
                    <a href="ListarAdministradores.jsp" class="btn btn-primary btn mb-2">Lista de Administradores</a>
                    <a href="ListarCategorias.jsp" class="btn btn-primary btn mb-2">Lista de Categorias</a>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>       
    </body>
</html>
   
