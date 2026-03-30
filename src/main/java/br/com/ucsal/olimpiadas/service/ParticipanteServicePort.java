package br.com.ucsal.olimpiadas.service;

import java.util.List;
import java.util.Optional;

import br.com.ucsal.olimpiadas.model.Participante;

/**
 * ISP — interface dedicada exclusivamente às operações de Participante.
 * DIP — permite substituir a implementação sem alterar quem usa esta interface.
 */
public interface ParticipanteServicePort {
    Participante cadastrar(String nome, String email);
    List<Participante> listarTodos();
    Optional<Participante> buscarPorId(long id);
    boolean existePorId(long id);
}
