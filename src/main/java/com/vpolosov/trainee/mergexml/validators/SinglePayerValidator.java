package com.vpolosov.trainee.mergexml.validators;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.DifferentPayerException;
import com.vpolosov.trainee.mergexml.utils.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.File;
import java.util.function.BiPredicate;

import static com.vpolosov.trainee.mergexml.utils.XmlTags.PAYER;

/**
 * Проверяет что в XML документах один и тот же плательщик.
 *
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class SinglePayerValidator implements BiPredicate<String, File> {

    /**
     * Вспомогательный класс для работы с {@link Document}.
     */
    private final DocumentUtil documentUtil;

    /**
     * {@inheritDoc}
     *
     * @throws DifferentPayerException когда разные плательщики.
     */
    @Loggable
    @Override
    public boolean test(String payer, File xml) {
        var nextPayer = documentUtil.getFirstElementByTagName(xml, PAYER);
        if (!payer.equals(nextPayer)) {
            throw new DifferentPayerException(
                "Данные файлы не могут быть объединены, т.к. обнаружены разные плательщики"
            );
        }
        return true;
    }
}
