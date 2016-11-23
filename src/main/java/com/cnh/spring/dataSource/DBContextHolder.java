package com.cnh.spring.dataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class DBContextHolder {

    private static InheritableThreadLocal<String> contextHolder = new InheritableThreadLocal<String>();

    public static List<String> dataSourceList = new ArrayList<String>();

    public static final String DB_TYPE_RW = "READ_AND_WRITE";

    public static final String DB_TYPE_R = "READ";

    public static final String DB_TYPE_W = "WRITE";


    /**
     * 取当前连接类型
     * @return
     */
    public static String getDBType () {
        String dbType = contextHolder.get();
        // 默认读写库
        return dbType == null ? DB_TYPE_RW : dbType;
    }

    /**
     * 设置当前线程的连接类型
     * @param dbType 连接类型
     */
    public static void setDBType (String dbType) {
        contextHolder.set(dbType);
    }

    /**
     * 清理连接类型
     */
    public static void clearDBType () {
        contextHolder.remove();
    }

}
