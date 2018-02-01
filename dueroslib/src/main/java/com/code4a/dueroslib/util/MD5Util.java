package com.code4a.dueroslib.util;

import com.baidu.android.common.logging.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jiang on 2018/1/26.
 */

public class MD5Util {
    private static final String TAG = "MD5Utils";

    /**
     * md5加密
     *
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        try {
            // 信息摘要器
            MessageDigest digest = MessageDigest.getInstance("MD5");
            try {
                digest.reset();
                digest.update(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.d(TAG, "md5,", e);
            }
            // 1.把明文变成-byte
            byte[] ret = digest.digest();
            // 2.遍历byte数组与8个二进制位做与运算
            StringBuffer buffer = new StringBuffer();
            for (byte b : ret) {
                // 标准的md5加密
                // 密码学-加盐
                int number = b & 0xff;
                // 3.转换成16进制
                String strNumber = Integer.toHexString(number);
                strNumber = strNumber.toUpperCase();
                // 4.补全
                if (strNumber.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(strNumber);
            }
            // 标准的md5加密后的结果了
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "md5,", e);
            return "";
        }
    }
}
