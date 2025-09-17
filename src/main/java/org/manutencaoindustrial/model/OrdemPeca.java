package org.manutencaoindustrial.model;

public class OrdemPeca {
    private OrdemManutencao ordem;
    private Peca peca;
    private double quantidade;

    public OrdemPeca(OrdemManutencao ordem, Peca peca, double quantidade) {
        this.ordem = ordem;
        this.peca = peca;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "\nORDEM DE PEÇAS:" +
                "\nOrdem de manutenção: " + ordem +
                "\nPeça: " + peca +
                "\nQuantidade: " + quantidade;
    }

    public OrdemManutencao getOrdem() {
        return ordem;
    }

    public void setOrdem(OrdemManutencao ordem) {
        this.ordem = ordem;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
