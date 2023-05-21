package br.unitins.dto.usuario;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.model.Sexo;
import br.unitins.model.Endereco;
import br.unitins.model.Telefone;
import br.unitins.model.Usuario;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String cpf,
        String email,
        String login,
        String nomeImagem,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Sexo sexo,

        List<Telefone> telefone,
        List<Endereco> endereco

) {
   public static UsuarioResponseDTO valueOf(Usuario u){
     if(u.getPessoa() == null)
        return new UsuarioResponseDTO(u.getId(), null, null, null,u.getLogin(), null, null, null, null);
        
    return new UsuarioResponseDTO(u.getId(),
        u.getPessoa().getNome(), 
        u.getPessoa().getCpf(), 
        u.getPessoa().getEmail(), 
        u.getLogin(), 
        u.getNomeImagem(),
        u.getPessoa().getSexo(), 
        u.getTelefone(), 
        u.getEndereco());
   }


}
