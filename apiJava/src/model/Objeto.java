package model;

public class Objeto {
    private Integer idObjeto;
    private String nome;
    private String descricao;

    public Integer getIdItem() {
        return idObjeto;
    }

    public void setIdItem(Integer idItem) {
        this.idObjeto = idItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

