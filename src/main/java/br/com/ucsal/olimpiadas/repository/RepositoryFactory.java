package br.com.ucsal.olimpiadas.repository;

/**
 * DIP — centraliza a criação das implementações concretas de repositórios.
 *       Serviços e UI nunca instanciam repositórios diretamente.     
 * ISP — cada método retorna apenas o que o cliente precisa, sem expor funcionalidades extras.
 */
public class RepositoryFactory {

    public static ParticipanteRepository criarParticipanteRepository() {
        return new InMemoryParticipanteRepository(new SequentialIdGenerator());
    }

    public static ProvaRepository criarProvaRepository() {
        return new InMemoryProvaRepository(new SequentialIdGenerator());
    }

    public static QuestaoRepository criarQuestaoRepository() {
        return new InMemoryQuestaoRepository(new SequentialIdGenerator());
    }

    public static TentativaRepository criarTentativaRepository() {
        return new InMemoryTentativaRepository(new SequentialIdGenerator());
    }
}
