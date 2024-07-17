package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum TypeArmeEnum {
	EPEE("Epée", DistanceAttaqueEnum.CORPS_A_CORPS),
	EPEE_COURTE("Epée courte", DistanceAttaqueEnum.CORPS_A_CORPS),
	ARC_LONG("Arc long", DistanceAttaqueEnum.DISTANCE),
	DAGUE("Dague", DistanceAttaqueEnum.CORPS_A_CORPS);

	private final String typeArme;
	private final DistanceAttaqueEnum distance;

	TypeArmeEnum(String typeArme, DistanceAttaqueEnum distance) {
		this.typeArme = typeArme;
		this.distance = distance;
	}

}
