package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Prova;
import java.util.List;

/**
 * ISP — interface dedicada exclusivamente às operações de Prova.
 * DIP — ConsoleUI e DataSeeder dependem desta abstração.
 */
public interface ProvaServicePort {
    Prova cadastrar(String titulo);
    List<Prova> listarTodos();
    boolean existePorId(long id);
}
