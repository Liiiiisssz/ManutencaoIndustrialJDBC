package org.manutencaoindustrial.model;

import java.time.LocalDate;

public class OrdemManutencao {
    private int id;
    private Maquina maquina;
    private Tecnico tecnico;
    private LocalDate dataSolicitacao;
    private String status;

    public OrdemManutencao(){
    }

    public OrdemManutencao(int id, Maquina maquina, Tecnico tecnico, LocalDate dataSolicitacao, String status) {
        this.id = id;
        this.maquina = maquina;
        this.tecnico = tecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public OrdemManutencao(Maquina maquina, Tecnico tecnico, LocalDate dataSolicitacao, String status) {
        this.maquina = maquina;
        this.tecnico = tecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nORDEM DE MANUTENÇÃO:" +
                "\nID: " + id +
                "\nMáquina: " + maquina +
                "\nTécnico: " + tecnico +
                "\nData de solicitacao: " + dataSolicitacao +
                "\nStatus: " + status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
