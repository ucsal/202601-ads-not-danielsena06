package br.com.ucsal.olimpiadas.service;

import java.util.List;
import java.util.Optional;

import br.com.ucsal.olimpiadas.model.Participante;
import br.com.ucsal.olimpiadas.repository.ParticipanteRepository;

/**
 * SRP — responsabilidade única: lógica de negócio de Participantes.
 * DIP — depende de ParticipanteRepository (abstração) via construtor e implementa
 * ISP — a interface ParticipanteServicePort expõe apenas o que a UI precisa.
 */
public class ParticipanteService implements ParticipanteServicePort {

    private final ParticipanteRepository repository;

    public ParticipanteService(ParticipanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Participante cadastrar(String nome, String email) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("nome inválido");
        var p = new Participante();
        p.setNome(nome);
        p.setEmail(email);
        repository.salvar(p);
        return p;
    }

    @Override
    public List<Participante> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public Optional<Participante> buscarPorId(long id) {
        return repository.buscarPorId(id);
    }

    @Override
    public boolean existePorId(long id) {
        return repository.existePorId(id);
    }
}
