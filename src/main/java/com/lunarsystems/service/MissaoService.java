package com.lunarsystems.service;

import com.lunarsystems.model.Astronauta;
import com.lunarsystems.model.Missao;
import com.lunarsystems.model.Nave;
import com.lunarsystems.repository.SerializationRepository;
import com.lunarsystems.repository.NitriteRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MissaoService {

    private final SerializationRepository serialRepo;
    private final NitriteRepository nitRepo;

    public MissaoService() {

        Path dataDir = Paths.get("data");
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            System.out.println("Erro ao criar diretório 'data': " + e.getMessage());
        }

        this.serialRepo = new SerializationRepository(Paths.get("data/missions.bin"));
        this.nitRepo = new NitriteRepository("data/lunarsystems.db");
    }

    public void close() {
        nitRepo.close();
    }

    public List<Missao> listarTodas() {
        return serialRepo.loadAll();
    }

    public Optional<Missao> buscarPorCodigo(String codigo) {
        return listarTodas().stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public void criarMissao(Missao m) {
        if (buscarPorCodigo(m.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Código de missão já existente: " + m.getCodigo());
        }

        for (Astronauta a : m.getAstronautas()) {
            if (a.getIdade() < 21) {
                throw new IllegalArgumentException(
                        "Astronauta menor de 21 anos não permitido: " + a.getNome()
                );
            }
        }

        Nave nave = m.getNave();
        if (m.getAstronautas().size() > nave.getCapacidadeTripulantes()) {
            throw new IllegalArgumentException(
                    "A nave " + nave.getModelo() +
                            " suporta apenas " + nave.getCapacidadeTripulantes() +
                            " tripulantes."
            );
        }

        serialRepo.add(m);
        nitRepo.add(m);
    }

    public void registrarRetorno(String codigo, LocalDate dataRetorno, String resultado) {

        List<Missao> todas = serialRepo.loadAll();
        boolean encontrado = false;

        for (Missao m : todas) {
            if (m.getCodigo().equalsIgnoreCase(codigo)) {
                m.setDataRetorno(dataRetorno);
                m.setResultado(resultado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("Missão não encontrada: " + codigo);
        }

        serialRepo.saveAll(todas);

        nitRepo.update(codigo, dataRetorno, resultado);
    }

    public List<Astronauta> buscarAstronautasPorNome(String nome) {
        List<Astronauta> encontrados = new ArrayList<>();
        for (Missao m : listarTodas()) {
            for (Astronauta a : m.getAstronautas()) {
                if (a.getNome().equalsIgnoreCase(nome)) {
                    encontrados.add(a);
                }
            }
        }
        return encontrados;
    }

    public List<Missao> buscarMissoesPorAstronauta(String nome) {
        List<Missao> missoes = new ArrayList<>();
        for (Missao m : listarTodas()) {
            for (Astronauta a : m.getAstronautas()) {
                if (a.getNome().equalsIgnoreCase(nome)) {
                    missoes.add(m);
                }
            }
        }
        return missoes;
    }

    public List<Missao> buscarMissoesComResultado() {
        List<Missao> missoes = new ArrayList<>();
        for (Missao m : listarTodas()) {
            if (m.getResultado() != null && !m.getResultado().isBlank()) {
                missoes.add(m);
            }
        }
        return missoes;
    }
}
