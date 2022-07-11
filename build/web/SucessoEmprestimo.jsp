<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Empr�stimo realizado com sucesso!</title>
    </head>
    <body>
        <%@include file="headerUsuario.html" %>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-home centralizar">
                    <div class="d-flex flex-row justify-content-center align-items-center" id="successMessage">
                        <h2>Empr�stimo realizado com sucesso!</h2>
                    </div>
                    <hr>
                    <a href="/BancoSinistroQualiTest-master/homeUsuario.jsp" class="btn btn-primary btn" id="return">Voltar � p�gina principal</a>
                    <a href="/BancoSinistroQualiTest-master/RegistrarEmprestimo.jsp" class="btn btn-primary btn">Registrar novamente</a>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>       
    </body>
</html>