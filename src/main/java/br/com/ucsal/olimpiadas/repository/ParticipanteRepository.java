package br.com.ucsal.olimpiadas.repository;

import java.util.List;
import java.util.Optional;

import br.com.ucsal.olimpiadas.model.Participante;

/**
 * ISP — interface específica para Participante.
 * DIP — serviços dependem desta abstração, nunca de implementações concretas.
 */
public interface ParticipanteRepository {
    void salvar(Participante participante);
    List<Participante> listarTodos();
    Optional<Participante> buscarPorId(long id);
    boolean existePorId(long id);
}
