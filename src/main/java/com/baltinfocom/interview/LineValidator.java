package com.baltinfocom.interview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineValidator {

    public static boolean validateLine(String line) {
        String[] numbersNum = line.split(";");
        for (String number : numbersNum) {
            if (!number.equals("\"\"")) {
                if (!validatePhone(number)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean validatePhone(String phone) {
        Pattern phonePattern = Pattern.compile("^(\")([0-9]*)(\")$");
        Matcher phoneMatcher = phonePattern.matcher(phone);
        return phoneMatcher.matches();
    }

}