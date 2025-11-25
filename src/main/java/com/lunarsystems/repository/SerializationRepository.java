package com.lunarsystems.repository;


import com.lunarsystems.model.Missao;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class SerializationRepository {
private final Path file;


public SerializationRepository(Path file) {
this.file = file;
}


@SuppressWarnings("unchecked")
public List<Missao> loadAll() {
if (!Files.exists(file)) return new ArrayList<>();
try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
Object obj = ois.readObject();
if (obj instanceof List) return (List<Missao>) obj;
} catch (Exception e) {
e.printStackTrace();
}
return new ArrayList<>();
}


public void saveAll(List<Missao> missoes) {
try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
oos.writeObject(missoes);
} catch (IOException e) {
e.printStackTrace();
}
}


public void add(Missao m) {
List<Missao> todas = loadAll();
todas.add(m);
saveAll(todas);
}
}