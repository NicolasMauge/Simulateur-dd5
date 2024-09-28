package org.simu.dd5.simulateur.domaine.probabilites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.etats.typeenum.EtatEnum;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Getter
public class Prediction {
	private int probabiliteSucces;
	private Map<TypeDegatEnum, Integer> degats;
	private Set<EtatEnum> etats;
}
