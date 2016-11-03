package com.pims.webapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 909436637@qq.com on 2016/10/26.
 * Description:
 */
public class VerificaDate {
    /**
     * @param sDate "yyyyMM","yyyyMMdd","yyyyMMdd HH:mm:ss",
        * 							  "yyyy-MM","yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"
        * 	                          "yyyy.MM","yyyy.MM.dd","yyyy.MM.dd HH:mm:ss"
        *                            "yyyy/MM","yyyy/MM/dd","yyyy/MM/dd HH:mm:ss"
        *                            "yyyy_MM","yyyy_MM_dd","yyyy_MM_dd HH:mm:ss"
     * @return false/true
     */
    public static boolean verificationOfDateIsCorrect(String sDate) {
        if(null == sDate || "".equals(sDate)){
            return false;
        }
        String datePattern = "(" +
                "(^\\d{3}[1-9]|\\d{2}[1-9]\\d{1}|\\d{1}[1-9]\\d{2}|[1-9]\\d{3}" +
                "([-/\\._]?)" +
                "(10|12|0?[13578])" +
                "([-/\\._]?)" +
                "((3[01]|[12][0-9]|0?[1-9])?)"+
        "([\\s]?)" +
                "((([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9]))?))$" +
                "|" +
                "(^\\d{3}[1-9]|\\d{2}[1-9]\\d{1}|\\d{1}[1-9]\\d{2}|[1-9]\\d{3}"+
        "([-/\\._]?)" +
                "(11|0?[469])" +
                "([-/\\._]?)" +
                "(30|[12][0-9]|0?[1-9])" +
                "([\\s]?)" +
                "((([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9]))?))$" +
                "|" +
                "(^\\d{3}[1-9]|\\d{2}[1-9]\\d{1}|\\d{1}[1-9]\\d{2}|[1-9]\\d{3}" +
                "([-/\\._]?)" +
                "(0?2)" +
                "([-/\\._]?)" +
                "(2[0-8]|1[0-9]|0?[1-9])" +
                "([\\s]?)" +
                "((([0-1]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9]))?))$" +
                "|" +
                "(^((\\d{2})(0[48]|[2468][048]|[13579][26]))|((0[48]|[2468][048]|[13579][26])00)" +
                "([-/\\._]?)" +
                "(0?2)" +
                "([-/\\._]?)" +
                "(29)" +
                "([\\s]?)" +
                "((([0-1]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d))?))$" +
                ")";

        return Pattern.compile(datePattern).matcher(sDate).matches();
    }

    public static void main(String[] args) {
        System.out.println(verificationOfDateIsCorrect("2016-10-26 14:57:30"));
    }
}
