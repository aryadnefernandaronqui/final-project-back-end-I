package com.br.aryadneronqui.Final.Project.Back.End.I.repositories.specifications;

import com.br.aryadneronqui.Final.Project.Back.End.I.enums.EStatus;
import com.br.aryadneronqui.Final.Project.Back.End.I.models.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class TaskSpecifications {
    public static Specification<Task> filterByTitleAndStatusAndArchived(String title, EStatus status, Boolean archived){
        return (root, query, criteriaBuilder) -> {
            var conditions = new ArrayList<Predicate>();

            if(title != null && !title.isEmpty()){
                conditions.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),"%" + title.toLowerCase() + "%")
                );
            }
            if(status != null){
                conditions.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if(archived != null){
                    conditions.add(criteriaBuilder.equal(root.get("archived"), archived));
            }
            return criteriaBuilder.and(conditions.toArray(new Predicate[0]));
        };
    }
}
