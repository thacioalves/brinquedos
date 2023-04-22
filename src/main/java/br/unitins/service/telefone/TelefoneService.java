package br.unitins.service.telefone;

import java.util.List;

import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.telefone.TelefoneResponseDTO;

public interface TelefoneService {
    List<TelefoneResponseDTO> getAll();

    TelefoneResponseDTO findById(long id);

    TelefoneResponseDTO create(TelefoneDTO telefonedto);

    TelefoneResponseDTO update(long id, TelefoneDTO telefonedto);

    void delete(long id);

    List<TelefoneResponseDTO> findByNumero(String numero);

    long count();
}
