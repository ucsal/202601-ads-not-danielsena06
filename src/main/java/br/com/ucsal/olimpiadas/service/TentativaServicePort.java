package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.Tentativa;

/**
 * ISP — interface dedicada exclusivamente às operações de Tentativa.
 * DIP — ConsoleUI depende desta abstração, nunca da implementação concreta.
 */
public interface TentativaServicePort {
    Tentativa iniciar(long participanteId, long provaId);
    void registrarResposta(Tentativa tentativa, Questao questao, char alternativaMarcada);
    int finalizar(Tentativa tentativa);
    List<Tentativa> listarTodos();
    int calcularNota(Tentativa tentativa);
}
