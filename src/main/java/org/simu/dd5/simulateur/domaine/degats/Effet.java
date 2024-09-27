package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.simu.dd5.simulateur.domaine.touche.DDTestReussite;

import java.util.Map;

@Getter
@AllArgsConstructor
@ToString
public class Effet {
	private Map<TypeDegatEnum, Degats> degats;
	private DDTestReussite test;
	private EffetEchec effetEchec;
	private EffetReussite effetReussite;
}
