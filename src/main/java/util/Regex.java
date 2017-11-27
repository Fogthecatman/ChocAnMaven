package util;

public class Regex {
    public static boolean characterLength(String s, int limit) {
        if(s.length() > limit) return false;

        return true;
    }
}
