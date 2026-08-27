import java.util.List;
import java.util.Scanner;

public class GestorHospitalar {

    private static final Hospital hospital = new Hospital();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("SISTEMA DE GESTAO HOSPITALAR");

        boolean executando = true;
        while (executando) {
            executando = switch (exibirMenu()) {
                case 1 -> {
                    admitirPaciente();
                    yield true;
                }
                case 2 -> {
                    relatorioTriagem();
                    yield true;
                }
                case 3 -> {
                    painelEstatisticas();
                    yield true;
                }
                case 4 -> {
                    buscarCasoDeRisco();
                    yield true;
                }
                case 5 -> {
                    System.out.println("Encerrando o sistema");
                    yield false;
                }
                default -> {
                    System.out.println("Opcao invalida, tente de novo");
                    yield true;
                }
            };
        }
    }

    // mostra o menu e le a opcao escolhida
    private static int exibirMenu() {
        System.out.println();
        System.out.println("------ MENU ------");
        System.out.println("1 - Admitir Paciente");
        System.out.println("2 - Relatorio de Triagem");
        System.out.println("3 - Painel de Estatisticas");
        System.out.println("4 - Buscar Caso de Risco");
        System.out.println("5 - Sair");
        System.out.println("------------------");
        System.out.print("Escolha uma opcao: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // cadastra um paciente novo
    private static void admitirPaciente() {
        System.out.println();
        System.out.println("--- ADMISSAO DE PACIENTE ---");
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Idade: ");
            int idade = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Nivel de emergencia (LEVE, MODERADO, URGENTE, CRITICO): ");
            String entradaNivel = scanner.nextLine().trim().toUpperCase();
            NivelEmergencia nivel;
            try {
                nivel = NivelEmergencia.valueOf(entradaNivel);
            } catch (IllegalArgumentException e) {
                System.out.println("Nivel invalido, use LEVE, MODERADO, URGENTE ou CRITICO");
                return;
            }

            System.out.print("Possui plano de saude? (s/n): ");
            boolean possuiPlano = scanner.nextLine().trim().equalsIgnoreCase("s");

            hospital.admitir(new Paciente(nome, idade, nivel, possuiPlano));
            System.out.println("Paciente admitido com sucesso");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na admissao: " + e.getMessage());
        }
    }

    // mostra os pacientes urgente e critico
    private static void relatorioTriagem() {
        System.out.println();
        System.out.println("--- RELATORIO DE TRIAGEM ---");
        List<Paciente> emergenciais = hospital.listarEmergenciais();
        if (emergenciais.isEmpty()) {
            System.out.println("Nenhum paciente urgente ou critico no momento");
        } else {
            emergenciais.forEach(GestorHospitalar::imprimirPaciente);
        }
    }

    // mostra as estatisticas do hospital
    private static void painelEstatisticas() {
        System.out.println();
        System.out.println("--- PAINEL DE ESTATISTICAS ---");

        hospital.calcularMediaIdadeCriticos().ifPresentOrElse(
                media -> System.out.printf("Media de idade dos criticos: %.1f anos%n", media),
                () -> System.out.println("Sem pacientes criticos no momento"));

        System.out.println("Total de pacientes com plano de saude: " + hospital.contarSegurados());
    }

    // busca o paciente mais velho cadastrado
    private static void buscarCasoDeRisco() {
        System.out.println();
        System.out.println("--- BUSCAR CASO DE RISCO ---");
        hospital.buscarPacienteMaisIdoso().ifPresentOrElse(
                paciente -> {
                    System.out.println("Paciente mais idoso encontrado:");
                    imprimirPaciente(paciente);
                },
                () -> System.out.println("Nenhum paciente cadastrado"));
    }

    private static void imprimirPaciente(Paciente p) {
        System.out.printf("  %-20s | %3d anos | %-9s | Plano: %s%n",
                p.nome(), p.idade(), p.nivel(), p.possuiPlano() ? "Sim" : "Nao");
    }
}
