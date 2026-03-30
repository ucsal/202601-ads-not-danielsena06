package br.com.ucsal.olimpiadas.ui;

import java.util.Scanner;

import br.com.ucsal.olimpiadas.model.Questao;
import br.com.ucsal.olimpiadas.service.ParticipanteServicePort;
import br.com.ucsal.olimpiadas.service.ProvaServicePort;
import br.com.ucsal.olimpiadas.service.QuestaoServicePort;
import br.com.ucsal.olimpiadas.service.TentativaServicePort;

/**
 * SRP — responsabilidade única: ler entradas do usuário e exibir saídas no console; 
 * DIP — recebe todas as dependências via construtor como abstrações (Ports); 
 * ISP — cada porta de serviço expõe apenas o contrato mínimo necessário:
 *       ParticipanteServicePort, ProvaServicePort, QuestaoServicePort,
 *       TentativaServicePort e FenRendererPort.
 */
public class ConsoleUI {

    //  Dependências
    private final ParticipanteServicePort participanteService;
    private final ProvaServicePort provaService;
    private final QuestaoServicePort questaoService;
    private final TentativaServicePort tentativaService;
    private final FenRendererPort fenRenderer;
    private final Scanner in;

    public ConsoleUI(ParticipanteServicePort participanteService,
                     ProvaServicePort provaService,
                     QuestaoServicePort questaoService,
                     TentativaServicePort tentativaService,
                     FenRendererPort fenRenderer,
                     Scanner in) {
        this.participanteService = participanteService;
        this.provaService        = provaService;
        this.questaoService      = questaoService;
        this.tentativaService    = tentativaService;
        this.fenRenderer         = fenRenderer;
        this.in                  = in;
    }

    //Loop principal

    public void iniciar() {
        while (true) {
            System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
            System.out.println("1) Cadastrar participante");
            System.out.println("2) Cadastrar prova");
            System.out.println("3) Cadastrar questão (A–E) em uma prova");
            System.out.println("4) Aplicar prova (selecionar participante + prova)");
            System.out.println("5) Listar tentativas (resumo)");
            System.out.println("0) Sair");
            System.out.print("> ");

            switch (in.nextLine()) {
                case "1" -> cadastrarParticipante();
                case "2" -> cadastrarProva();
                case "3" -> cadastrarQuestao();
                case "4" -> aplicarProva();
                case "5" -> listarTentativas();
                case "0" -> { System.out.println("tchau"); return; }
                default  -> System.out.println("opção inválida");
            }
        }
    }

    //Participante

    private void cadastrarParticipante() {
        System.out.print("Nome: ");
        var nome = in.nextLine();
        System.out.print("Email (opcional): ");
        var email = in.nextLine();

        try {
            var p = participanteService.cadastrar(nome, email);
            System.out.println("Participante cadastrado: " + p.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Prova 

    private void cadastrarProva() {
        System.out.print("Título da prova: ");
        var titulo = in.nextLine();
        try {
            var prova = provaService.cadastrar(titulo);
            System.out.println("Prova criada: " + prova.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Questão 

    private void cadastrarQuestao() {
        if (provaService.listarTodos().isEmpty()) {
            System.out.println("não há provas cadastradas");
            return;
        }

        var provaId = escolherProva();
        if (provaId == null) return;

        System.out.println("Enunciado:");
        var enunciado = in.nextLine();

        var alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }

        System.out.print("Alternativa correta (A–E): ");
        char correta;
        try {
            correta = Questao.normalizar(in.nextLine().trim().charAt(0));
        } catch (Exception e) {
            System.out.println("alternativa inválida");
            return;
        }

        var q = questaoService.cadastrar(provaId, enunciado, alternativas, correta, null);
        System.out.println("Questão cadastrada: " + q.getId() + " (na prova " + provaId + ")");
    }

    //Aplicar Prova

    private void aplicarProva() {
        if (participanteService.listarTodos().isEmpty()) {
            System.out.println("cadastre participantes primeiro");
            return;
        }
        if (provaService.listarTodos().isEmpty()) {
            System.out.println("cadastre provas primeiro");
            return;
        }

        var participanteId = escolherParticipante();
        if (participanteId == null) return;

        var provaId = escolherProva();
        if (provaId == null) return;

        var questoes = questaoService.listarPorProva(provaId);
        if (questoes.isEmpty()) {
            System.out.println("esta prova não possui questões cadastradas");
            return;
        }

        var tentativa = tentativaService.iniciar(participanteId, provaId);
        System.out.println("\n--- Início da Prova ---");

        for (var q : questoes) {
            System.out.println("\nQuestão #" + q.getId());
            System.out.println(q.getEnunciado());
            System.out.println("Posição inicial:");
            fenRenderer.renderizar(q.getFenInicial());

            for (var alt : q.getAlternativas()) System.out.println(alt);

            System.out.print("Sua resposta (A–E): ");
            char marcada;
            try {
                marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
            } catch (Exception e) {
                System.out.println("resposta inválida (marcando como errada)");
                marcada = 'X';
            }

            tentativaService.registrarResposta(tentativa, q, marcada);
        }

        int nota = tentativaService.finalizar(tentativa);
        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
    }

    //Listar Tentativas 

    private void listarTentativas() {
        System.out.println("\n--- Tentativas ---");
        for (var t : tentativaService.listarTodos()) {
            System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n",
                    t.getId(), t.getParticipanteId(), t.getProvaId(),
                    tentativaService.calcularNota(t), t.getRespostas().size());
        }
    }

    //Helpers de seleção

    private Long escolherParticipante() {
        System.out.println("\nParticipantes:");
        for (var p : participanteService.listarTodos())
            System.out.printf("  %d) %s%n", p.getId(), p.getNome());
        System.out.print("Escolha o id do participante: ");
        try {
            long id = Long.parseLong(in.nextLine());
            if (!participanteService.existePorId(id)) { System.out.println("id inválido"); return null; }
            return id;
        } catch (Exception e) { System.out.println("entrada inválida"); return null; }
    }

    private Long escolherProva() {
        System.out.println("\nProvas:");
        for (var p : provaService.listarTodos())
            System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
        System.out.print("Escolha o id da prova: ");
        try {
            long id = Long.parseLong(in.nextLine());
            if (!provaService.existePorId(id)) { System.out.println("id inválido"); return null; }
            return id;
        } catch (Exception e) { System.out.println("entrada inválida"); return null; }
    }
}
