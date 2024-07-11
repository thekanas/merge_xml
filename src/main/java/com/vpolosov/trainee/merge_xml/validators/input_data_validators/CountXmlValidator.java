package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.aspect.Loggable;
import com.vpolosov.trainee.merge_xml.handler.exception.NotExactlyTenFilesException;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class CountXmlValidator implements InputDataValidation {
    private static final int XML_FILES_NUMBER = 10;

    @Loggable
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {

        if (xmlFiles.isEmpty() || xmlFiles.size() > XML_FILES_NUMBER) {
            throw new NotExactlyTenFilesException("There are more than " + XML_FILES_NUMBER + " xml files, or the files are missing");
        }
    }
}
