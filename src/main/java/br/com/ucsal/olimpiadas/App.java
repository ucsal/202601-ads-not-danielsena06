package br.com.ucsal.olimpiadas;


import java.util.Scanner;

import br.com.ucsal.olimpiadas.repository.RepositoryFactory;
import br.com.ucsal.olimpiadas.seed.DataSeeder;
import br.com.ucsal.olimpiadas.service.NotaCalculator;
import br.com.ucsal.olimpiadas.service.ParticipanteService;
import br.com.ucsal.olimpiadas.service.ParticipanteServicePort;
import br.com.ucsal.olimpiadas.service.ProvaService;
import br.com.ucsal.olimpiadas.service.ProvaServicePort;
import br.com.ucsal.olimpiadas.service.QuestaoService;
import br.com.ucsal.olimpiadas.service.QuestaoServicePort;
import br.com.ucsal.olimpiadas.service.TentativaService;
import br.com.ucsal.olimpiadas.service.TentativaServicePort;
import br.com.ucsal.olimpiadas.ui.ConsoleFenRenderer;
import br.com.ucsal.olimpiadas.ui.ConsoleUI;

/**
 /**
 * S — Single Responsibility Principle
 *     Cada classe tem uma única razão para mudar:
 *       • Models       → dados e regras internas do domínio
 *       • Repositories → persistência em memória
 *       • Services     → lógica de negócio por entidade
 *       • UI           → interação com o usuário no console
 *       • Seeder       → carga de dados iniciais
 *       • FenRenderer  → renderização do tabuleiro FEN
 *
 * I — Interface Segregation Principle
 *     Interfaces segregadas por responsabilidade:
 *       • ParticipanteServicePort  — apenas operações de participante
 *       • ProvaServicePort         — apenas operações de prova
 *       • QuestaoServicePort       — apenas operações de questão
 *       • TentativaServicePort     — apenas operações de tentativa
 *       • FenRendererPort          — apenas renderização FEN
 *     Nenhum cliente depende de métodos que não usa.
 *
 * D — Dependency Inversion Principle
 *     Módulos de alto nível (UI, Seeder) dependem de abstrações (Ports),
 *     nunca de implementações concretas. Toda instanciação ocorre aqui,
 *     no único Composition Root da aplicação.
 *
 */
 */
public class App {

    public static void main(String[] args) {

        // Repositórios
        var participanteRepo = RepositoryFactory.criarParticipanteRepository();
        var provaRepo        = RepositoryFactory.criarProvaRepository();
        var questaoRepo      = RepositoryFactory.criarQuestaoRepository();
        var tentativaRepo    = RepositoryFactory.criarTentativaRepository();

        // Serviços concretos referenciados apenas pelas suas interfaces 
        ParticipanteServicePort participanteService = new ParticipanteService(participanteRepo);
        ProvaServicePort        provaService        = new ProvaService(provaRepo);
        QuestaoServicePort      questaoService      = new QuestaoService(questaoRepo);
        NotaCalculator          notaCalculator      = new NotaCalculator();
        TentativaServicePort    tentativaService    = new TentativaService(tentativaRepo, notaCalculator);

        // Seed de dados iniciais 
        new DataSeeder(provaService, questaoService).executar();

        var ui = new ConsoleUI(
                participanteService,
                provaService,
                questaoService,
                tentativaService,
                new ConsoleFenRenderer(),   // FenRendererPort
                new Scanner(System.in)
        );

        ui.iniciar();
    }
}
