package com.ruoyi.project.spider.util;

public class ParseImageUtil {

    public static String getFileName(String objUrl) {
        String parse = parse(objUrl);
        return parse.substring(parse.lastIndexOf("/")+1,parse.length());
    }

    public static String parse(String objUrl) {
        String s = objUrl.replace("_z2C$q", ":")
                .replace("_z&e3B", ".")
                .replace("AzdH3F", "/");
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '0') {
                chars[i] = '7';
                continue;
            }
            if (chars[i] == '1') {
                chars[i] = 'd';
                continue;
            }
            if (chars[i] == '2') {
                chars[i] = 'g';
                continue;
            }
            if (chars[i] == '3') {
                chars[i] = 'j';
                continue;
            }
            if (chars[i] == '4') {
                chars[i] = 'm';
                continue;
            }
            if (chars[i] == '5') {
                chars[i] = 'o';
                continue;
            }
            if (chars[i] == '6') {
                chars[i] = 'r';
                continue;
            }
            if (chars[i] == '7') {
                chars[i] = 'u';
                continue;
            }
            if (chars[i] == '8') {
                chars[i] = '1';
                continue;
            }
            if (chars[i] == '9') {
                chars[i] = '4';
                continue;
            }
            if (chars[i] == 'a') {
                chars[i] = '0';
                continue;
            }
            if (chars[i] == 'b') {
                chars[i] = '8';
                continue;
            }
            if (chars[i] == 'c') {
                chars[i] = '5';
                continue;
            }
            if (chars[i] == 'd') {
                chars[i] = '2';
                continue;
            }
            if (chars[i] == 'e') {
                chars[i] = 'v';
                continue;
            }
            if (chars[i] == 'f') {
                chars[i] = 's';
                continue;
            }
            if (chars[i] == 'g') {
                chars[i] = 'n';
                continue;
            }
            if
            (chars[i] == 'h') {

                chars[i] = 'k';
                continue;
            }
            if (chars[i] == 'i') {
                chars[i] = 'h';
                continue;
            }
            if (chars[i] == 'j') {
                chars[i] = 'e';
                continue;
            }
            if (chars[i] == 'k') {
                chars[i] = 'b';
                continue;
            }
            if (chars[i] == 'l') {
                chars[i] = '9';
                continue;
            }
            if (chars[i] == 'm') {
                chars[i] = '6';
                continue;
            }
            if (chars[i] == 'n') {
                chars[i] = '3';
                continue;
            }
            if (chars[i] == 'o') {
                chars[i] = 'w';
                continue;
            }
            if (chars[i] == 'p') {
                chars[i] = 't';
                continue;
            }
            if (chars[i] == 'q') {
                chars[i] = 'q';
                continue;
            }
            if (chars[i] == 'r') {
                chars[i] = 'p';
                continue;
            }
            if (chars[i] == 's') {
                chars[i] = 'l';
                continue;
            }
            if (chars[i] == 't') {
                chars[i] = 'i';
                continue;
            }
            if (chars[i] == 'u') {
                chars[i] = 'f';
                continue;
            }
            if (chars[i] == 'v') {
                chars[i] = 'c';
                continue;
            }
            if (chars[i] == 'w') {
                chars[i] = 'a';
                continue;
            }
        }

        return new String(chars);
    }
}
