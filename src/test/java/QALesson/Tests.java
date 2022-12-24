package QALesson;

import QALesson.pojo.People;
import QALesson.utils.FileUtils;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class Tests {
    @Test
    public void textReadZipFile() throws IOException, CsvException {
        HashMap<String, Object> map = FileUtils.readerFromZIP("upload.zip");

        PDF pdf = (PDF) map.get("upload/3.pdf");
        assertThat(pdf).containsExactText("A Simple PDF File");

        XLS xls = (XLS) map.get("upload/2.xls");
        xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue().equals("Михаил");
        xls.excel.getSheetAt(0).getRow(2).getCell(1).getStringCellValue().equals("Дарья");
        xls.excel.getSheetAt(0).getRow(3).getCell(1).getStringCellValue().equals("Слава");

        List<String[]> content = (List<String[]>) map.get("upload/1.csv");
        String[] row1 = content.get(0);
        assertThat(row1[1]).isEqualTo("Russia");
        assertThat(row1[2]).isEqualTo("Ford");
        assertThat(row1[3]).isEqualTo("Car");
        assertThat(row1[4]).isEqualTo("1000000");
    }

    @Test
    void jsonFileJackson() throws Exception {
        People pavel = FileUtils.readJsonToPeople("src/test/resources/People.json");
        assertThat(pavel.name).isEqualTo("Maks");
        assertThat(pavel.age).isEqualTo(30);
        assertThat(pavel.children.get(0).name).isEqualTo("Mary");
        assertThat(pavel.children.get(0).age).isEqualTo(2);
        assertThat(pavel.children.get(1).name).isEqualTo("Bob");
        assertThat(pavel.children.get(1).age).isEqualTo(5);
    }
}
