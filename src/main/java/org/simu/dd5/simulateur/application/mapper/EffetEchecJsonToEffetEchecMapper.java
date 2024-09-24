package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.EffetEchec;
import org.simu.dd5.simulateur.domaine.degats.EffetEchecJson;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EffetEchecJsonToEffetEchecMapper {
	private final DegatsJsonToDegatsMapper degatsJsonToDegatsMapper;

	public EffetEchec mapToEffetEchec(EffetEchecJson effetEchecJson) {
		if (effetEchecJson == null) {
			return null;
		}

		return new EffetEchec(
				degatsJsonToDegatsMapper.mapToListeDegats(effetEchecJson.getDegats()),
				null // TODO
		);
	}
}
