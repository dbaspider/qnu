package com.ltt.wp;

import com.ltt.wp.config.UploadConfig;
import com.ltt.wp.database.DbPool;
import com.ltt.wp.entity.FileObj;
import com.ltt.wp.utils.FileUtils;
import com.ltt.wp.utils.ThreadUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private UploadConfig uploadConfig;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        log.info("APPLICATION FINISHED");
    }
 
    @Override
    public void run(String... args) {
        log.info("begin EXECUTING : command line runner");

        // 初始化并打开数据库
        if (!DbPool.initDb()) {
            return;
        }

        // 上传文件
        doUpload();

        // 关闭数据库
        DbPool.closeDb();
        log.info("end EXECUTING : command line runner");
    }

    private void doUpload() {
        log.info("*** doUpload begin ***");

        // 初始化七牛云上传配置
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(uploadConfig.getAccessKey(), uploadConfig.getSecretKey());
        String token = auth.uploadToken(uploadConfig.getBucketName());

        log.info("UploadManager init ok");

        // 获取本次上传的文件列表
        List<String> listFiles = new ArrayList<>(128);
        FileUtils.listDirectory(uploadConfig.getFileDir(), listFiles);
        log.info("List total files is {}", listFiles.size());

        long beginTime = System.currentTimeMillis();
        int totalFiles = 0;
        int succFiles = 0;
        int failFiles = 0;
        for (String file : listFiles) {
            totalFiles++;
            log.info("processing {}", totalFiles);

            FileObj obj = new FileObj();
            obj.setOrgFilePath(file);
            if (!obj.populate()) {
                log.error("populate file error: {}", file);
                failFiles++;
                continue;
            }

            if (obj.getFileSize() > uploadConfig.getMaxFileSize()) {
                log.error("file size over limit: {} / {}", file, obj.getFileSize());
                failFiles++;
                continue;
            }

            try {
                String key = obj.getCloudFileName();
                Response resp = uploadManager.put(file, key, token);
                if (resp.isOK()) {
                    succFiles++;
                    log.info("put file success: {} / {}", file, resp.bodyString());
                } else {
                    failFiles++;
                    log.error("put file error: {} / {}", file, resp.bodyString());
                }
            } catch (QiniuException ex) {
                failFiles++;
                log.error("put file exception: {} / {}", file, ex.getMessage());
                ex.printStackTrace();
            }

        }

        // 统计下时间
        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - beginTime);
        long avgTime = (totalFiles > 0) ? totalTime / totalFiles : 0;
        log.info("upload finish, cost total time {} ms / per file avg {} ms", totalTime, avgTime);

        // 汇总信息
        log.info("Success files: {} / Failed files: {}", succFiles, failFiles);

        log.info("*** doUpload end ***");
    }
}