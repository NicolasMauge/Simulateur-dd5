package com.dd5.entity.effets;

import com.dd5.enumeration.TypeDegatEnum;
import com.dd5.model.aleatoire.NombreDeDes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("degats")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class AttaqueDeBaseEntity extends AbstractEffetEntity{
	private TypeDegatEnum typeDegat;

	@Embedded
	private NombreDeDes nombreDeDes;
}
