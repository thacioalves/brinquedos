package br.unitins.dto;

import java.util.HashMap;
import java.util.Map;

import br.unitins.model.Brinquedo;

public class BrinquedoResponseDTO {
    private String nome;
    private String marca;
    private Map<String, Object> loja;

    public BrinquedoResponseDTO(Brinquedo brinquedo) {
        this.nome = brinquedo.getNome();
        this.marca = brinquedo.getMarca();
        this.loja = new HashMap<String, Object>();
        this.loja.put("nome", brinquedo.getLoja().getNome());
        this.loja.put("cnpj", brinquedo.getLoja().getCnpj());
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Map<String, Object> getLoja() {
        return loja;
    }

    public void setLoja(Map<String, Object> loja) {
        this.loja = loja;
    }

}
