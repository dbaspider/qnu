package com.ltt.wp.tools;

// 分片上传 v1
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import com.qiniu.http.Response;

// 分片上传 v2
//import com.qiniu.storage.UploadManager;
//import com.qiniu.util.Auth;
//import com.qiniu.storage.Configuration;
//import com.qiniu.http.Response;

public class Main {

    public static void main(String[] args) throws QiniuException {
        String accessKey = "xxx";
        String secretKey = "xxx";
        String bucketName = "app-50";

        // Zone z = Zone.autoZone();
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucketName);
        String key = "Background_A.png";
        String localFile = "D:\\reslib\\Background_A.png";

        System.out.println("upload begin ...");
        long beginTime = System.currentTimeMillis();

        Response resp = uploadManager.put(localFile, key, token);

        long endTime = System.currentTimeMillis();
        System.out.println("upload finish ... cost " + (endTime - beginTime) + " ms.");
        System.out.println(resp.bodyString());
//        String accessKey = "Your AccessKey";
//        String secretKey = "Your SecretKey";
//        String bucketName = "upload to bucket";
//        Configuration cfg = new Configuration();
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
//        UploadManager uploadManager = new UploadManager(cfg);
//        Auth auth = Auth.create(accessKey, secretKey);
//        String token = auth.uploadToken(bucketName);
//        String key = "file save key";
//        Response r = uploadManager.put("hello world".getBytes(), key, token);
    }
}
