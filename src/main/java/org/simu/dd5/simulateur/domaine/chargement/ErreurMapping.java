package org.simu.dd5.simulateur.domaine.chargement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;

@AllArgsConstructor
@Getter
public class ErreurMapping {
    private Exception exception;
    private OpposantJson opposantJson;
}
