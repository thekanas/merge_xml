package com.vpolosov.trainee.mergexml.service.specification;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.model.History;
import org.springframework.data.jpa.domain.Specification;

/**
 * Спецификация {@link History} для запросов Criteria API.
 *
 * @author Ali Takushinov
 */
public class HistorySpecifications {

    /**
     * Формирование условия для поиска по ref документу.
     *
     * @param docRef ссылка на документ.
     * @return условие запроса для поиска по ссылке документа.
     */
    @Loggable
    public static Specification<History> docRefEquals(String docRef) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("docRef"), docRef);
    }
}
