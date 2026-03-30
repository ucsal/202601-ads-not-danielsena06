package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.repository.QuestaoRepository;

/**
 * SRP — responsabilidade única: lógica de negócio de Questões.
 * DIP — depende de QuestaoRepository (abstração) via construtor.
 * ISP — implementa QuestaoServicePort, interface mínima usada pela UI e pelo Seeder.
 */
public class QuestaoService implements QuestaoServicePort {

    private final QuestaoRepository repository;

    public QuestaoService(QuestaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Questao cadastrar(long provaId, String enunciado, String[] alternativas,
                             char alternativaCorreta, String fenInicial) {
        var q = new Questao();
        q.setProvaId(provaId);
        q.setEnunciado(enunciado);
        q.setAlternativas(alternativas);
        q.setAlternativaCorreta(alternativaCorreta);
        q.setFenInicial(fenInicial);
        repository.salvar(q);
        return q;
    }

    @Override
    public List<Questao> listarPorProva(long provaId) {
        return repository.listarPorProva(provaId);
    }
}
