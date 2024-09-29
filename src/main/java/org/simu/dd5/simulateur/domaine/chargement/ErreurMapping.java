package org.simu.dd5.simulateur.domaine.chargement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.opposant.OpposantJson;

@AllArgsConstructor
@Getter
@ToString
public class ErreurMapping {
    private Exception exception;
    private OpposantJson opposantJson;
}
