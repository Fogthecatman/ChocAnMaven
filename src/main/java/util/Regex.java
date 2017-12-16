package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean characterLength(String s, int limit) {
        if(s.length() <= limit) return true;

        return false;
    }

    public static boolean isInteger(String s){
        return  s.matches("^-?\\d+$");
    }

}
