package org.simu.dd5.simulateur.domaine.degats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class EffetReussite {
	private String degatsMinimum;
	private Map<TypeDegatEnum, Degats> degats;
}
