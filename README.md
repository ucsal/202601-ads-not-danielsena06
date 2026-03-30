# Olimpíada de Questões — Refatoração SOLID

## Sobre o projeto

Este repositório contém a refatoração do sistema de olimpíada .  
O código original  foi reorganizado aplicando **três princípios SOLID**:  
**Single Responsibility, Interface Segregation e Dependency Inversion**.

A lógica de negócio, funcionalidades e dependências externas foram mantidas integralmente; apenas a estrutura interna do código mudou.

---

## Principais mudanças

O código foi dividido em **5 pacotes**, cada um com responsabilidade única:

```
br.com.ucsal.olimpiadas
├── model/          → dados do domínio
├── repository/     → persistência em memória + interfaces + fábrica
├── service/        → lógica de negócio + interfaces (ports)
├── ui/             → interação com o usuário no console
└── seed/           → carga de dados iniciais
```

`App.java` foi reduzido a ~60 linhas funciona exclusivamente como **Composition Root**.

---

### S — Single Responsibility Principle

> Cada classe deve ter uma única razão para mudar.

| Classe | Responsabilidade única |
|---|---|
| `ConsoleUI` | Ler input e exibir output no console |
| `ParticipanteService` | Lógica de negócio de participantes |
| `ProvaService` | Lógica de negócio de provas |
| `QuestaoService` | Lógica de negócio de questões |
| `TentativaService` | Lógica de aplicação de provas e tentativas |
| `NotaCalculator` | Calcular a nota de uma tentativa |
| `ConsoleFenRenderer` | Renderizar um tabuleiro FEN no console |
| `InMemoryParticipanteRepository` | Armazenar participantes em memória |
| `DataSeeder` | Popular o sistema com dados iniciais |

Antes da refatoração, todas essas responsabilidades estavam misturadas em `App.java`. 

---

### I — Interface Segregation Principle

> Nenhum cliente deve ser forçado a depender de métodos que não usa.

Foram criadas **interfaces específicas por responsabilidade**:

**Service Ports:**

- `ParticipanteServicePort` — operações de participante
- `ProvaServicePort` — operações de prova
- `QuestaoServicePort` — operações de questão
- `TentativaServicePort` — operações de tentativa

**Repositórios:**

- `ParticipanteRepository`, `ProvaRepository`, `QuestaoRepository`, `TentativaRepository`  
  Cada uma expõe apenas métodos relevantes à entidade.

**Outros contratos:**

- `FenRendererPort` — método único `renderizar(String fen)`  
- `IdGenerator` — método único `proximo()`

Exemplo: `ConsoleUI` depende de quatro service ports e do renderer, mas não acessa métodos de repositório diretamente.

---

### D — Dependency Inversion Principle

> Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações.

Todas as dependências são **passadas via construtor como interfaces**, nunca como classes concretas:

```java
// UI recebe abstrações, nunca implementações
public ConsoleUI(ParticipanteServicePort participanteService,
                 ProvaServicePort provaService,
                 QuestaoServicePort questaoService,
                 TentativaServicePort tentativaService,
                 FenRendererPort fenRenderer,
                 Scanner in) { ... }

// Services recebem abstrações de repositórios
public ParticipanteService(ParticipanteRepository repository) { ... }

public TentativaService(TentativaRepository repository,
                        NotaCalculator notaCalculator) { ... }
```

Toda instanciação ocorre no App.java.
---

## Restrições respeitadas

- Lógica de negócio original mantida (validações, cálculo de nota, renderização FEN, seed)
- Nenhuma funcionalidade removida (menu completo com 5 opções)
- Nenhum framework externo adicionado (sem Spring ou Lombok)
- Dependências do pom.xml mantidas: apenas JUnit 5 para testes

---

## Como executar

```bash
# Compilar
mvn compile

# Executar
mvn exec:java -Dexec.mainClass="br.com.ucsal.olimpiadas.App"

# Testes
mvn test
```

---

## Estrutura de arquivos

```
src/main/java/br/com/ucsal/olimpiadas/
├── App.java                                  ← Composition Root
├── model/
│   ├── Participante.java
│   ├── Prova.java
│   ├── Questao.java
│   ├── Resposta.java
│   └── Tentativa.java
├── repository/
│   ├── IdGenerator.java                      ← interface
│   ├── ParticipanteRepository.java           ← interface
│   ├── ProvaRepository.java                  ← interface
│   ├── QuestaoRepository.java                ← interface
│   ├── TentativaRepository.java              ← interface
│   ├── RepositoryFactory.java
│   └── InMemoryRepositories.java             ← 4 implementações + SequentialIdGenerator
├── service/
│   ├── ParticipanteServicePort.java          ← interface
│   ├── ParticipanteService.java
│   ├── ProvaServicePort.java                 ← interface
│   ├── ProvaService.java
│   ├── QuestaoServicePort.java               ← interface
│   ├── QuestaoService.java
│   ├── TentativaServicePort.java             ← interface
│   ├── TentativaService.java
│   └── NotaCalculator.java
├── ui/
│   ├── FenRendererPort.java                  ← interface
│   ├── ConsoleFenRenderer.java
│   └── ConsoleUI.java
└── seed/
    └── DataSeeder.java
```
