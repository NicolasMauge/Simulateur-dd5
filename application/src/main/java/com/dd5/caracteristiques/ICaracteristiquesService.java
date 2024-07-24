package com.dd5.caracteristiques;

import com.dd5.model.caracteristiques.enumeration.CaracteristiqueEnum;
import com.dd5.enumeration.MetaTypeDegatEnum;
import com.dd5.enumeration.TypeDegatEnum;
import com.dd5.entity.protagoniste.ProtagonisteEntity;

import java.util.Map;

public interface ICaracteristiquesService {
    Map<TypeDegatEnum, MetaTypeDegatEnum> getReactionDegats(ProtagonisteEntity p);
    int getCaracteristique(ProtagonisteEntity p, CaracteristiqueEnum caracteristique);
}
