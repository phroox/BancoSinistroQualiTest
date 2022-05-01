<%@page import="app.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page import="app.Usuario"%>
<%@page import="model.AdministradorDAO"%>
<%@page contentType="text/html; encoding=UTF-8"%>
<!DOCTYPE html>
<%@include file="bootstrap.html" %>

<html lang="pt-br">
    <head>
        <%@include file="header.html" %>
        <title>Edi��o de Categoria</title>
    </head>
    <body>
        <%@include file="headerAdministrador.html" %>
 
        <div class="container container-index">
            <div class="container container-texto">
                <br>
                <div class="container container-forms">
                    <div class="d-flex flex-row justify-content-center align-items-center">
                        <h2>Edi��o de Categoria</h2>
                    </div>
                    <hr>
                    <form method="POST" action="EditarCategoria" >
                        <div class="form-group mb-4">
                            <label for="cpf">Selecione a categoria a ser editada:</label>
                            <select class="form-control" id="id" name="id" required="required">
                                <option></option>
                            <%
                                AdministradorDAO admindao = new AdministradorDAO();
                                ArrayList<Categoria> ListaCategoria = admindao.getListaCategorias();
                                for (int i = 0; i < ListaCategoria.size(); i++) {
                                    Categoria aux = ListaCategoria.get(i);
                            %>                    
                            <option value="<%=aux.getId()%>"><%=aux.getDescricao()%></option>
                            <%
                            }
                            %>
                            </select>
                        </div>
                        <hr>
                        <div class="form-group mb-4">
                            <label for="exampleInputPassword1">Nova descri��o:</label>
                            <input type="text" class="form-control"  name="descricao" required="required" maxlength="20" placeholder="">
                        </div>                    
                        
                        <div class="d-flex flex-row justify-content-center align-items-center">
                            <button type="submit" class="btn btn-primary" style="background-color: green;">Confirmar</button>
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