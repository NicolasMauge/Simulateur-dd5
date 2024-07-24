package com.dd5;

import com.dd5.model.aleatoire.NombreDeDes;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class LancerDesService implements ILancerDesService {
    @Override
    public int valeurMoyenne(NombreDeDes nombreDeDes) {
        return (nombreDeDes.getTypeDes().getNumSides()+1)/2 * nombreDeDes.getNombreDes() + nombreDeDes.getAdditionDes();
    }

    @Override
    public int getValeurDes(NombreDeDes nombreDeDes) {
        return IntStream
                .range(0, nombreDeDes.getNombreDes())
                .map(_ -> nombreDeDes.getTypeDes().value())
                .sum() + nombreDeDes.getAdditionDes();
    }
}
