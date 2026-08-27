// paciente do hospital
// usei record porque o professor pediu pra praticar essa parte da materia
public record Paciente(String nome, int idade, NivelEmergencia nivel, boolean possuiPlano) {

    // valida os dados antes de criar o paciente
    public Paciente {
        if (idade < 0) {
            throw new IllegalArgumentException("idade nao pode ser negativa");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome nao pode ser vazio");
        }
        nome = nome.trim();
    }
}
