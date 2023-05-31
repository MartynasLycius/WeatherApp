package com.proit.application.data.specifications;


import com.proit.application.data.entity.Location;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.List;

public final class LocationSpecifications {
    private LocationSpecifications() {
    }

    public static Specification<Location> withName(String name) {
        return StringUtils.hasText(name) ? (root, query, builder) ->
                builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%") : null;
    }

    public static Specification<Location> withIdIn(List<Long> ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids != null && !ids.isEmpty()) {
                CriteriaBuilder.In<Long> inClause = criteriaBuilder.in(root.get("id"));
                for (Long id : ids) {
                    inClause.value(id);
                }
                return inClause;
            } else {
                return null;
            }
        };
    }
}
