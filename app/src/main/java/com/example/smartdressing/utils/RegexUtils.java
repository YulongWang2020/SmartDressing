package com.example.smartdressing.utils;

import java.util.regex.Pattern;

/**
 * 正则工具类，提供验证邮箱、密码等方法
 */
public final class RegexUtils {

    /**
     * 验证email
     * @param email
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email){
        String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        return Pattern.matches(regex, email);
    }
}
