package com.dd5.model.combat;

import com.dd5.entity.protagoniste.ProtagonisteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class EvolutionProtagoniste {
    private ProtagonisteEntity p;
    private List<EtatProtagoniste> etatProtagonisteList;
}
