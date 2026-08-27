import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class Hospital {

    private final List<Paciente> pacientes = new ArrayList<>();

    // adiciona um paciente na lista
    public void admitir(Paciente p) {
        pacientes.add(p);
    }

    // pega os pacientes urgente ou critico, do mais velho pro mais novo
    public List<Paciente> listarEmergenciais() {
        return pacientes.stream()
                .filter(p -> p.nivel() == NivelEmergencia.URGENTE || p.nivel() == NivelEmergencia.CRITICO)
                .sorted(Comparator.comparingInt(Paciente::idade).reversed())
                .toList();
    }

    // media de idade dos pacientes criticos
    public OptionalDouble calcularMediaIdadeCriticos() {
        return pacientes.stream()
                .filter(p -> p.nivel() == NivelEmergencia.CRITICO)
                .mapToInt(Paciente::idade)
                .average();
    }

    // acha o paciente mais velho da lista
    public Optional<Paciente> buscarPacienteMaisIdoso() {
        return pacientes.stream()
                .max(Comparator.comparingInt(Paciente::idade));
    }

    // conta quantos pacientes tem plano de saude
    public long contarSegurados() {
        return pacientes.stream()
                .filter(Paciente::possuiPlano)
                .count();
    }
}
