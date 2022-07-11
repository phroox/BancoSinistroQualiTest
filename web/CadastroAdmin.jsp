<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Cadastro de Administrador</title>
    </head>
    <body>
        <%@include file="headerAdministrador.html" %>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-forms">
                    <div class="d-flex flex-row justify-content-center align-items-center">
                        <h2>Cadastro de Administrador</h2>
                    </div>
                    <hr>
                    <form method="POST" action="CadastrarAdministrador" >
                        <div class="form-group mb-2">
                          <label for="nome">Nome:</label>
                          <input type="text" class="form-control" id="name" name="nome" required="required" maxlength="20" placeholder="">
                        </div>

                        <div class="form-group mb-2">
                            <label for="cpf">CPF:</label>
                            <input type="text" class="form-control" id="cpf" name="cpf" required="required" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" required placeholder="">
                            <span style="font-size: 0.8rem;">Exemplo de valor v�lido: 888.888.888-88</span>
                        </div>

                        <div class="form-group mb-4">
                            <label for="senha">Senha:</label>
                            <input type="password" class="form-control" id="password" name="senha" required="required" maxlength="255"  placeholder="">
                        </div>
                     
                        <div class="d-flex flex-row justify-content-center align-items-center">
                            <button type="submit" class="btn btn-primary" id="saveAdm" style="background-color: green;">Confirmar</button>
                        </div>
                    </form>           
                </div>
                <div class="d-flex flex-row justify-content-center align-items-center mt-3">
                    <a href="/BancoSinistro/homeAdmin.jsp" class="btn btn-primary btn-lg">Voltar</a>
                </div>
            </div>    
        </div>
        <%@include file="footer.jsp" %>       
    </body>
</html>