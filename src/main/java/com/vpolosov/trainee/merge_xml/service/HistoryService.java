package com.vpolosov.trainee.merge_xml.service;

import com.vpolosov.trainee.merge_xml.model.History;
import com.vpolosov.trainee.merge_xml.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public List<History> getHistoryListBySpec(Specification<History> spec) {
        return historyRepository.findAll(spec);
    }

    @Transactional
    public void addHistory(History history) {
        historyRepository.save(history);
    }

    @Transactional
    public void addHistoryFromTotal(Document document) {
        NodeList elementsByTagName1 = document.getElementsByTagName("DOCUMENT");
        for (int i = 0; i < elementsByTagName1.getLength(); i++) {
            String docRef = document.getElementsByTagName("DOCREF").item(i).getTextContent();
            String documentDate = document.getElementsByTagName("DOCUMENTDATE").item(i).getTextContent();
            addHistory(new History(docRef, LocalDate.parse(documentDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    LocalDateTime.now()));
        }
    }
}
