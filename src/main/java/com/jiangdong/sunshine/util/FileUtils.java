package com.jiangdong.sunshine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtils {

    /**
     * @param fileName 文件名
     * @param str      统计的字符串
     * @return 统计一个文件中某个字符串出现的次数
     * @throws Exception
     */
    public static int getCount(String fileName, String str) throws Exception {
        int count = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder = stringBuilder.append(line);
        }
        int index;
        while ((index = stringBuilder.indexOf(str)) != -1) {
            stringBuilder = stringBuilder.delete(index, index + str.length());
            count++;
        }
        return count;
    }

    /**递归输出文件下所有文件|文件夹
     * @param path
     */
    private static void getFiles(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {//date文件夹

                    } else {

                    }
                }
            }
        }
    }

}
