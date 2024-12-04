package com.example.bookstore.utils;

import android.util.Log;

import com.example.bookstore.dto.MessageCode;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    public static Map<MessageCode, String> readErrorMessages(InputStream excelFile) {
        Map<MessageCode, String> errorMessages = new EnumMap<>(MessageCode.class);

        try (Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                Cell codeCell = row.getCell(0); // Cột "Error Code"
                Cell messageCell = row.getCell(1); // Cột "Message"

                if (codeCell != null && messageCell != null) {
                    String code = codeCell.getStringCellValue();
                    String message = messageCell.getStringCellValue();

                    try {
                        MessageCode messageCode = MessageCode.valueOf(code); // Parse code as enum
                        Log.d("Code", messageCode + " " + message);
                        errorMessages.put(messageCode, message);
                    } catch (IllegalArgumentException e) {
                        Log.w("ReadExcelLog", "Unknown MessageCode: " + code);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ReadExcelLog", e.getMessage());
        }

        return errorMessages;
    }
}
