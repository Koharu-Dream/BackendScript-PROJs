# ProjetoHospital — Sistema de Gestão Hospitalar

Trabalho da disciplina de Programação de Scripts — Java Moderno e Programação Funcional.

## Integrantes

- Caio Vitor de Souza Torres Morais
- Beatriz Carvalheiro

## Sobre o projeto

Sistema de console para triagem hospitalar, feito para praticar recursos do Java moderno:

- Records para representar o paciente de forma imutável, com validação no construtor
- Streams para filtrar, ordenar e calcular dados da lista de pacientes, sem usar laços
- Optional e OptionalDouble para lidar com casos em que não há dados, evitando NullPointerException
- Switch expression no menu do console
- Method references nos pontos onde fazia sentido usar (ex: `Paciente::idade`)

## Estrutura

```
ProjetoHospital/
├── README.md
└── src/
    ├── NivelEmergencia.java
    ├── Paciente.java
    ├── Hospital.java
    └── GestorHospitalar.java
```

## Como executar

Requer JDK 17 ou superior (o projeto usa records e switch expressions).

```bash
javac -d out src/*.java
java -cp out GestorHospitalar
```

## Menu

| Opção | O que faz |
|-------|-----------|
| 1 | Admitir paciente, com validação de nome e idade |
| 2 | Relatório de triagem: pacientes URGENTE ou CRITICO, do mais velho para o mais novo |
| 3 | Estatísticas: média de idade dos críticos e total de pacientes com plano de saúde |
| 4 | Busca o paciente mais idoso cadastrado |
| 5 | Sair |

## Principais decisões de implementação

- O construtor do record `Paciente` valida os dados: idade não pode ser negativa e nome não pode ser vazio. Se algo estiver errado, lança `IllegalArgumentException`.
- `listarEmergenciais()` filtra os pacientes URGENTE e CRITICO com `EnumSet` e ordena por idade do maior para o menor, usando `.reversed()`.
- `calcularMediaIdadeCriticos()` usa `mapToInt(...).average()`, que devolve um `OptionalDouble` — assim não há erro de divisão por zero quando não existe nenhum paciente crítico.
- `buscarPacienteMaisIdoso()` usa `stream().max(...)`, que devolve um `Optional<Paciente>`, tratado no console com `ifPresentOrElse`.
- `contarSegurados()` conta os pacientes com plano de saúde usando `filter(Paciente::possuiPlano).count()`.
- O menu usa switch expression com `yield` para decidir se o programa continua ou encerra.
- Nenhuma coleção é percorrida com `for`, `while` ou `foreach` tradicional — o único `while` do programa serve só para repetir o menu.

## Dificuldades e soluções

1. **Record não tem "set"** — no começo tentamos alterar a idade de um paciente já cadastrado, do jeito que faríamos numa classe comum. Só que record não tem esse tipo de método, ele é imutável. Entendemos que, pra "mudar" um dado, o certo é criar um novo `Paciente` com as informações atualizadas.
2. **Scanner pulando o `nextLine()`** — quando testamos o menu pela primeira vez, depois de ler um número o programa pulava a leitura do nome. Resolvemos usando `nextLine()` pra tudo e convertendo o texto pra número com `Integer.parseInt` só quando precisava.
3. **Lambda em vez de method reference** — no início escrevíamos tudo como lambda, por exemplo `p -> p.idade()`, até perceber que dava pra trocar por `Paciente::idade` e o código ficava mais curto e mais fácil de ler.
4. **Uso errado do Optional** — tentamos chamar `.get()` direto no `Optional` de `buscarPacienteMaisIdoso()` e o programa quebrou quando ainda não tinha nenhum paciente cadastrado. Depois vimos que o jeito certo era usar `ifPresentOrElse`, que já trata os dois casos (tem paciente ou não tem).

## Tecnologias

- Java 17+ (Records, Switch Expressions, Streams API, Optional)