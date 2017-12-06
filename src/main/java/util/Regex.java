package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean characterLength(String s, int limit) {
        if(s.length() == limit) return true;

        return false;
    }

    public static boolean date(String s) {
        String datePtrn = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";
        Pattern check = Pattern.compile(datePtrn);
        Matcher m = check.matcher(s);
        System.out.println(m.toString());
        return m.matches();
    }
}
