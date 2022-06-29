package com.ltt.wp.database;

import cn.hutool.core.io.IoUtil;
import com.ltt.wp.entity.FileObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbPool {

    private static Logger log = LoggerFactory.getLogger(DbPool.class);

    private static final String DB_NAME = "file_upload.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_NAME;

    private static final String TAB_NAME = "upload_record";

    private static final int QUERY_TIMEOUT = 15;

    private static Connection connection = null;
    private static Statement statement = null;

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static boolean initDb() {
        log.info("initDb begin");
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            // set timeout sec
            statement.setQueryTimeout(QUERY_TIMEOUT);
            statement.executeUpdate("drop table if exists " + TAB_NAME);
            statement.executeUpdate("create table if not exists " + TAB_NAME + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "org_file_name TEXT NOT NULL,"
                    + "org_file_path TEXT NOT NULL,"
                    + "md5 TEXT NOT NULL,"
                    + "file_size INTEGER NOT NULL,"
                    + "file_ext TEXT,"
                    + "cloud_file_path TEXT NOT NULL,"
                    + "cloud_file_name TEXT NOT NULL,"
                    + "upload_time TEXT NOT NULL,"
                    + "upload_success INTEGER NOT NULL,"
                    + "last_error TEXT)");
            log.info("initDb success");
            return true;
        } catch (Exception e) {
            log.error("initDb failed", e);
            IoUtil.close(statement);
            IoUtil.close(connection);
            statement = null;
            connection = null;
        }
        return false;
    }

    public static void closeDb() {
        log.info("closeDb begin");
        IoUtil.close(statement);
        IoUtil.close(connection);
        log.info("closeDb finish");
    }

    public static boolean record(FileObj rec) {
        return true;
    }
}
