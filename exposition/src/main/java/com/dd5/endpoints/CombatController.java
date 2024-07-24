package com.dd5.endpoints;

import com.dd5.combat.ICombatService;
import com.dd5.combat.IPaireService;
import com.dd5.model.combat.TousLesProtagonistes;
import com.dd5.entity.protagoniste.Equipes;
import com.dd5.protagonistes.IEquipesService;
import com.dd5.protagonistes.ProtagonisteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class CombatController {
    private final ICombatService combatService;
    private final IEquipesService equipesService;
    private final ProtagonisteService protagonisteService;
    private final IPaireService paireService;

    @PostMapping("/attaque")
    public TousLesProtagonistes lanceCombat(@RequestBody Equipes equipe) {
        return combatService.commenceCombat(equipesService.reconstruireEquipes(equipe));
    }

    @PostMapping("/un_round")
    public TousLesProtagonistes lanceUnRound(@RequestBody Equipes equipes) {
        Equipes equipes_reconstruit = equipesService.reconstruireEquipes(equipes);

        System.out.println(equipes_reconstruit);

        TousLesProtagonistes tousLesProtagonistes = protagonisteService.initialiseTousLesProtagonistes(equipes_reconstruit);
        tousLesProtagonistes.setListePaireAttaquantDefenseur(paireService.getPaireAttaquantDefenseur(equipes_reconstruit));

        return combatService.faireUnRound(tousLesProtagonistes);
    }
}
