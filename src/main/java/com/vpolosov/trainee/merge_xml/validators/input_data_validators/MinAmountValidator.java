package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.aspect.Loggable;
import com.vpolosov.trainee.merge_xml.handler.exception.IncorrectMinAmountException;
import com.vpolosov.trainee.merge_xml.handler.exception.NonExistentTagException;
import com.vpolosov.trainee.merge_xml.utils.DocumentUtil;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.vpolosov.trainee.merge_xml.utils.XmlTags.AMOUNT;

@Component
@RequiredArgsConstructor
public class MinAmountValidator implements InputDataValidation {

    private static final BigDecimal MIN_AMOUNT = new BigDecimal(10);

    private final DocumentUtil documentUtil;

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

    private List<String> getIncorrectMinAmountFileName(List<File> xmlFiles) {
        List<String> xmlCheckAmount = new ArrayList<>();
        for (File xml : xmlFiles) {
            var amountStr = documentUtil.getFirstElementByTagName(xml, AMOUNT);
            BigDecimal amount;
            try {
                amount = new BigDecimal(amountStr);
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