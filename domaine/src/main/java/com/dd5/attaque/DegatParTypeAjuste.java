package com.dd5.attaque;

import com.dd5.enumeration.MetaTypeDegatEnum;
import com.dd5.enumeration.TypeDegatEnum;

public record DegatParTypeAjuste(TypeDegatEnum typeDegat, Integer degat, Integer degatAjuste, MetaTypeDegatEnum effetEnnemi) {
}
