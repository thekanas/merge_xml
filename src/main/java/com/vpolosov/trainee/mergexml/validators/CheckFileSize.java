package com.vpolosov.trainee.mergexml.validators;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Проверка размера файла.
 *
 * @author Ali Takushinov
 */
@Component
public class CheckFileSize {

    /**
     * Константное значение максимального размера файла.
     */
    private static final long FILE_SIZE_LIMIT = 500L * 1024;

    /**
     * Проверяет файл на превышение лимитного размера.
     *
     * @param file который будет проверяться.
     * @return {@code true} если файл не превышает лимитированного размера, иначе {@code false}.
     */
    @Loggable
    public boolean isMoreThanFiveKb(File file) {
        return file.length() > FILE_SIZE_LIMIT;
    }
}
