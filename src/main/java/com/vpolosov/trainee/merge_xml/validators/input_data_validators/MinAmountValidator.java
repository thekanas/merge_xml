package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.aspect.Loggable;
import com.vpolosov.trainee.merge_xml.handler.exception.IncorrectMinAmountException;
import com.vpolosov.trainee.merge_xml.handler.exception.NonExistentTagException;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class MinAmountValidator implements InputDataValidation {

    private static final String AMOUNT_TAG = "AMOUNT";
    private static final BigDecimal MIN_AMOUNT = new BigDecimal(10);

    @Override
    @Loggable
    @SneakyThrows
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {
        List<String> xmlCheckAmount = getIncorrectMinAmountFileName(xmlFiles);
        if (!xmlCheckAmount.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (String fileName : xmlCheckAmount) {
                message.append("В файле ")
                        .append(fileName)
                        .append(" сумма платежа не соответствует минимальной")
                        .append(";");
            }
            throw new IncorrectMinAmountException(message.toString());
        }
    }

    private List<String> getIncorrectMinAmountFileName(List<File> xmlFiles) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        List<String> xmlCheckAmount = new ArrayList<>();
        for (File xml : xmlFiles) {
            Document document = documentBuilder.parse(xml);
            NodeList elementsByTagName = document.getElementsByTagName(AMOUNT_TAG);

            BigDecimal amount;
            try {
                amount = new BigDecimal(elementsByTagName.item(0).getTextContent());
            } catch (Exception e) {
                throw new NonExistentTagException("В файле " + xml.getName() + " не найдена сумма платежа или сумма некорректна");
            }

            if (amount.compareTo(MIN_AMOUNT) < 0) {
                xmlCheckAmount.add(xml.getName());
            }
        }
        return xmlCheckAmount;
    }
}