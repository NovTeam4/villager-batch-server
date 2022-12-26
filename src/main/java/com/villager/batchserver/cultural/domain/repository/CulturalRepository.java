package com.villager.batchserver.cultural.domain.repository;

import com.villager.batchserver.cultural.domain.Cultural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CulturalRepository extends JpaRepository<Cultural, Long> {

    @Modifying
    @Query(value = "delete from cultural c where DATE (c.created_at) < STR_TO_DATE(:createdAt, '%Y%m%d')", nativeQuery = true)
    void deleteCreatedAtLessThan(@Param("createdAt") String createdAt);
}
