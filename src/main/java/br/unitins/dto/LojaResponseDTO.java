package br.unitins.dto;

import java.util.HashMap;
import java.util.Map;
import br.unitins.model.Loja;

public class LojaResponseDTO {
    private String nome;
    private Map<String, Object> loja;

    public LojaResponseDTO(Loja loja) {
        this.nome = loja.getNome();
        this.loja = new HashMap<String, Object>();
        this.loja.put("nome", loja.getNome());

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
