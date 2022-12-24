package qaLesson.utils;

import qaLesson.pojo.People;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUtils {

    public static People readJsonToPeople(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path), People.class);
    }


    public static HashMap readerFromZIP(String nameZip) throws IOException, CsvException {
        ZipFile zf = new ZipFile(new File("src/test/resources/" + nameZip));
        ClassLoader cl = FileUtils.class.getClassLoader();
        HashMap<String, Object> map = new HashMap();
        try (ZipInputStream is = new ZipInputStream(cl.getResourceAsStream(nameZip))) {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                try (InputStream inputStream = zf.getInputStream(entry)) {
                    if (entry.getName().contains(".csv")) {
                        CSVReader csv = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        map.put(entry.getName(), csv.readAll());
                    } else if (entry.getName().contains(".pdf")) {
                        map.put(entry.getName(), new PDF(inputStream));
                    } else if (entry.getName().contains(".xls")) {
                        map.put(entry.getName(), new XLS(inputStream));
                    }
                }
            }
        }
        return map;
    }
}
