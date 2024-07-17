package com.dd5.caracteristiques;

import com.dd5.enumeration.MetaTypeDegatEnum;
import com.dd5.enumeration.TypeDegatEnum;
import com.dd5.protagoniste.ProtagonisteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaracteristiquesService implements ICaracteristiquesService {
    private static final Logger logger = LoggerFactory.getLogger(CaracteristiquesService.class);

    @Override
    public Map<TypeDegatEnum, MetaTypeDegatEnum> getReactionDegats(ProtagonisteEntity p) {
        Map<TypeDegatEnum, MetaTypeDegatEnum> dictionnaire = new HashMap<>();

        List<TypeDegatEnum> resistances = getListeTypeDegatEnum(p.getListeResistances());
        resistances.forEach(r -> dictionnaire.put(r, MetaTypeDegatEnum.RESISTANCE));

        List<TypeDegatEnum> vulnerabilites = getListeTypeDegatEnum(p.getListeVulnerabilites());
        vulnerabilites.forEach(r -> dictionnaire.put(r, MetaTypeDegatEnum.VULNERABILITE));

        List<TypeDegatEnum> immmunites = getListeTypeDegatEnum(p.getListeImmunites());
        immmunites.forEach(r -> dictionnaire.put(r, MetaTypeDegatEnum.IMMUNITE));

        return dictionnaire;
    }

    private List<TypeDegatEnum> getListeTypeDegatEnum(String champADecouper) {
        if (champADecouper == null) {
            return new ArrayList<>();
        }

        String[] parties = champADecouper.split("\\|");

        List<TypeDegatEnum> liste = new ArrayList<>();
        for (String valeur : parties) {
            try {
                liste.add(TypeDegatEnum.valueOf(valeur));
            } catch (IllegalArgumentException e) {
                logger.warn("Erreur de conversion de {}", valeur);
            }
        }

        return liste;
    }
}


