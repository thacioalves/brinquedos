package br.unitins.service.tokenJwt;

import br.unitins.model.Usuario;

public interface TokenJwtService {
    public String generateJwt(Usuario usuario);
}
