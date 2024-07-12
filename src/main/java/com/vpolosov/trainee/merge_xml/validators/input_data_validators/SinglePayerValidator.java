package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.handler.exception.DifferentPayerException;
import com.vpolosov.trainee.merge_xml.utils.DocumentUtil;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

import static com.vpolosov.trainee.merge_xml.utils.XmlTags.PAYER;

/**
 * Проверяет что в XML документах один и тот же плательщик.
 *
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class SinglePayerValidator implements InputDataValidation {

    /**
     * Один плательщик.
     */
    private static final int SINGLE_PAYER = 1;

    /**
     * Вспомогательный класс для работы с {@link Document}.
     */
    private final DocumentUtil documentUtil;

    /**
     * {@inheritDoc}
     *
     * @throws DifferentPayerException когда разные плательщики.
     */
    @Override
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {
        var iterator = xmlFiles.iterator();
        var payer = documentUtil.getFirstElementByTagName(iterator.next(), PAYER);
        while (iterator.hasNext()) {
            var nextPayer = documentUtil.getFirstElementByTagName(iterator.next(), PAYER);
            if (!payer.equals(nextPayer)) {
                throw new DifferentPayerException("Данные файлы не могут быть объединены, т.к. обнаружены разные плательщики");
            }
        }
    }
}
