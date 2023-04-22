package br.unitins.dto.usuario;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.model.Endereco;
import br.unitins.model.Produto;
import br.unitins.model.Sexo;
import br.unitins.model.Telefone;
import br.unitins.model.Usuario;

public record UsuarioResponseDTO(
    
        Long id,

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,

        @NotBlank(message = "O campo cpf deve ser informado.")
        @Size(max = 14, message = "O cpf deve posssuir no máximo 14 caracteres.")
        String cpf,
        String email,
        String senha,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Sexo sexo,
        
        List<Telefone> telefone,
        List<Endereco> endereco,
        List<Produto> produto
) {
    public UsuarioResponseDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail(),
        usuario.getSenha(), usuario.getSexo(), usuario.getTelefone(), usuario.getEndereco(), usuario.getProduto()
        );
    }
}
