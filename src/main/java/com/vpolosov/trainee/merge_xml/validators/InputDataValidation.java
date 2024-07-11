package com.vpolosov.trainee.merge_xml.validators;

import java.io.File;
import java.util.List;

public interface InputDataValidation {

    void verify(List<File> xmlFiles, List<File> xsdFiles);
}
