package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.handler.exception.IncorrectDateException;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import com.vpolosov.trainee.merge_xml.utils.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.File;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static com.vpolosov.trainee.merge_xml.utils.XmlTags.DOCUMENTDATE;

/**
 * Проверяет что текущая дата равна дате совершения платежа.
 * <p>
 * Использует дату создания документа, как то с чем будет происходить сравнение.
 *
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class PaymentDateValidator implements InputDataValidation {

    /**
     * Часы для корректировки времени.
     */
    private final Clock clock;

    /**
     * Вспомогательный класс для работы с {@link Document}.
     */
    private final DocumentUtil documentUtil;

    /**
     * {@inheritDoc}
     *
     * @throws IncorrectDateException когда дата платежа не равна текущей дате.
     */
    @Override
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {
        var nowDate = LocalDate.now(clock);
        for (var xmlFile : xmlFiles) {
            var dateStr = documentUtil.getFirstElementByTagName(xmlFile, DOCUMENTDATE);
            var date = LocalDate.parse(dateStr);
            if (!date.equals(nowDate)) {
                throw new IncorrectDateException("Дата платежного документа должна быть равна текущей дате");
            }
        }
    }
}
