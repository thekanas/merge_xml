package com.vpolosov.trainee.mergexml.utils;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.FileDeleteException;
import com.vpolosov.trainee.mergexml.handler.exception.FileNotFoundException;
import com.vpolosov.trainee.mergexml.handler.exception.IllegalSizeException;
import com.vpolosov.trainee.mergexml.handler.exception.NotExactlyOneXsdFileException;
import com.vpolosov.trainee.mergexml.handler.exception.NotExactlyTenFilesException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.vpolosov.trainee.mergexml.utils.Constant.FIRST_ELEMENT;

/**
 * Вспомогательный класс для работы с файлами.
 * <p>
 * извлечения XML и XSD файлов
 *
 * @author Maksim Litvinenko
 */
@Component
public class FileUtil {

    /**
     * XML расширение.
     */
    private static final String XML_EXTENSION = ".xml";

    /**
     * XSD расширение.
     */
    private static final String XSD_EXTENSION = ".xsd";

    /**
     * Количество XML файлов.
     */
    private static final int XML_FILES_COUNT = 10;

    /**
     * Количество XSD файлов.
     */
    private static final int XSD_FILES_COUNT = 1;

    /**
     * Возвращает список XML файлов.
     *
     * @param location путь до директории с XML файлами.
     * @return список XML файлов.
     * @throws NotExactlyTenFilesException если количество XML файлов в директории не равно 10.
     * @throws RuntimeException            если при открытии каталога возникает ошибка ввода-вывода.
     */
    @Loggable
    public List<File> listXml(String location) {
        try (var pathStream = Files.list(Path.of(location))) {
            return files(pathStream, XML_EXTENSION, XML_FILES_COUNT);
        } catch (IllegalSizeException e) {
            throw new NotExactlyTenFilesException(
                "There are more than %s xml files, or the files are missing".formatted(XML_FILES_COUNT)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает XSD файл.
     *
     * @param location путь до директории с XSD файлом.
     * @return XSD файл.
     * @throws NotExactlyOneXsdFileException если количество XSD файлов в директории не равно 1.
     * @throws RuntimeException              если при открытии каталога возникает ошибка ввода-вывода.
     */
    @Loggable
    public File xsd(String location) {
        try (var pathStream = Files.list(Path.of(location))) {
            return files(pathStream, XSD_EXTENSION, XSD_FILES_COUNT).get(FIRST_ELEMENT);
        } catch (IllegalSizeException e) {
            throw new NotExactlyOneXsdFileException("There are not exactly 1 xsd files");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает список файлов указанного расширения и размера.
     *
     * @param pathStream    поток файловых путей.
     * @param extensionFile фильтр указывающий на расширение файлов.
     * @param size          определённое значение файлов.
     * @return список файлов определенного расширения и размера.
     * @throws IllegalSizeException при недопустимом размере.
     */
    @Loggable
    public List<File> files(Stream<Path> pathStream, String extensionFile, int size) {
        var xmlList = pathStream
            .filter(path -> path.getFileName().toString().endsWith(extensionFile))
            .map(Path::toFile)
            .collect(ArrayList<File>::new,
                (list, file) -> {
                    list.add(file);
                    if (list.size() > size) {
                        throw new IllegalSizeException();
                    }
                },
                ArrayList::addAll);

        if (xmlList.size() != size) {
            throw new IllegalSizeException();
        }
        return xmlList;
    }

    /**
     * Удалить файл.
     *
     * @param file который нужно удалить.
     * @throws FileNotFoundException если файл не существует.
     * @throws FileDeleteException   если не удаётся удалить файл.
     */
    @Loggable
    public void delete(File file) {
        final boolean filePresent = file.exists();
        if (!file.delete()) {
            if (!filePresent) {
                throw new FileNotFoundException("File does not exist: " + file);
            }
            throw new FileDeleteException("Unable to delete file: " + file);
        }
    }
}
