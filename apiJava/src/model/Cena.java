package model;

import java.util.List;

public class Cena {
    private Integer idCena;
    private String descricao;
    private List<Objeto> itens;

    public Integer getIdCena() {
        return idCena;
    }

    public void setIdCena(Integer idCena) {
        this.idCena = idCena;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Objeto> getItens() {
        return itens;
    }

    public void setItens(List<Objeto> itens) {
        this.itens = itens;
    }
}
