package org.manutencaoindustrial.model;

public class Tecnico {
    private int id;
    private String nome;
    private String especialidade;

    public Tecnico(){
    }

    public Tecnico(int id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Tecnico(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "\nTÉCNICO:" +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nEspecialidade: " + especialidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
