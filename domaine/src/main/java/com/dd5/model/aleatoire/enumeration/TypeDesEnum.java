package com.dd5.model.aleatoire.enumeration;

import lombok.Getter;

import java.util.Random;

@Getter
public enum TypeDesEnum {
	D4("d4", 4),
	D6("d6", 6),
	D8("d8", 8),
	D10("d10", 10),
	D12("d12", 12),
	D20("d20", 20);

	private final String typeDe;
	private final int numSides;

	TypeDesEnum(String typeDe, int numSides) {
		this.typeDe = typeDe;
		this.numSides = numSides;
	}

    public int value() {
		Random r = new Random();
		return r.nextInt(this.numSides)+1;
	}
}
