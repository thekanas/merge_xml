package com.vpolosov.trainee.merge_xml.repository;

import com.vpolosov.trainee.merge_xml.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, String>, JpaSpecificationExecutor<History> {
}
