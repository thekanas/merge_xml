package com.vpolosov.trainee.merge_xml.controller;

import com.vpolosov.trainee.merge_xml.handler.exception.DuplicationProcessingException;
import com.vpolosov.trainee.merge_xml.handler.exception.MoreFiveHundredKbException;
import com.vpolosov.trainee.merge_xml.handler.exception.NotExactlyOneXsdFileException;
import com.vpolosov.trainee.merge_xml.handler.exception.NotExactlyTenFilesException;
import com.vpolosov.trainee.merge_xml.service.CheckDocumentInHistory;
import com.vpolosov.trainee.merge_xml.service.files.FilesParsing;
import com.vpolosov.trainee.merge_xml.service.files.MergeXmlFiles;
import com.vpolosov.trainee.merge_xml.validators.CheckFileSize;
import com.vpolosov.trainee.merge_xml.validators.FilesNumberValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xml")
@AllArgsConstructor
public class MergeController {

    private FilesParsing countFiles;
    private MergeXmlFiles fileMerge;
    private CheckFileSize checkFileSize;
    private CheckDocumentInHistory checkDocumentInHistory;
    private FilesNumberValidator filesNumberValidator;

    @PostMapping
    public String patchXml(@RequestBody String path) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        List<File> xmlFiles = countFiles.listXmlFiles(path);
        if (filesNumberValidator.isMoreThanTenXml(xmlFiles)) {
            throw new NotExactlyTenFilesException("There are more than 10 xml files");
        }

        List<File> xsdFiles = countFiles.listXsdFiles(path);
        if (!filesNumberValidator.isExactlyOneXsd(xsdFiles)) {
            throw new NotExactlyOneXsdFileException("There are not exactly 1 xsd files");
        }

        Map<String, String> docRefAndFileNameFromHistory = checkDocumentInHistory.getLoadDateToBDFromHistory(xmlFiles);
        if (!docRefAndFileNameFromHistory.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<String, String> entry : docRefAndFileNameFromHistory.entrySet()) {
                message.append("В файле ").append(entry.getValue()).append(" найден платеж, который уже был загружен ранее ")
                        .append(entry.getKey()).append(";");
            }
            throw new DuplicationProcessingException(message.toString());
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
