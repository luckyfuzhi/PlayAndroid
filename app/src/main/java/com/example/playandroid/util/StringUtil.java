package com.example.playandroid.util;

public class StringUtil {
    /**
     * 获取字符串中某一部分字符串与另一部分字符串之间的内容
     * @param input 总的字符串
     * @param startMarker 某一字符串
     * @param endMarker 另一字符串
     * @return 两字符串中间的内容
     */
    public static String extractSubstring(String input, String startMarker, String endMarker) {
        int startIndex = input.indexOf(startMarker);
        if (startIndex == -1) {
            return null; // 起始标记不存在
        }
        startIndex += startMarker.length(); // 跳过起始标记

        int endIndex = input.indexOf(endMarker, startIndex);
        if (endIndex == -1) {
            return null; // 结束标记不存在
        }

        return input.substring(startIndex, endIndex);
    }
}
