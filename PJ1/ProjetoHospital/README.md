# Projeto Hospital

Trabalho da disciplina de Programação de Scripts.

## Integrantes

- Beatriz Mariana Antolini Carvalheiro
- Caio Vitor de Souza Torres Morais

## Sobre o projeto

Sistema de console para fazer a triagem de pacientes de um hospital. Usamos records, streams, Optional e switch expression, que foram os assuntos vistos na disciplina.

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

Precisa do JDK 17 ou mais novo (o projeto usa record e switch expression).

```bash
javac -d out src/*.java
java -cp out GestorHospitalar
```

## Menu

| Opção | O que faz |
|-------|-----------|
| 1 | Admitir paciente |
| 2 | Relatório de triagem: pacientes URGENTE ou CRITICO, do mais velho pro mais novo |
| 3 | Estatísticas: média de idade dos críticos e total de pacientes com plano |
| 4 | Busca o paciente mais idoso |
| 5 | Sair |

## Observações

- O `Paciente` é um record, então depois de criado não dá pra mudar os dados. O construtor já valida se a idade é negativa ou se o nome está vazio.
- Pra achar o paciente mais velho e pra calcular a média de idade usamos `Optional` e `OptionalDouble`, porque a lista pode estar vazia e assim evita erro.
- No menu usamos switch expression com `yield` pra saber se o programa continua ou fecha.

## Tecnologias

Java 17+ (Records, Switch Expression, Streams, Optional)
