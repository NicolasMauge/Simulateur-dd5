package org.simu.dd5.simulateur.domaine.opposant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.simu.dd5.simulateur.domaine.opposant.typeenum.CompetenceEnum;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Competence {
	private CompetenceEnum competence;
	private Integer bonus;
}
