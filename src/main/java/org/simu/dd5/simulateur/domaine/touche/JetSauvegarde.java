package org.simu.dd5.simulateur.domaine.touche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;

@Getter
@AllArgsConstructor
@ToString
public class JetSauvegarde {
	private CompetenceEnum competence;
	private int dd;
}
