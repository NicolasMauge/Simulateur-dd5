package org.simu.dd5.simulateur.application.mapper;

import lombok.AllArgsConstructor;
import org.simu.dd5.simulateur.domaine.degats.Degats;
import org.simu.dd5.simulateur.domaine.degats.DegatsJson;
import org.simu.dd5.simulateur.domaine.degats.typeenum.TypeDegatEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DegatsJsonToDegatsMapper {
	private final DegatsFromString degatsFromString;

	public Map<TypeDegatEnum, Degats> mapToListeDegats(List<DegatsJson> listeDegatsJson) {
		if (listeDegatsJson == null) {
			return null;
		}

		return listeDegatsJson
				.stream()
				.filter(Objects::nonNull)
				.map(degatsFromString::mapToDegats)
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(ancien, _) -> ancien
				));
	}
}
