package com.dd5.entity.protagoniste;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Equipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<ProtagonisteEntity> equipeA;

    @OneToMany
    private List<ProtagonisteEntity> equipeB;

    @Override
    public String toString() {
        return "Equipes{" +
                "id=" + id +
                ", equipeA=" + equipeA +
                ", equipeB=" + equipeB +
                '}';
    }
}

