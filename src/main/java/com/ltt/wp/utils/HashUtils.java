package com.ltt.wp.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class HashUtils {

    private static Logger log = LoggerFactory.getLogger(HashUtils.class);

    public static String md5File(String filename) {
        try {
            HashCode hash = com.google.common.io.Files.hash(new File(filename), Hashing.md5());
            return hash.toString().toLowerCase(Locale.ENGLISH);
        } catch (IOException e) {
            log.error("md5File error: {} / {}", filename, e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(md5File("d:\\post-2016-sunsetglow.jpg"));
        System.out.println(md5File("d:\\post-2016-sunsetglow2.jpg"));
    }
}
