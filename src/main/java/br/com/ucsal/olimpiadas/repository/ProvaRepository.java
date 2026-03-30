package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.model.Prova;
import java.util.List;

/**
 * ISP — interface específica e mínima para Prova.
 * DIP — ProvaService depende desta abstração.
 */
public interface ProvaRepository {
    void salvar(Prova prova);
    List<Prova> listarTodos();
    boolean existePorId(long id);
}
