package br.unitins.model;

import jakarta.persistence.Entity;

@Entity
public class Brinquedo extends Produto {

    private String marca;
    private String idade;

    public String getMarca() {
        return marca;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
