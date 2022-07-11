<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Usu�rio liberado com sucesso!</title>
    </head>
    <body>
        <%@include file="headerAdministrador.html" %>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-home centralizar">
                    <div class="d-flex flex-row justify-content-center align-items-center">
                        <h2>Usu�rio liberado com sucesso!</h2>
                    </div>
                    <hr>
                    <a href="/BancoSinistro/homeAdmin.jsp" class="btn btn-primary btn">Voltar � p�gina principal</a>
                    <a href="/BancoSinistro/LiberarUsuario.jsp" class="btn btn-primary btn">Liberar novamente</a>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>       
    </body>
</html>