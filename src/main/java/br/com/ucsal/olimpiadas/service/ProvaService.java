package br.com.ucsal.olimpiadas.service;

import java.util.List;

import br.com.ucsal.olimpiadas.model.Prova;
import br.com.ucsal.olimpiadas.repository.ProvaRepository;

/**
 * SRP — responsabilidade única: lógica de negócio de Provas.
 * DIP — depende de ProvaRepository (abstração) via construtor.
 * ISP — implementa ProvaServicePort, interface mínima necessária para a UI.
 */
public class ProvaService implements ProvaServicePort {

    private final ProvaRepository repository;

    public ProvaService(ProvaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Prova cadastrar(String titulo) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("título inválido");
        var prova = new Prova();
        prova.setTitulo(titulo);
        repository.salvar(prova);
        return prova;
    }

    @Override
    public List<Prova> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public boolean existePorId(long id) {
        return repository.existePorId(id);
    }
}
