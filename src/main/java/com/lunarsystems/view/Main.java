package com.lunarsystems.view;

import com.lunarsystems.model.*;
import com.lunarsystems.service.MissaoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        MissaoService service = new MissaoService();

        try {
            while (true) {
                System.out.println("\n========= CONTROLE DE MISSÕES LUNAR SYSTEMS =========");
                System.out.println("1) Listar missões");
                System.out.println("2) Criar nova missão");
                System.out.println("3) Registrar retorno da missão");
                System.out.println("4) Buscar astronautas por nome");
                System.out.println("5) Buscar missões por astronauta");
                System.out.println("6) Listar missões com resultados científicos");
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

    private static void listarMissoes(MissaoService service) {
        var missoes = service.listarTodas();
        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missão cadastrada.");
            return;
        }

        missoes.forEach(m -> {
            System.out.println("\n---");
            System.out.println("Código: " + m.getCodigo());
            System.out.println("Nome: " + m.getNome());
            System.out.println("Destino: " + m.getDestino());
            System.out.println("Lançamento: " + m.getDataLancamento());
            System.out.println("Nave: " + m.getNave());
            System.out.println("Tripulação:");
            m.getAstronautas().forEach(a -> System.out.println("  - " + a));
            if (m.getResultado() != null) {
                System.out.println("Resultado científico: " + m.getResultado());
            }
        });
    }

    private static void criarMissao(MissaoService service) {

        System.out.println("\n=== Criar nova missão ===");

        System.out.print("Código da missão: ");
        String codigo = sc.nextLine();

        System.out.print("Nome da missão: ");
        String nome = sc.nextLine();

        System.out.print("Destino: ");
        String destino = sc.nextLine();

        System.out.print("Objetivo: ");
        String objetivo = sc.nextLine();

        System.out.print("Data de lançamento (YYYY-MM-DD): ");
        LocalDate dataLanc = LocalDate.parse(sc.nextLine());

        Nave nave = escolherNave();

        List<Astronauta> astronautas = cadastrarAstronautas();

        Missao m = new Missao(codigo, nome, dataLanc, destino, objetivo, nave);
        astronautas.forEach(m::addAstronauta);

        try {
            service.criarMissao(m);
            System.out.println("\nMissão criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar missão: " + e.getMessage());
        }
    }

    private static Nave escolherNave() {

        System.out.println("\n=== Escolha do tipo de nave ===");
        System.out.println("1) Nave Tripulada");
        System.out.println("2) Nave Cargueira");
        System.out.print("Tipo: ");
        String tipo = sc.nextLine();

        System.out.print("ID da nave: ");
        String id = sc.nextLine();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Capacidade de tripulantes: ");
        int cap = Integer.parseInt(sc.nextLine());

        return switch (tipo) {
            case "1" -> new NaveTripulada(id, modelo, cap);
            case "2" -> {
                System.out.print("Capacidade de carga (kg): ");
                double carga = Double.parseDouble(sc.nextLine());
                yield new NaveCargueira(id, modelo, cap, carga);
            }
            default -> {
                System.out.println("Tipo inválido, escolhendo nave tripulada padrão.");
                yield new NaveTripulada(id, modelo, cap);
            }
        };
    }

    private static List<Astronauta> cadastrarAstronautas() {
        List<Astronauta> lista = new ArrayList<>();
        System.out.print("Quantos astronautas deseja adicionar? ");
        int qtd = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < qtd; i++) {
            System.out.println("\nAstronauta #" + (i + 1));

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());

            System.out.print("Especialidade: ");
            String esp = sc.nextLine();

            System.out.print("Horas de voo: ");
            int horas = Integer.parseInt(sc.nextLine());

            lista.add(new Astronauta(nome, idade, esp, horas));
        }
        return lista;
    }

    private static void registrarRetorno(MissaoService service) {
        System.out.print("Código da missão: ");
        String codigo = sc.nextLine();

        System.out.print("Data de retorno (YYYY-MM-DD): ");
        LocalDate dt = LocalDate.parse(sc.nextLine());

        System.out.print("Resultado científico: ");
        String resultado = sc.nextLine();

        try {
            service.registrarRetorno(codigo, dt, resultado);
            System.out.println("Retorno registrado!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarAstronautas(MissaoService service) {
        System.out.print("Nome do astronauta: ");
        String nome = sc.nextLine();

        var encontrados = service.buscarAstronautasPorNome(nome);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum astronauta encontrado.");
        } else {
            encontrados.forEach(a -> System.out.println("- " + a));
        }
    }

    private static void buscarMissoesPorAstronauta(MissaoService service) {
        System.out.print("Nome do astronauta: ");
        String nome = sc.nextLine();

        var missoes = service.buscarMissoesPorAstronauta(nome);

        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missão encontrada para esse astronauta.");
        } else {
            missoes.forEach(m -> {
                System.out.println("\n---");
                System.out.println("Código: " + m.getCodigo());
                System.out.println("Nome: " + m.getNome());
            });
        }
    }

    private static void listarComResultado(MissaoService service) {
        var missoes = service.buscarMissoesComResultado();
        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missão com resultado registrado.");
        } else {
            missoes.forEach(m -> {
                System.out.println("\n---");
                System.out.println("Código: " + m.getCodigo());
                System.out.println("Nome: " + m.getNome());
                System.out.println("Resultado: " + m.getResultado());
            });
        }
    }
}
