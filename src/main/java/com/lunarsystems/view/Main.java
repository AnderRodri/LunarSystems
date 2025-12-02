package com.lunarsystems.view;

import com.lunarsystems.model.*;
import com.lunarsystems.service.MissaoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MissaoService service = new MissaoService();

        try {
            while (true) {
                System.out.println("\n--------- CONTROLE DE MISSOES LUNAR SYSTEMS ---------");
                System.out.println("1) Listar missoes");
                System.out.println("2) Criar nova missao");
                System.out.println("3) Registrar retorno da missao");
                System.out.println("4) Buscar astronautas por nome");
                System.out.println("5) Buscar missoes por astronauta");
                System.out.println("6) Listar missoes com resultados cientificos");
                System.out.println("7) Excluir registros");
                System.out.println("0) Sair");
                System.out.print("Selecione: ");

                String op = sc.nextLine();

                switch (op) {
                    case "1" -> listarMissoes(service);
                    case "2" -> criarMissao(service);
                    case "3" -> registrarRetorno(service);
                    case "4" -> buscarAstronautas(service);
                    case "5" -> buscarMissoesPorAstronauta(service);
                    case "6" -> listarComResultado(service);
                    case "7" -> menuExclusao(service);
                    case "0" -> {
                        System.out.println("Encerrando sistema...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        } finally {
            service.close();
        }
    }

    private static String lerEntradaNumerica(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String input = sc.nextLine();
            if (input != null && input.matches("\\d+")) {
                return input;
            }
            System.out.println("ERRO: O campo deve conter apenas números. Tente novamente.");
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite um número válido (ex: 1000.5).");
            }
        }
    }


    private static void menuExclusao(MissaoService service) {
        boolean noMenu = true;
        while (noMenu) {
            System.out.println("\n--- MENU DE EXCLUSAO ---");
            System.out.println("1) Excluir Missao");
            System.out.println("2) Excluir Astronauta");
            System.out.println("3) Excluir Nave");
            System.out.println("0) Voltar");
            System.out.print("Opção: ");
            String op = sc.nextLine();

            switch (op) {
                case "1" -> {
                    String cod = lerEntradaNumerica("Digite o código da missão para excluir: ");
                    service.excluirMissao(cod);
                    System.out.println("Comando enviado.");
                }
                case "2" -> {
                    System.out.print("Digite o nome do astronauta para excluir: ");
                    String nome = sc.nextLine();
                    service.excluirAstronauta(nome);
                    System.out.println("Comando enviado.");
                }
                case "3" -> {
                    System.out.print("Digite o id da nave para excluir: ");
                    String id = sc.nextLine();
                    service.excluirNave(id);
                    System.out.println("Comando enviado.");
                }
                case "0" -> noMenu = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void listarMissoes(MissaoService service) {
        var missoes = service.listarTodas();
        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missao cadastrada.");
            return;
        }
        missoes.forEach(System.out::println);
    }

    private static void criarMissao(MissaoService service) {
        System.out.println("\n--- Criar nova missao ---");

        String codigo = lerEntradaNumerica("Código da missao (apenas números): ");

        System.out.print("Nome da missão: ");
        String nome = sc.nextLine();
        System.out.print("Destino: ");
        String destino = sc.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = sc.nextLine();

        System.out.print("Data de lançamento ex:(2025-01-01): ");
        String dataLanc = sc.nextLine();


        Nave nave = escolherNave();

        List<Astronauta> astronautas = cadastrarAstronautas(nave.getCapacidadeTripulantes());

        try {
            Missao m = new Missao(codigo, nome, dataLanc, destino, objetivo, nave);

            astronautas.forEach(m::addAstronauta);

            service.criarMissao(m);
            System.out.println("\nMissao criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar missao: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static Nave escolherNave() {
        System.out.println("\n--- Escolha do tipo de nave ---");
        System.out.println("1) Nave Tripulada");
        System.out.println("2) Nave Cargueira");
        System.out.print("Tipo: ");
        String tipo = sc.nextLine();

        String id = lerEntradaNumerica("ID da nave (apenas números): ");

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        int cap = lerInteiro("Capacidade de tripulantes: ");

        Nave naveEscolhida;

        switch (tipo) {
            case "2":
                double carga = lerDecimal("Capacidade de carga (kg): ");
                naveEscolhida = new NaveCargueira(id, modelo, cap, carga);
                break;

            case "1":
            default:
                naveEscolhida = new NaveTripulada(id, modelo, cap);
                break;
        }

        return naveEscolhida;
    }

    private static List<Astronauta> cadastrarAstronautas(int capacidadeNecessaria) {
        List<Astronauta> lista = new ArrayList<>();

        System.out.println("\n--- Cadastro de Tripulação ---");
        System.out.println("A nave selecionada comporta e EXIGE exatamente " + capacidadeNecessaria + " astronautas.");


        for (int i = 0; i < capacidadeNecessaria; i++) {
            System.out.println("\nAstronauta #" + (i + 1) + " de " + capacidadeNecessaria);

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            int idade;
            while (true) {
                idade = lerInteiro("Idade: ");
                if (idade >= 21) break;
                System.out.println("ERRO: A idade mínima permitida é 21 anos.");
            }

            System.out.print("Especialidade: ");
            String esp = sc.nextLine();

            int horas = lerInteiro("Horas de voo: ");

            try {
                lista.add(new Astronauta(nome, idade, esp, horas));
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        return lista;
    }
    private static void registrarRetorno(MissaoService service) {
        String codigo = lerEntradaNumerica("Código da missao (apenas números): ");

        System.out.print("Data de retorno ex:(2025-01-01): ");
        String dt = sc.nextLine();

        System.out.print("Resultado científico: ");
        String res = sc.nextLine();
        try {
            service.registrarRetorno(codigo, dt, res);
            System.out.println("Retorno registrado!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarAstronautas(MissaoService service) {
        System.out.print("Nome do astronauta: ");
        String nome = sc.nextLine();
        var lista = service.buscarAstronautasPorNome(nome);
        if (lista.isEmpty()) System.out.println("Nenhum encontrado.");
        else lista.forEach(System.out::println);
    }

    private static void buscarMissoesPorAstronauta(MissaoService service) {
        System.out.print("Nome do astronauta: ");
        String nome = sc.nextLine();
        var lista = service.buscarMissoesPorAstronauta(nome);
        if (lista.isEmpty()) System.out.println("Nenhuma missao encontrada.");
        else lista.forEach(m -> System.out.println(m.getNome()));
    }

    private static void listarComResultado(MissaoService service) {
        var lista = service.buscarMissoesComResultado();
        if (lista.isEmpty()) System.out.println("Nenhuma missao com resultado.");
        else lista.forEach(m -> System.out.println(m.getNome() + ": " + m.getResultado()));
    }
}
