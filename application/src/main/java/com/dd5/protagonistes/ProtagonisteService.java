package com.dd5.protagonistes;

import com.dd5.enumeration.StatutEquipeEnum;
import com.dd5.enumeration.StatutProtagonisteEnum;
import com.dd5.combat.EtatProtagoniste;
import com.dd5.combat.EvolutionProtagoniste;
import com.dd5.combat.TousLesProtagonistes;
import com.dd5.protagoniste.Equipes;
import com.dd5.protagoniste.ProtagonisteEntity;
import com.dd5.protagoniste.ProtagonisteRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProtagonisteService implements IProtagonisteService {
    private final ProtagonisteRepository protagonisteRepository;

    public ProtagonisteService(ProtagonisteRepository protagonisteRepository) {
        this.protagonisteRepository = protagonisteRepository;
    }

    public ProtagonisteEntity save(ProtagonisteEntity protagonisteEntity) {
        return protagonisteRepository.save(protagonisteEntity);
    }

    @Override
    public List<ProtagonisteEntity> findAll() {
        return protagonisteRepository.findAll();
    }

    public ProtagonisteEntity findById(Long id) {
        return protagonisteRepository.findById(id).orElseThrow();
    }

    @Override
    public TousLesProtagonistes initialiseTousLesProtagonistes(Equipes equipes) {
        List<ProtagonisteEntity> protagonisteList = fusionneDeuxListes(equipes.getEquipeA(), equipes.getEquipeB());
        Map<ProtagonisteEntity, EvolutionProtagoniste> evolutionParProtagoniste = new HashMap<>();

        protagonisteList
                .forEach(p -> evolutionParProtagoniste.put(
                            p,
                            new EvolutionProtagoniste(
                                    p,
                                    List.of(new EtatProtagoniste(
                                            p.getPointDeVie(),
                                            new HashSet<>(),
                                            StatutProtagonisteEnum.VIVANT))
                            )
                        )
                );

        return new TousLesProtagonistes(
                equipes,
                evolutionParProtagoniste,
                new ArrayList<>(),
                StatutEquipeEnum.VIVANTE,
                StatutEquipeEnum.VIVANTE);
    }

    private List<ProtagonisteEntity> fusionneDeuxListes(List<ProtagonisteEntity> listeA,
                                                        List<ProtagonisteEntity> listeB) {
        List<ProtagonisteEntity> fusion = new ArrayList<>(listeA);
        fusion.addAll(listeB);

        return fusion;
    }
}
