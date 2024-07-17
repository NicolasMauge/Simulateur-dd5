package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum DistanceAttaqueEnum {
	CORPS_A_CORPS("Attaque corps à corps"),
	DISTANCE("Attaque à distance");


	private final String description;

	DistanceAttaqueEnum(String description) {
		this.description = description;
	}

}
