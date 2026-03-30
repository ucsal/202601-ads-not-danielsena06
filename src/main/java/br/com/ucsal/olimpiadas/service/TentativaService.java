package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.model.Resposta;
import br.com.ucsal.olimpiadas.model.Tentativa;
import br.com.ucsal.olimpiadas.repository.TentativaRepository;

/**
 * SRP — responsabilidade única: lógica de aplicação de provas e gestão de tentativas.
 * DIP — depende de TentativaRepository (abstração) e NotaCalculator via construtor; 
 *       nunca instancia colaboradores diretamente.
 * ISP — implementa TentativaServicePort, interface mínima necessária para a UI.
 */
public class TentativaService implements TentativaServicePort {

    private final TentativaRepository repository;
    private final NotaCalculator notaCalculator;

    public TentativaService(TentativaRepository repository, NotaCalculator notaCalculator) {
        this.repository = repository;
        this.notaCalculator = notaCalculator;
    }

    @Override
    public Tentativa iniciar(long participanteId, long provaId) {
        var t = new Tentativa();
        t.setParticipanteId(participanteId);
        t.setProvaId(provaId);
        return t;
    }

    @Override
    public void registrarResposta(Tentativa tentativa, Questao questao, char alternativaMarcada) {
        var r = new Resposta();
        r.setQuestaoId(questao.getId());
        r.setAlternativaMarcada(alternativaMarcada);
        r.setCorreta(questao.isRespostaCorreta(alternativaMarcada));
        tentativa.getRespostas().add(r);
    }

    @Override
    public int finalizar(Tentativa tentativa) {
        repository.salvar(tentativa);
        return notaCalculator.calcular(tentativa);
    }

    @Override
    public List<Tentativa> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public int calcularNota(Tentativa tentativa) {
        return notaCalculator.calcular(tentativa);
    }
}
