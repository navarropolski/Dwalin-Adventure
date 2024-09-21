package model;

public class Cena_Objeto {
    private Integer idCenaObjeto;
    private Integer idObjeto;
    private Integer idCena;
    private String nome;
    private String usar;

    public Integer getIdCenaObjeto() {
        return idCenaObjeto;
    }

    public void setIdCenaObjeto(Integer idCenaObjeto) {
        this.idCenaObjeto = idCenaObjeto;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public Integer getIdCena() {
        return idCena;
    }

    public void setIdCena(Integer idCena) {
        this.idCena = idCena;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsar() {
        return usar;
    }

    public void setUsar(String usar) {
        this.usar = usar;
    }

    public boolean usarObjeto(Objeto Objeto) {
        if (this.usar != null && !this.usar.isEmpty()) {
            System.out.println("Você pode usar o comando: use " + Objeto.getNome() + " no " + this.nome);
            return true;
        }else {
            System.out.println("Você usou o " + Objeto.getNome() + " no " + this.nome + ".");
            return false;
        }
    }
}
