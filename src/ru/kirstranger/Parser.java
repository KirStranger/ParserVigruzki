package ru.kirstranger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Parser {
    // Строка с которой начинаем обрабатывать файл
    private int startRow;

    public int getStartRow() {
        return startRow;
    }

    // Номер ячейки в которой хранится дата
    private int numberCellDate;
    // Номер ячейки в которой хранится сумма
    private int numberCellAmount;
    // Номер ячейки в которой хранится лицевой счет
    private int numberCellID;

    public Parser(int startRow, int numberCellDate, int numberCellAmount, int numberCellID) {
        this.startRow = startRow - 1;
        this.numberCellDate = numberCellDate - 1;
        this.numberCellAmount = numberCellAmount - 1;
        this.numberCellID = numberCellID - 1;
    }

    ArrayList<SingleString> getStrings(File file) {
        ArrayList<SingleString> listStrings = new ArrayList<>();
        XSSFSheet sheet = getSheets(file);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() >= startRow) {
                listStrings.add(new SingleString(parseDate(row), parseAmount(row), parseID(row)));
            }
        }
        return listStrings;
    }

    private String parseID(Row row) {
        String ID = "";
        Pattern pattern = Pattern.compile("\\d{10}");
        String str = row.getCell(numberCellID).getStringCellValue();
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            ID += matcher.group();
        }
        return ID;
    }

    private double parseAmount(Row row) {
        double amount = row.getCell(numberCellAmount).getNumericCellValue();
        if (amount == 0) {
            // Вывести ошибку что сумма не заполнена
        }
        return amount;
    }

    private String parseDate(Row row) {
        String date = row.getCell(numberCellDate).getStringCellValue().trim();
        if (date.isEmpty()) {
            // Вывести сообщение о незаполненой дате в строке №
        }
        return date;
    }

    private XSSFSheet getSheets(File file) {
        XSSFSheet sheet = null;
        FileInputStream fos = null;
        try {
            fos = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fos);
            sheet = workbook.getSheetAt(0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sheet;
    }
}
