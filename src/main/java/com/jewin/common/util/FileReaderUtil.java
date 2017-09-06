package com.jewin.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jianyang on 17/8/2.
 * 文件读操作工具类
 */
public class FileReaderUtil extends FileUtils {

    /**
     * 文件内容处理类
     * @param <T>
     */
    public interface ProcessCallback<T>{
        /**
         * 针对每一行文件进行处理
         * @param lineIndex     文件行数索引
         * @param line          该文件行数索引所对应的文本内容
         * @return
         */
        T callBack(int lineIndex, String line);
    }

    /**
     * 读取文件中的每一行，并对每一行内容进行处理
     * @param filePath          文件路径
     * @param charset           读取文件的编码
     * @param processCallback   读取一行文件内容后的处理行数
     * @param <T>
     * @return
     */
    public static <T> T readAndProcessEachLine(String filePath, String charset, ProcessCallback<T> processCallback) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException(String.format("文件【%s】不存在。", filePath));
        }
        if(!file.isFile()){
            throw new IOException(String.format("【%s】不是一个文件,请检查。", filePath));
        }

        int i = 0;
        String line = null;
        T t = null;
        BufferedReader br = null;

        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            while(null != (line = br.readLine())){
                i++;
                t = processCallback.callBack(i, line);
            }
        }catch (Exception e){
            throw  e;
        }finally {
            IOUtils.closeQuietly(br);
        }

        return t;
    }
}
