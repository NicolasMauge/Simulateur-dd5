package org.simu.dd5.simulateur.application.commun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.simu.dd5.simulateur.application.chargement.ChargementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ToStringPretty {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final Logger logger = LoggerFactory.getLogger(ChargementService.class);
    private final String OUTPUT_FILE = "/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/non-complets.json";

    public void print(Object o) {
        try {
            // Convertir l'objet en JSON formaté
            String jsonFormatted = mapper.writeValueAsString(o);
            logger.info(jsonFormatted);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String pretty(Object o) {
        try {
            // Convertir l'objet en JSON formaté
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Object o) {
        Path cheminFichier = Paths.get(OUTPUT_FILE);

        try {
            // Écrire dans le fichier en utilisant Files.write
            Files.writeString(cheminFichier, pretty(o));
        } catch (IOException e) {
            logger.error("Erreur dans la sauvegarde", e);
        }
    }
}
