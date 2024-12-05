//package com.example.bookstore.utils;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.example.bookstore.dto.MessageCode;
//
//import java.io.InputStream;
//import java.util.Collections;
//import java.util.Map;
//
//public class ErrorMessagesManager {
//
//    private static ErrorMessagesManager instance;
//    private Map<MessageCode, String> errorMessages;
//
//    private ErrorMessagesManager() {}
//
//    public static ErrorMessagesManager getInstance() {
//        if (instance == null) {
//            synchronized (ErrorMessagesManager.class) {
//                if (instance == null) {
//                    instance = new ErrorMessagesManager();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public void initialize(Context context) {
//        if (errorMessages == null) {
//            try {
//                // Đọc file từ thư mục assets
//                InputStream excelFile = context.getAssets().open("messagecode.xlsx");
//                errorMessages = ExcelReader.getInstance().readErrorMessages(excelFile);
//            } catch (Exception e) {
//                Log.e("ErrorMessagesManager", "Failed to load error messages: " + e.getMessage());
//                errorMessages = Collections.emptyMap();
//            }
//        }
//    }
//
//    public Map<MessageCode, String> getErrorMessages() {
//        return errorMessages;
//    }
//}
