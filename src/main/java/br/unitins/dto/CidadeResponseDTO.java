package br.unitins.dto;

import java.util.HashMap;
import java.util.Map;

import br.unitins.model.Cidade;

public class CidadeResponseDTO {
    private String nome;
    private Map<String, Object> estado;

    public CidadeResponseDTO(Cidade cidade) {

        this.nome = cidade.getNome();
        this.estado = new HashMap<String, Object>();
        this.estado.put("nome", cidade.getEstado().getNome());
        this.estado.put("sigla", cidade.getEstado().getSigla());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Map<String, Object> getEstado() {
        return estado;
    }

    public void setEstado(Map<String, Object> estado) {
        this.estado = estado;
    }

}
