package com.vpolosov.trainee.merge_xml.service.specification;

import com.vpolosov.trainee.merge_xml.model.History;
import org.springframework.data.jpa.domain.Specification;

public class HistorySpecifications {

    public static Specification<History> docRefEquals(String docRef) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("docRef"), docRef));
    }
}
