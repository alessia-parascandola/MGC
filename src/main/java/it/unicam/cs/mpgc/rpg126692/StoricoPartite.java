package it.unicam.cs.mpgc.rpg126692;

import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

/** Storico degli esiti, non salvataggio di una partita riprendibile. */
public final class StoricoPartite {
    private final Path file;
    public StoricoPartite() { this(Path.of(System.getProperty("user.home"), ".fuga-dal-cassero", "partite.tsv")); }
    public StoricoPartite(Path file) { this.file = file; }
    public void salva(String personaggio, String esito) throws IOException {
        Files.createDirectories(file.toAbsolutePath().getParent());
        Files.writeString(file, Instant.now() + "\t" + personaggio.replaceAll("[\\t\\r\\n]", " ") + "\t" + esito + "\n",
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    public List<String> leggi() throws IOException {
        return Files.exists(file) ? Files.readAllLines(file, StandardCharsets.UTF_8) : List.of();
    }
}
