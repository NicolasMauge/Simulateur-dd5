package com.dd5.model.combat;

import com.dd5.enumeration.StatutEquipeEnum;
import com.dd5.entity.protagoniste.Equipes;
import com.dd5.entity.protagoniste.ProtagonisteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class TousLesProtagonistes {
    private Equipes equipes;
    private Map<ProtagonisteEntity, EvolutionProtagoniste> evolutionParProtagoniste;
    private List<PaireAttaquantDefenseur> listePaireAttaquantDefenseur;
    private StatutEquipeEnum statutEquipeA;
    private StatutEquipeEnum statutEquipeB;
}
