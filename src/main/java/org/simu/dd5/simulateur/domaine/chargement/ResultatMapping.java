package org.simu.dd5.simulateur.domaine.chargement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.domaine.opposant.Opposant;

import java.util.List;

@AllArgsConstructor
@Getter
public class ResultatMapping {
    private List<Opposant> opposantListe;
    private List<ErreurMapping> erreurMappingListe;
}
