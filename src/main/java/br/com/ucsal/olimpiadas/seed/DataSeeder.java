package br.com.ucsal.olimpiadas.seed;

import br.com.ucsal.olimpiadas.service.ProvaServicePort;
import br.com.ucsal.olimpiadas.service.QuestaoServicePort;

/**
 * SRP — responsabilidade única: popular o sistema com dados iniciais.
 * DIP — depende de ProvaServicePort e QuestaoServicePort (abstrações) via construtor.
 * ISP — usa apenas os métodos necessários de cada porta de serviço, sem depender de extras.
 */
public class DataSeeder {

    private final ProvaServicePort provaService;
    private final QuestaoServicePort questaoService;

    public DataSeeder(ProvaServicePort provaService, QuestaoServicePort questaoService) {
        this.provaService   = provaService;
        this.questaoService = questaoService;
    }

    public void executar() {
        var prova = provaService.cadastrar("Olimpíada 2026 • Nível 1 • Prova A");

        questaoService.cadastrar(
            prova.getId(),
            """
            Questão 1 — Mate em 1.
            É a vez das brancas.
            Encontre o lance que dá mate imediatamente.
            """,
            new String[]{"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"},
            'C',
            "6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1"
        );
    }
}
