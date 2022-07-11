package app;

public class Lancamento {
    int id;
    int idConta;
    int idCategoria;
    float valor;
    String operacao;
    String data;
    String descricao;
  
    public Lancamento(){
    }
        
    public Lancamento(int id, int idConta, int idCategoria, float valor, String operacao, String data, String descricao){
        this.id = id;
        this.idConta = idConta;
        this.idCategoria = idCategoria;
        this.valor = valor;
        this.operacao = operacao;
        this.data = data;
        this.descricao = descricao;
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

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
