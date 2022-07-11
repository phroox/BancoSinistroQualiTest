package app;

public class Emprestimo {
    int id;
    int idConta;
    String operacao;
    float valor;

  
    public Emprestimo(){
    }
        
    public Emprestimo(int id, int idConta, String operacao, float valor){
        this.id = id;
        this.idConta = idConta;
        this.operacao = operacao;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
    
    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
