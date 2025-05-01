package main;

import objects.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Excel - это класс для работы с файлами excel
 * @author Деребас Любовь
 */
public class Excel {
    /**
     * <p>Метод для экспорта результатов игры</p>
     *
     * @param results объект с результатами
     * @since 1.0
     */
    public void exportData(ArrayList<Result> results) {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
        XSSFRow r = sheet.createRow(0);
        r.createCell(0).setCellValue("№");
        r.createCell(1).setCellValue("Имя");
        r.createCell(2).setCellValue("Количество баллов");
        for (int i = 0; i < results.size(); i++) {
            if (i < 10) {
                XSSFRow r2 = sheet.createRow(i + 1);
                r2.createCell(0).setCellValue(i + 1);
                r2.createCell(1).setCellValue(results.get(i).getName());
                r2.createCell(2).setCellValue(results.get(i).getPoints());
            }
        }
        File f = new File("Results.xlsx");
        try {
            book.write(new FileOutputStream(f));
            book.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Метод для импорта результатов игры</p>
     *
     * @since 1.0
     */
    public ArrayList<Result> importData(){
        ArrayList<Result> results = new ArrayList<>();
        try {
            XSSFWorkbook book = new XSSFWorkbook("Results.xlsx");
            XSSFSheet sh = book.getSheetAt(0);
            for (int i = 1; i <= sh.getLastRowNum(); i++) {
                results.add(new Result(sh.getRow(i).getCell(1).getStringCellValue(), (int) sh.getRow(i).getCell(2).getNumericCellValue()));
            }
        } catch (org.apache.poi.openxml4j.exceptions.InvalidOperationException | IOException e) {
            System.out.println("No file");
        }
        return results;
    }
}
