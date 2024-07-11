package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.aspect.Loggable;
import com.vpolosov.trainee.merge_xml.handler.exception.NotExactlyOneXsdFileException;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class CountXsdValidator implements InputDataValidation {

    private static final int XSD_FILES_NUMBER = 1;

    @Loggable
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {

        if (xsdFiles.size() != XSD_FILES_NUMBER) {
            throw new NotExactlyOneXsdFileException("There are not exactly 1 xsd files");
        }
    }
}
