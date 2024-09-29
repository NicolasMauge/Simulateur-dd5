package org.simu.dd5.simulateur.domaine.effet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.simu.dd5.simulateur.domaine.degats.DegatsJson;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EffetEchecJson {
	private List<DegatsJson> degats;
	private List<String> etat;
}
