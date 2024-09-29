package org.simu.dd5.simulateur.application.commun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

@Service
public class ToStringPretty {
    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final Logger logger = LoggerFactory.getLogger(ToStringPretty.class);
    private final String OUTPUT_FILE_INCOMPLET = "/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/non-complets.json";
    private final String OUTPUT_FILE_COMPLET = "/Users/nicolasmauge/Documents/01-projets/2024-08_nvle-base-fusionnee/complets.json";

    public void print(Object o) {
        logger.info(cleanJsonFromObject(o));
    }

    public void save_incomplet(Object o) {
        save(o, OUTPUT_FILE_INCOMPLET);
    }

    public void save_complet(Object o) {
        save(o, OUTPUT_FILE_COMPLET);
    }

    public void save(Object o, String fichier) {
        Path cheminFichier = Paths.get(fichier);

        try {
            // Écrire dans le fichier en utilisant Files.write
            Files.writeString(cheminFichier, cleanJsonFromObject(o));
        } catch (IOException e) {
            logger.error("Erreur dans la sauvegarde", e);
        }
    }

    public String cleanJsonFromObject(Object o) {
        // Conversion de l'objet Java en JsonNode (structure intermédiaire)
        JsonNode jsonNode = objectMapper.valueToTree(o);

        // Nettoyage des champs null dans le JsonNode sans modifier l'objet d'origine
        JsonNode cleanedJsonNode = removeNullValues(jsonNode);

        // Sérialisation du JsonNode nettoyé en JSON
        try {
            return objectMapper.writeValueAsString(cleanedJsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode removeNullValues(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode childNode = field.getValue();

                // Appel récursif pour les objets complexes ou les tableaux
                if (childNode.isObject() || childNode.isArray()) {
                    removeNullValues(childNode);
                }

                // Si la valeur est null, supprimer le champ
                if (childNode.isNull()) {
                    fields.remove();
                }
            }
        } else if (jsonNode.isArray()) {
            for (int i = 0; i < jsonNode.size(); i++) {
                removeNullValues(jsonNode.get(i));
            }
        }
        return jsonNode;
    }
}
