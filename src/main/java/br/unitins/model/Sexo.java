package br.unitins.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sexo {
    MASCULINO(2, "Masculino"),
    FEMININO(1, "Feminino");

    private int id;
    private String label;

    Sexo(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Sexo valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (Sexo sexo : Sexo.values()) {
            if (id.equals(sexo.getId()))
                return sexo;
        }
        throw new IllegalArgumentException("Id inválido:" + id);
    }

}