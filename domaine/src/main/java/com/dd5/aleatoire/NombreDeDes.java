package com.dd5.aleatoire;

import com.dd5.enumeration.TypeDesEnum;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NombreDeDes {
    private int nombreDes;
    private TypeDesEnum typeDes;
    private int additionDes;

    @Override
    public String toString() {
        return "NombreDeDes{" +
                "nombreDes=" + nombreDes +
                ", typeDes=" + typeDes +
                ", additionDes=" + additionDes +
                '}';
    }
}
