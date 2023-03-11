package br.unitins.dto;

import java.util.HashMap;
import java.util.Map;

import br.unitins.model.Brinquedo;

public class BrinquedoResponseDTO {
    private Long id;
    private String nome;
    private Map<String, Object> loja;

    public BrinquedoResponseDTO(Brinquedo brinquedo) {
        this.id = brinquedo.getId();
        this.nome = brinquedo.getNome();
        this.loja = new HashMap<String, Object>();
        this.loja.put("nome", brinquedo.getNome());
        this.loja.put("estado", brinquedo.getLoja().getEstado());
        this.loja.put("cidade", brinquedo.getLoja().getCidade());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
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
