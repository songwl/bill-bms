package com.yipeng.bill.bms.core.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 *
    ZipFiles zf = new ZipFiles("/Users/lancey/Desktop","/Users/lancey/Desktop/ab.zip");
    zf.addFile("rep.prpt");
    zf.addFile("dbvis.license");
    zf.addFile("02-071606_262.jpg");
    zf.make();
 *
 *
 * Created by lancey on 16/3/28.
 */
public class ZipFiles {

    private String rootpath;
    private String filepath;


    private List<String> files = new ArrayList<>();

    /**
     *
     * @param rootpath 文件的根目录，添加的文件必须在这个目录下面才可以加入到压缩文件当中
     * @param filepath  输出的文件位置
     */
    public ZipFiles(String rootpath,String filepath) {
        this.rootpath = rootpath;
        this.filepath = filepath;
    }

    /**
     *
     * @param file 相对路径
     */
    public void addFile(String file){
        files.add(file);
    }


    public void make()throws IOException{
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(filepath)));
        try {
            for (String f : files) {
                ZipEntry entry = new ZipEntry(f);
                zos.putNextEntry(entry);
                FileInputStream fin = new FileInputStream(new File(rootpath, f));
                IOUtils.copy(fin, zos);
            }
            zos.finish();
        }finally {
            zos.close();
        }
    }

}
