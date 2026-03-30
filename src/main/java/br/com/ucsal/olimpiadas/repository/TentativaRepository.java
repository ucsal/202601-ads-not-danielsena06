package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.model.Tentativa;
import java.util.List;

/**
 * ISP — interface específica e mínima para Tentativa.
 * DIP — TentativaService depende desta abstração.
 */
public interface TentativaRepository {
    void salvar(Tentativa tentativa);
    List<Tentativa> listarTodos();
}
