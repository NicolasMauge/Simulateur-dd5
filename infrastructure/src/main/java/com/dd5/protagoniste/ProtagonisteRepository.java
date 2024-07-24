package com.dd5.protagoniste;

import com.dd5.entity.protagoniste.ProtagonisteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtagonisteRepository extends JpaRepository<ProtagonisteEntity, Long> {
}
