package com.dd5.attaque;

import com.dd5.enumeration.TypeArmeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttaqueEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private TypeArmeEnum typeArme;
	private int hitBonus;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<AttaqueParDegatEntity> attaqueParDegatList;

	// TODO il y aura aussi les effets à prendre en compte (empoisonné)

	@Override
	public String toString() {
		return "AttaqueEntity{" +
				"id=" + id +
				", typeArme=" + typeArme +
				", hitBonus=" + hitBonus +
				", attaqueParDegatList=" + attaqueParDegatList +
				'}';
	}
}
