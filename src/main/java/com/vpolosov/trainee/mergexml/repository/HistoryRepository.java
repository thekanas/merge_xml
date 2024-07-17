package com.vpolosov.trainee.mergexml.repository;

import com.vpolosov.trainee.mergexml.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для взаимодействия с {@link History}.
 * <p>
 * Наследуется от {@link JpaRepository} и {@link JpaSpecificationExecutor}.
 *
 * @author Ali Takushinov
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, String>, JpaSpecificationExecutor<History> {
}
