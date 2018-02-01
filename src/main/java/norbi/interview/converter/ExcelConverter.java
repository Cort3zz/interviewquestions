package norbi.interview.converter;
import norbi.interview.domain.Question;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class ExcelConverter {


    public  List<Question> readXLSFile() {
        HSSFWorkbook workbook = null;
        try {
            //Getting the workbook instance for xls file
            workbook =new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream("interview.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //getting the first sheet from the workbook using sheet name.
        // We can also pass the index of the sheet which starts from '0'.
        HSSFSheet sheet = workbook.getSheet("Munka1");
        HSSFRow row;
        HSSFCell cell;
        List<Question> questions = new ArrayList<>();
        //Iterating all the rows in the sheet
        Iterator rows = sheet.rowIterator();

        while (rows.hasNext()) {
            row = (HSSFRow) rows.next();

            //Iterating all the cells of the current row
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (HSSFCell) cells.next();
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    if (cell.getAddress().toString().startsWith("A")) {
                        String question = cell.getStringCellValue();
                        question = question.replace("\r", "");
                        question = question.replace("\n", "<br />");
                        questions.add(new Question(cell.getAddress().toString(),question));
                    } else if (cell.getAddress().toString().startsWith("B")) {
                        String answer = cell.getStringCellValue();
                        answer = answer.replace("\r", "");
                        answer = answer.replace("\n", "<br />");
                        questions.get(questions.size() - 1).setAnswer(answer);
                    }
                } else { // //Here if require, we can also add below methods to
                    // read the cell content
                    // HSSFCell.CELL_TYPE_BLANK
                    // HSSFCell.CELL_TYPE_FORMULA
                    // HSSFCell.CELL_TYPE_ERROR
                }

            }
        }
        return questions;
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("four score and seven years ago");
        test.add("score four and seven years ago");
        test.add("four score and seven years ago");
        test.add("ask not what your country can do for you");

        for (String string : test) {
            System.out.println(doThisShit(string));
        }
        System.out.println("/////////\\\\\\\\\\\\\\\\\\\\\\");
        List<String> test2 = new ArrayList<>();
        test2.add("the quick brown fox jumped over the lazy dog");
        test2.add("over the lazy dog the quick brown fox jumped");
        test2.add("the lazy quick fox jumped over the brown dog");
        test2.add("the quick lazy dog over the brown fox jumped");
        int[] result = new int[test2.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = doThisShit(test2.get(i));
        }
        int igaziresult = 0;
        for (int i = 0; i < result.length-1; i++) {
            igaziresult += result[i] ^ result[i + 1];
        }
        System.out.println(igaziresult);
    }

    private static int doThisShit(String string) {
        byte[] asciiOfArray  = string.getBytes();
        int result=0;
        for (int i = 0; i < asciiOfArray.length-1; i++) {
            result += asciiOfArray [i]^asciiOfArray [i+1];
        }
        return result;
        }

}
