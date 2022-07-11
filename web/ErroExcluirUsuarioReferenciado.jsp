<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Erro ao excluir usu�rio</title>
    </head>
    <body>
        <%@include file="headerAdministrador.html" %>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-home centralizar">
                    <div class="d-flex flex-row justify-content-center align-items-center">
                        <h2>Erro ao Excluir Usu�rio</h2>
                    </div>
                    <hr>
                    <div class="d-flex flex-row justify-content-center align-items-center m-3">
                        <h4>N�o � poss�vel excluir este usu�rio, pois ele j� foi referenciado em outras opera��es.</h4>
                    </div>
                    <a href="/BancoSinistro/homeAdmin.jsp" class="btn btn-primary btn">Voltar � p�gina principal</a>
                    <a href="/BancoSinistro/ExcluirUsuario.jsp" class="btn btn-primary btn">Voltar</a>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>       
    </body>
</html>