<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Login</title>
    </head>
    <body>
        <nav class="navbar fixed-top">
            <div class="container nav-bar">
                <a class="navbar-brand text-white" href="index.jsp"><strong>BancoSinistro</strong></a>
            </div>
        </nav>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-forms">
                    <div class="d-flex flex-row justify-content-center align-items-center">
                        <h2>Login</h2>
                    </div>
                    <hr>
                    <form method="POST" action="Login">
                        <div class="form-group mb-2">
                            <label for="nome_conta">Seu CPF:</label>
                            <input type="text" class="form-control" required name="cpf" id="cpf" required="required" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" placeholder="">
                            <span style="font-size: 0.8rem;">Exemplo de valor v�lido: 888.888.888-88</span>
                        </div>
                        
                        <div class="form-group mb-4">
                            <label for="nome_conta">Sua senha:</label>
                            <input type="password" class="form-control" required name="senha" id="senha" placeholder="">
                        </div>
                        
                        <div>
                            <input type = "checkbox" id = "administrador" name = "administrador" value = "S">
                            <label for = "administrador">Sou administrador</label>
                        </div>
                    
                        <div class="d-flex flex-row justify-content-center align-items-center">
                            <button type="submit" class="btn btn-primary" id="loginSubmit" style="background-color: green;">Confirmar</button>
                        </div>
                    </form>           
                </div>
            </div>    
        </div>
        <%@include file="footer.jsp" %>       
    </body>
</html>