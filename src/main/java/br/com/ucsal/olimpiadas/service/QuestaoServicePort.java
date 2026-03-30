package br.com.ucsal.olimpiadas.service;

import br.com.ucsal.olimpiadas.model.Questao;
import java.util.List;

/**
 * ISP — interface dedicada exclusivamente às operações de Questao.
 * DIP — ConsoleUI e DataSeeder dependem desta abstração.
 */
public interface QuestaoServicePort {
    Questao cadastrar(long provaId, String enunciado, String[] alternativas,
                      char alternativaCorreta, String fenInicial);
    List<Questao> listarPorProva(long provaId);
}
