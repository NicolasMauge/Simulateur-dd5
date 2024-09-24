package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.DegatsJson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DegatsJsonToDegatsMapper {
	private final DegatsFromString degatsFromString;

	public List<Degats> mapToListeDegats(List<DegatsJson> listeDegatsJson) {
		if (listeDegatsJson == null) {
			return null;
		}
		return listeDegatsJson
				.stream()
				.map(degatsFromString::mapToDegats)
				.collect(Collectors.toList());
	}
}
