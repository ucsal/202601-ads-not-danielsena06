package br.com.ucsal.olimpiadas.repository;

import br.com.ucsal.olimpiadas.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * SRP — responsabilidade única: gerar IDs incrementais.
 * DIP — depende da abstração IdGenerator, não de implementação concreta.
 */

class SequentialIdGenerator implements IdGenerator {
    private long proximo = 1;

    @Override
    public long proximo() { return proximo++; }
}
/**
 *SRP — responsabilidade única: gerenciar armazenamento em memória de Participante.
 *ISP — implementa apenas os métodos da abstração ParticipanteRepository.
 *DIP — depende de IdGenerator (abstração) via construtor, nunca de implementação concreta.
 */

class InMemoryParticipanteRepository implements ParticipanteRepository {
    private final List<Participante> store = new ArrayList<>();
    private final IdGenerator ids;

    InMemoryParticipanteRepository(IdGenerator ids) { this.ids = ids; }

    @Override
    public void salvar(Participante p) {
        p.setId(ids.proximo());
        store.add(p);
    }

    @Override
    public List<Participante> listarTodos() { return List.copyOf(store); }

    @Override
    public Optional<Participante> buscarPorId(long id) {
        return store.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public boolean existePorId(long id) {
        return store.stream().anyMatch(p -> p.getId() == id);
    }
}

/**
 * SRP — responsabilidade única: gerenciar armazenamento em memória de Prova.
 * ISP — implementa apenas os métodos da abstração ProvaRepository.
 * DIP — depende de IdGenerator (abstração) via construtor, nunca de implementação concreta.
 */
class InMemoryProvaRepository implements ProvaRepository {
    private final List<Prova> store = new ArrayList<>();
    private final IdGenerator ids;

    InMemoryProvaRepository(IdGenerator ids) { this.ids = ids; }

    @Override
    public void salvar(Prova prova) {
        prova.setId(ids.proximo());
        store.add(prova);
    }

    @Override
    public List<Prova> listarTodos() { return List.copyOf(store); }

    @Override
    public boolean existePorId(long id) {
        return store.stream().anyMatch(p -> p.getId() == id);
    }
}
/**
 * SRP — responsabilidade única: gerenciar armazenamento em memória de Questao.
 * ISP — implementa apenas os métodos da abstração QuestaoRepository.
 * DIP — depende de IdGenerator (abstração) via construtor, nunca de implementação concreta.
 */
class InMemoryQuestaoRepository implements QuestaoRepository {
    private final List<Questao> store = new ArrayList<>();
    private final IdGenerator ids;

    InMemoryQuestaoRepository(IdGenerator ids) { this.ids = ids; }

    @Override
    public void salvar(Questao q) {
        q.setId(ids.proximo());
        store.add(q);
    }

    @Override
    public List<Questao> listarPorProva(long provaId) {
        return store.stream().filter(q -> q.getProvaId() == provaId).toList();
    }
}
/**
 * SRP — responsabilidade única: gerenciar armazenamento em memória de Tentativa.
 * ISP — implementa apenas os métodos da abstração TentativaRepository.
 * DIP — depende de IdGenerator (abstração) via construtor, nunca de implementação concreta.
 */
class InMemoryTentativaRepository implements TentativaRepository {
    private final List<Tentativa> store = new ArrayList<>();
    private final IdGenerator ids;

    InMemoryTentativaRepository(IdGenerator ids) { this.ids = ids; }

    @Override
    public void salvar(Tentativa t) {
        t.setId(ids.proximo());
        store.add(t);
    }

    @Override
    public List<Tentativa> listarTodos() { return List.copyOf(store); }
}
