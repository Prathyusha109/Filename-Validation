package com.processing.constants;

import java.util.HashMap;
import java.util.Map;

public class AppConstants {

    public static final Map<String, String> fileExtensionMap;

    static {
        fileExtensionMap = new HashMap<>();
        fileExtensionMap.put("csv", "text/csv");
        fileExtensionMap.put("csv", "application/vnd.ms-excel");
    }

    public static String ERROR_CODE_000="InvalidFile";
    public static String ERROR_CODE_001="InvalidInput";
    public static String ERROR_CODE_002="InvalidFileType";
    public static String ERROR_CODE_003="InvalidFileSize";
    public static String ERROR_CODE_004="EmptyFile";

}
