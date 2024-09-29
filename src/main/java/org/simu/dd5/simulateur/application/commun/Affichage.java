package org.simu.dd5.simulateur.application.commun;

import org.simu.dd5.simulateur.domaine.opposant.Opposant;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class Affichage {
    public void afficheOpposantsClasses(List<Opposant> opposantListe) {
        opposantListe
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Opposant::getClassementELO).reversed())
                .toList()
                .forEach(o -> System.out.println(o.getNom() + " : " + o.getClassementELO()));
    }
}
