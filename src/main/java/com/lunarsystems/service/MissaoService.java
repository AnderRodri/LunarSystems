package com.lunarsystems.service;

import com.lunarsystems.model.Astronauta;
import com.lunarsystems.model.Missao;
import com.lunarsystems.model.Nave;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.time.LocalDate;
import java.util.List;

public class MissaoService {

    private Nitrite db;
    private ObjectRepository<Missao> missaoRepo;
    private ObjectRepository<Astronauta> astronautaRepo;
    private ObjectRepository<Nave> naveRepo;

    public MissaoService() {
        // Inicializa o banco de dados
        db = Nitrite.builder()
                .compressed()
                .filePath("lunar_system.db")
                .openOrCreate();

        missaoRepo = db.getRepository(Missao.class);
        astronautaRepo = db.getRepository(Astronauta.class);
        naveRepo = db.getRepository(Nave.class);
    }


    public void excluirMissao(String codigo) {
        missaoRepo.remove(ObjectFilters.eq("codigo", codigo));
    }

    public void excluirAstronauta(String nome) {
        astronautaRepo.remove(ObjectFilters.eq("nome", nome));
    }

    public void excluirNave(String id) {
        naveRepo.remove(ObjectFilters.eq("id", id));
    }

    public void criarMissao(Missao m) {
        missaoRepo.insert(m);
        if(m.getNave() != null) naveRepo.update(m.getNave(), true);
        for(Astronauta a : m.getAstronautas()) {
            astronautaRepo.update(a, true);
        }
    }

    public List<Missao> listarTodas() {
        return missaoRepo.find().toList();
    }

    public void registrarRetorno(String codigo, String dataRetorno, String resultado) {
        Missao m = missaoRepo.find(ObjectFilters.eq("codigo", codigo)).firstOrDefault();
        if (m != null) {
            m.setDataRetorno(dataRetorno);
            m.setResultado(resultado);
            missaoRepo.update(m);
        } else {
            throw new RuntimeException("Missão não encontrada: " + codigo);
        }
    }

    public List<Astronauta> buscarAstronautasPorNome(String nome) {
        return astronautaRepo.find(ObjectFilters.regex("nome", "(?i).*" + nome + ".*")).toList();
    }

    public List<Missao> buscarMissoesPorAstronauta(String nomeAstronauta) {
        return missaoRepo.find().toList().stream()
                .filter(m -> m.getAstronautas().stream()
                        .anyMatch(a -> a.getNome().equalsIgnoreCase(nomeAstronauta)))
                .toList();
    }

    public List<Missao> buscarMissoesComResultado() {
        return missaoRepo.find(ObjectFilters.not(ObjectFilters.eq("resultado", null))).toList();
    }

    public void close() {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }
}