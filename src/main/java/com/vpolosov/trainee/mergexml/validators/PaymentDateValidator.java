package com.vpolosov.trainee.mergexml.validators;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.IncorrectDateException;
import com.vpolosov.trainee.mergexml.utils.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.File;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import static com.vpolosov.trainee.mergexml.utils.XmlTags.DOCUMENTDATE;

/**
 * Проверяет что текущая дата равна дате совершения платежа.
 * <p>
 * Использует дату создания документа, как то с чем будет происходить сравнение.
 *
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class PaymentDateValidator implements Predicate<File> {

    /**
     * Часы для корректировки времени.
     */
    private final Clock clock;

    /**
     * Парсер даты.
     */
    private final DateTimeFormatter localDateFormat;

    /**
     * Вспомогательный класс для работы с {@link Document}.
     */
    private final DocumentUtil documentUtil;

    /**
     * {@inheritDoc}
     *
     * @throws IncorrectDateException когда дата платежа не равна текущей дате.
     */
    @Loggable
    @Override
    public boolean test(File xml) {
        var nowDate = LocalDate.now(clock);
        var dateStr = documentUtil.getFirstElementByTagName(xml, DOCUMENTDATE);
        var date = LocalDate.parse(dateStr, localDateFormat);
        if (!date.equals(nowDate)) {
            throw new IncorrectDateException("Дата платежного документа должна быть равна текущей дате");
        }
        return true;
    }
}
