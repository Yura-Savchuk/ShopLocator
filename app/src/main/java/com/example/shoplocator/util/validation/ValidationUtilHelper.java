package com.example.shoplocator.util.validation;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by macbookpro on 04.12.16.
 */

public class ValidationUtilHelper {

    public static boolean isMatch(@NonNull String value, @NonNull String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.find();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isValidDouble(@NonNull String text) {
        try {
            Double.parseDouble(text);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

}
