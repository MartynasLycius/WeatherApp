package com.proit.application.data.repository;

import com.proit.application.data.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
    Location findByExternalId(long externalId);

    @Query("SELECT l.externalId FROM Location l WHERE l.id IN :locationIds")
    Set<Long> findExternalIdsByIdIn(List<Long> locationIds);

    @Query("SELECT l.id FROM Location l WHERE l.externalId = :externalId")
    Long findIdByExternalId(Long externalId);

    Page<Location> findAllByIdIn(List<Long> locationIds, Pageable pageable, Specification<Location> specification);
}
