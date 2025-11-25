package com.lunarsystems.repository;

import com.lunarsystems.model.Missao;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class NitriteRepository {

    private final Nitrite db;
    private final ObjectRepository<Missao> repo;

    public NitriteRepository(String caminhoArquivo) {
        this.db = Nitrite.builder()
                .filePath(caminhoArquivo)
                .openOrCreate();

        this.repo = db.getRepository(Missao.class);
    }

    public void close() {
        db.close();
    }


    public void add(Missao m) {
        repo.insert(m);
    }

    public void update(Missao m) {
        repo.update(m);
    }

    public void delete(String codigo) {
        repo.remove(ObjectFilters.eq("codigo", codigo));
    }

    public List<Missao> findAll() {
        return repo.find().toList();
    }

    public Missao findByCodigo(String codigo) {
        return repo.find(ObjectFilters.eq("codigo", codigo))
                .firstOrDefault();
    }

    
    public void update(String codigo, LocalDate dataRetorno, String resultado) {
        Missao m = findByCodigo(codigo);

        if (m == null) return;

        m.setDataRetorno(dataRetorno);
        m.setResultado(resultado);

        repo.update(m);
    }

    public List<Missao> findComResultado() {
        return repo.find().toList()
                .stream()
                .filter(m -> m.getResultado() != null && !m.getResultado().isBlank())
                .collect(Collectors.toList());
    }
}