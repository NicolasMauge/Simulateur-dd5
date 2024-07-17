package com.dd5.caracteristiques;

import com.dd5.enumeration.MetaTypeDegatEnum;
import com.dd5.enumeration.TypeDegatEnum;
import com.dd5.protagoniste.ProtagonisteEntity;

import java.util.Map;

public interface ICaracteristiquesService {
    Map<TypeDegatEnum, MetaTypeDegatEnum> getReactionDegats(ProtagonisteEntity p);
}
