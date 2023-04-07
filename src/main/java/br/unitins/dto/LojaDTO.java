package br.unitins.dto;

import javax.validation.constraints.NotBlank;

public class LojaDTO {

    @NotBlank(message = "O campo nome deve ser informado.")
    private String nome;

    @NotBlank(message = "O campo cnpj deve ser informado.")
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
