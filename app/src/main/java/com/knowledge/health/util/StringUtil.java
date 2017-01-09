package com.knowledge.health.util;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RudyJun on 2016/11/23.
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (null == str || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    public static String buildTextFont(Context context, int resColorId, String text) {
        int color = context.getResources().getColor(resColorId);
        StringBuilder stringBuilder = new StringBuilder().append(String.format("<font color=\"#%s\">", String.format("%X", color).substring(2))).append(text).append("</font>");
        return stringBuilder.toString();
    }

    public static boolean isEmail(String emailStr) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(emailStr);
        return m.matches();
    }
}
