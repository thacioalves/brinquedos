package br.unitins.dto;

import java.util.HashMap;
import java.util.Map;

import br.unitins.model.Estado;

public class EstadoResponseDTO {
    private String nome;
    private String sigla;
    private Map<String, Object> estado;

    public EstadoResponseDTO(Estado estado) {
        this.nome = estado.getNome();
        this.sigla = estado.getSigla();
        this.estado = new HashMap<String, Object>();
        this.estado.put("nome", estado.getNome());
        this.estado.put("sigla", estado.getSigla());

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Map<String, Object> getEstado() {
        return estado;
    }

    public void setEstado(Map<String, Object> estado) {
        this.estado = estado;
    }

}
