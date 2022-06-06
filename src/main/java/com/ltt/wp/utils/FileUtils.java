package com.ltt.wp.utils;

import cn.hutool.core.util.StrUtil;
import com.ltt.wp.entity.FileObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void listDirectory(String path, List<String> list) {
        if (StrUtil.isEmpty(path)) {
            return;
        }

        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        for (File f : files) {
            if (f.isDirectory()) {
                listDirectory(f.getAbsolutePath(), list);
            } else {
                list.add(f.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(128);
        listDirectory("G:\\Pictures\\win7_qwh_wp", list);
        System.out.println(list.size());
        System.out.println("------------------------------------------");
        for (String s : list) {
            System.out.println(s);
        }

        FileObj obj = new FileObj();
        //obj.setOrgFilePath("G:\\Pictures\\The_Sun_in_high_resolution_pillars.jpg");
        obj.setOrgFilePath("d:\\post-2016-sunsetglow");
        obj.populate();
        System.out.println(obj);
    }

}
