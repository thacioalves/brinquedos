package br.unitins.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BrinquedoDTO {

    @NotBlank(message = "O campo nome deve ser informado.")
    private String nome;

    @NotBlank(message = "O campo nome deve ser informado.")
    private String marca;

    @NotNull(message = "O campo idLoja deve ser informado.")
    private Long idLoja;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(Long idLoja) {
        this.idLoja = idLoja;
    }

}
