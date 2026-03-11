package model;

public class Tarefa {

    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;
    private String dataLimite;

    public Tarefa(){}

    public Tarefa(String titulo, String descricao, String prioridade, String status, String dataLimite){
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.dataLimite = dataLimite;
    }

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getTitulo(){ return titulo; }
    public void setTitulo(String titulo){ this.titulo = titulo; }

    public String getDescricao(){ return descricao; }
    public void setDescricao(String descricao){ this.descricao = descricao; }

    public String getPrioridade(){ return prioridade; }
    public void setPrioridade(String prioridade){ this.prioridade = prioridade; }

    public String getStatus(){ return status; }
    public void setStatus(String status){ this.status = status; }

    public String getDataLimite(){ return dataLimite; }
    public void setDataLimite(String dataLimite){ this.dataLimite = dataLimite; }

}