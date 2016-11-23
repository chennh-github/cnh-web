package com.cnh.mvc.share.xls;

import com.cnh.frame.holders.ApplicationContextHolder;
import com.cnh.mvc.share.xls.handler.IXlsExportHandler;
import com.cnh.mvc.share.xls.handler.IXlsImportHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/19
 */
public class XlsServiceRegister {

    /**
     * 导入服务Map
     */
    private static Map<String, String> xlsImportHandlerMap = new HashMap<String, String>();

    /**
     * 导出服务Map
     */
    private static Map<String, String> xlsExportHandlerMap = new HashMap<String, String>();

    static {
        registerExportHandler();
        registerXlsImportHandler();
    }

    /**
     * 注册导入服务
     */
    private static void registerXlsImportHandler() {
        xlsImportHandlerMap.put("DemoXlsImportService", "demoXlsImportService");
    }

    /**
     * 注册导出服务
     */
    private static void registerExportHandler() {


    }

    /**
     * 取导出服务
     *
     * @param name
     * @return
     */
    public static IXlsExportHandler getXlsExportHandler(String name) {
        if (xlsExportHandlerMap.containsKey(name)) {
            String serviceName = xlsExportHandlerMap.get(name);
            return ApplicationContextHolder.getApplicationContext().getBean(serviceName, IXlsExportHandler.class);
        }
        return null;
    }

    /**
     * 取导入服务
     *
     * @param name
     * @return
     */
    public static IXlsImportHandler getXlsImportHandler(String name) {
        if (xlsImportHandlerMap.containsKey(name)) {
            String serviceName = xlsImportHandlerMap.get(name);
            return ApplicationContextHolder.getApplicationContext().getBean(serviceName, IXlsImportHandler.class);
        }
        return null;
    }

}
