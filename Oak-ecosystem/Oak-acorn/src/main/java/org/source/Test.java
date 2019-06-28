package org.source;

import org.source.utils.FileRead;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Oak on 2018/7/23.
 * Description:
 */
public class Test {
    /**
     * 读取resources 下文件
     */
    public static void readSource1(){
        try {
            File  file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "param.json");
            FileRead.readFileByLine(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取resources下文件
     */
    public static void readSource2(){
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:templates");
            if(file.exists()){
                File[] files = file.listFiles();
                if(files != null){
                    for(File childFile:files){
                        System.out.println(childFile.toString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readSource1();
    }
}
