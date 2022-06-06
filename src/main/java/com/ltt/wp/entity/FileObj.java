package com.ltt.wp.entity;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ltt.wp.utils.HashUtils;
import lombok.Data;

import java.io.File;

/**
 * 文件对象
 */
@Data
public class FileObj {

    // 主键
    private Long id;

    // 原始文件名
    private String orgFileName;

    // 原始全路径
    private String orgFilePath;

    // md5 hash
    private String md5;

    // 文件大小
    private Long fileSize;

    // 文件扩展名
    private String fileExt;

    // 云上全路径
    private String cloudFilePath;

    // 云上文件名，一般对应 md5 + ext
    private String cloudFileName;

    // 最近一次上传时间
    private String uploadTime;

    // 是否上传成功 0 : 成功，1 ：失败
    private Long uploadSuccess;

    // 最后一次上传失败原因
    private String lastError;

    public void populate() {
        if (StrUtil.isEmpty(orgFilePath)) {
            throw new RuntimeException("populate error: orgFilePath is empty!");
        }

        this.md5 = HashUtils.md5File(orgFilePath);
        this.fileExt = FileUtil.extName(orgFilePath);
        this.orgFileName = FileUtil.getName(orgFilePath);

        if (!StrUtil.isEmpty(this.fileExt)) {
            this.cloudFileName = this.md5 + "." + this.fileExt;
        } else {
            this.cloudFileName = this.md5;
        }

        this.fileSize = FileUtil.size(new File(orgFilePath));
    }
}
