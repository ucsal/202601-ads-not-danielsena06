package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.model.Questao;
import java.util.List;

/**
 * ISP — interface específica e mínima para Questao.
 * DIP — QuestaoService depende desta abstração.
 */
public interface QuestaoRepository {
    void salvar(Questao questao);
    List<Questao> listarPorProva(long provaId);
}
