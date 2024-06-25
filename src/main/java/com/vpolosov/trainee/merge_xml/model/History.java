package com.vpolosov.trainee.merge_xml.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @Column(name = "doc_ref")
    private String docRef;
    @Column(name = "document_date")
    private LocalDate documentDate;
    @Column(name = "date_time_upload")
    private LocalDateTime dateTimeUpload;
}
