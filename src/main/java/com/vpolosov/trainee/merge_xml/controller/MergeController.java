package com.vpolosov.trainee.merge_xml.controller;

import com.vpolosov.trainee.merge_xml.handler.exception.MoreFiveHundredKbException;
import com.vpolosov.trainee.merge_xml.service.files.FilesParsing;
import com.vpolosov.trainee.merge_xml.service.files.MergeXmlFiles;
import com.vpolosov.trainee.merge_xml.validators.CheckFileSize;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
public class MergeController {

    private final FilesParsing countFiles;
    private final MergeXmlFiles fileMerge;
    private final CheckFileSize checkFileSize;
    private final List<InputDataValidation> validators;

    @PostMapping
    public String patchXml(@RequestBody String path) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        List<File> xmlFiles = countFiles.listXmlFiles(path);
        List<File> xsdFiles = countFiles.listXsdFiles(path);

        for(InputDataValidation validator : validators) {
            validator.verify(xmlFiles, xsdFiles);
        }

        File target = fileMerge.merge(xmlFiles, xsdFiles.get(0), "./Total.xml");

        if (checkFileSize.isMoreThanFiveKb(target)) {
            target.delete();
            throw new MoreFiveHundredKbException("There are more than 500 kb files");
        }
        return "Total.xml was created!";
    }

    @GetMapping("/logs")
    public String getLogs() throws IOException {
        String path = "logs/user-logs.log";
        return Files.readString(Path.of(path));
    }
}
